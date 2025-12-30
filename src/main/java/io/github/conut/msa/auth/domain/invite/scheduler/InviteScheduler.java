package io.github.conut.msa.auth.domain.invite.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.github.conut.msa.auth.domain.invite.service.InviteService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InviteScheduler {
    private final InviteService inviteService;

    @Scheduled(cron = "0 */10 * * * *")
    public void releaseInviteCodeReservations() {
        inviteService.releaseReservedInviteCodes();
    }
}
