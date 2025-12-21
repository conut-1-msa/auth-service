package io.github.conut.msa.auth.infra.outbox.row;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OutboxRow {
    private int id;
    private String eventId;
    private String aggregateType;
    private String aggregateId;
    private String eventType;
    private String payload;
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;
}
