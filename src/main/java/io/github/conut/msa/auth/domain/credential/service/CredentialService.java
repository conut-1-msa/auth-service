package io.github.conut.msa.auth.domain.credential.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.conut.msa.auth.domain.credential.dao.CredentialDAO;
import io.github.conut.msa.auth.domain.credential.dto.CredentialInsertParam;
import io.github.conut.msa.auth.domain.credential.dto.CredentialRow;
import io.github.conut.msa.auth.infra.outbox.dao.OutboxDAO;
import io.github.conut.msa.auth.infra.outbox.row.OutboxRow;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CredentialService {
    private final CredentialDAO credentialDAO;
    private final OutboxDAO outboxDAO;
    private final ObjectMapper objectMapper;

    public CredentialRow selectActiveCredentialByUserid(String userid) {
        return credentialDAO.selectActiveCredentialByUserid(userid);
    }

    public boolean existsByUserid(String userid) {
        return credentialDAO.existsByUserid(userid);
    }

    public void insert(String userUuid, String userid, String password) {
        CredentialInsertParam credentialInsertParam = new CredentialInsertParam();
        credentialInsertParam.setUserUuid(userUuid);
        credentialInsertParam.setUserid(userid);
        credentialInsertParam.setPassword(password);
        credentialDAO.insert(credentialInsertParam);
    }

    public int activateCredentialByUserUuid(String userUuid) {
        int updated = credentialDAO.activateCredentialByUserUuid(userUuid);
        if (updated > 1) {
            throw new IllegalStateException("Multiple credentials activated for a single user UUID: " + userUuid);
        }
        return updated;
    }

    public boolean isActive(String userUuid) {
        return credentialDAO.isActive(userUuid);
    }

    @Transactional
    public void failPendingCredentials() {
        String batchId = UUID.randomUUID().toString();
        int updated = credentialDAO.failPendingCredentialsWithBatchId(batchId);
        if (updated == 0) {
            return;
        }
        List<CredentialRow> credentials = credentialDAO.selectByBatchId(batchId);
        List<OutboxRow> outboxRows = new ArrayList<>(credentials.size());
        for (var credential: credentials) {
            outboxRows.add(buildOutboxRow(credential));
        }
        outboxDAO.insertBatch(outboxRows);
        credentialDAO.clearBatchId(batchId);
    }

    private OutboxRow buildOutboxRow(CredentialRow credential) {
        String eventId = buildOutboxEventId(credential);
        String payload = buildOutboxPayload(credential);

        OutboxRow row = new OutboxRow();
        row.setEventId(eventId);
        row.setAggregateType("credential");
        row.setAggregateId(credential.getUserUuid());
        row.setEventType("credential.register.failed");
        row.setPayload(payload);
        return row;
    }

    private String buildOutboxEventId(CredentialRow credential) {
        return "credential.register.failed:" + credential.getUserUuid();
    }

    private String buildOutboxPayload(CredentialRow credential) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userUuid", credential.getUserUuid());
        String payloadJson;
        try {
            payloadJson = objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("outbox payload serialize fail", exception);
        }
        return payloadJson;
    }
}
