package io.github.conut.msa.auth.infra.outbox.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.github.conut.msa.auth.infra.outbox.kafka.OutboxProducer;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {
    private final OutboxProducer outboxProducer;

    @Scheduled(cron = "0 */10 * * * *")
    public void publish() {
        outboxProducer.publish();
    }
}
