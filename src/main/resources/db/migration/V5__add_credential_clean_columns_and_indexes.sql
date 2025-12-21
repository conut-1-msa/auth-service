ALTER TABLE credential
ADD COLUMN failed_at TIMESTAMP NULL,
ADD COLUMN batch_id VARCHAR(36) NULL,
MODIFY status ENUM('PENDING', 'ACTIVE', 'REVOKED', 'FAILED') NOT NULL DEFAULT 'PENDING';

CREATE INDEX idx_credential_status_created_at
ON credential (status, created_at);

CREATE INDEX idx_credential_batch_id
ON credential (batch_id);
