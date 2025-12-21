package io.github.conut.msa.auth.credential.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.github.conut.msa.auth.credential.service.CredentialService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CredentialScheduler {
    private final CredentialService credentialService;

    @Scheduled(cron = "0 */10 * * * *")
    public void failPendingCredentials() {
        credentialService.failPendingCredentials();
    }
}
