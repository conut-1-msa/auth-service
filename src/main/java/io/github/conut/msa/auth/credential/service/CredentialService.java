package io.github.conut.msa.auth.credential.service;

import org.springframework.stereotype.Service;

import io.github.conut.msa.auth.credential.dao.CredentialDAO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CredentialService {
    private final CredentialDAO credentialDAO;
}
