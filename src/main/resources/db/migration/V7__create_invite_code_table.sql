CREATE TABLE invite_code (
    id INT AUTO_INCREMENT NOT NULL,
    code VARCHAR(36) NOT NULL,
    description VARCHAR(30) NULL,
    status ENUM('UNUSED', 'RESERVED', 'CONSUMED') NOT NULL DEFAULT 'UNUSED',
    expires_at TIMESTAMP NOT NULL,
    reserved_at TIMESTAMP NULL,
    reserved_by VARCHAR(36) NULL,
    consumed_at TIMESTAMP NULL,
    consumed_by VARCHAR(36) NULL,

    CONSTRAINT pk_invite_code_id PRIMARY KEY (id),
    CONSTRAINT uk_invite_code_code UNIQUE (code)
);

CREATE INDEX idx_invite_code_status_id
ON invite_code (status, id);
CREATE INDEX idx_invite_code_status_expires_at_id
ON invite_code (status, expires_at, id);
