package io.github.conut.msa.auth.credential.service;

import org.springframework.stereotype.Service;

import io.github.conut.msa.auth.credential.dao.CredentialDAO;
import io.github.conut.msa.auth.credential.dto.CredentialInsertParam;
import io.github.conut.msa.auth.credential.dto.CredentialRow;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CredentialService {
    private final CredentialDAO credentialDAO;

    public CredentialRow selectActiveCredentialByUserid(String userid) {
        return credentialDAO.selectActiveCredentialByUserid(userid);
    }

    public void insert(String userUuid, String userid, String password) {
        CredentialInsertParam credentialInsertParam = new CredentialInsertParam();
        credentialInsertParam.setUserUuid(userUuid);
        credentialInsertParam.setUserid(userid);
        credentialInsertParam.setPassword(password);
        credentialDAO.insert(credentialInsertParam);
    }
}
