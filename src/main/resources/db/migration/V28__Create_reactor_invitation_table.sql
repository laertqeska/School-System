CREATE TABLE rector_invitation(
    id BIGINT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
    rector_email VARCHAR(255) NOT NULL,
    rector_full_name VARCHAR(255) NOT NULL,
    school_id BIGINT NOT NULL,
    invitation_token VARCHAR(255) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    invited_by BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    accepted_at TIMESTAMP,
    FOREIGN KEY (school_id) REFERENCES schools(id) ON DELETE CASCADE,
    FOREIGN KEY (invited_by) REFERENCES users(id) ON DELETE SET NULL
)