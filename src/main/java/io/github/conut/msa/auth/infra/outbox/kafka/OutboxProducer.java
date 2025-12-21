package io.github.conut.msa.auth.infra.outbox.kafka;

import java.util.List;
import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.conut.msa.auth.infra.outbox.dao.OutboxDAO;
import io.github.conut.msa.auth.infra.outbox.row.OutboxRow;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OutboxProducer {
    private static final Map<String, String> TOPIC_BY_EVENT_TYPE = Map.of(
        "credential.register.failed", "auth.credential.failed"
    );
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OutboxDAO outboxDAO;
    private final ObjectMapper objectMapper;

    @Transactional
    public void publish() {
        List<OutboxRow> outboxRows = outboxDAO.selectUnpublished();

        for (var outboxRow: outboxRows) {
            String topic = TOPIC_BY_EVENT_TYPE.get(outboxRow.getEventType());
            if (topic == null) {
                continue;
            }

            JsonNode payload;
            try {
                payload = objectMapper.readTree(outboxRow.getPayload());
            } catch (Exception exception) {
                continue;
            }

            try {
                kafkaTemplate.send(
                    topic,
                    outboxRow.getAggregateId(),
                    payload
                ).get();
                outboxDAO.markPublished(outboxRow.getId());
            } catch (Exception exception) {
                break;
            }
        }
    }
}
