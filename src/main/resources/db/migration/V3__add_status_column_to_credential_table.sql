ALTER TABLE credential
ADD COLUMN status ENUM('PENDING', 'ACTIVE', 'REVOKED') NULL
AFTER password;

UPDATE credential
SET status = 'ACTIVE'
WHERE status IS NULL;

ALTER TABLE credential
MODIFY status ENUM('PENDING', 'ACTIVE', 'REVOKED')
NOT NULL DEFAULT 'PENDING';

CREATE INDEX idx_credential_userid_status
ON credential (userid, status);
