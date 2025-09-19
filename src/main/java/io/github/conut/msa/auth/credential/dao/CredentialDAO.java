package io.github.conut.msa.auth.credential.dao;

import org.apache.ibatis.annotations.Mapper;

import io.github.conut.msa.auth.credential.dto.CredentialInsertParam;
import io.github.conut.msa.auth.credential.dto.CredentialRow;

@Mapper
public interface CredentialDAO {
    CredentialRow selectByUserid(String userid);
    void insert(CredentialInsertParam credentialInsertParam);
}
