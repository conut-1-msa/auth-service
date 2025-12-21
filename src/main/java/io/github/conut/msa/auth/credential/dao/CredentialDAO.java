package io.github.conut.msa.auth.credential.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.github.conut.msa.auth.credential.dto.CredentialInsertParam;
import io.github.conut.msa.auth.credential.dto.CredentialRow;

@Mapper
public interface CredentialDAO {
    CredentialRow selectActiveCredentialByUserid(String userid);
    boolean existsByUserid(String userid);
    void insert(CredentialInsertParam credentialInsertParam);
    int activateCredentialByUserUuid(String userUuid);
    boolean isActive(String userUuid);
    int failPendingCredentialsWithBatchId(String batchId);
    List<CredentialRow> selectByBatchId(String batchId);
    int clearBatchId(String batchId);
}
