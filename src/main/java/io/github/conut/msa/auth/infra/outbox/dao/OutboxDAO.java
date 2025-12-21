package io.github.conut.msa.auth.infra.outbox.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.github.conut.msa.auth.infra.outbox.row.OutboxRow;

@Mapper
public interface OutboxDAO {
    int insertBatch(List<OutboxRow> outboxRows);
    List<OutboxRow> selectUnpublished();
    int markPublished(int id);
}
