CREATE TABLE dean_invitation(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    dean_email VARCHAR(255) NOT NULL,
    dean_full_name VARCHAR(255) NOT NULL,
    faculty_id BIGINT NOT NULL,
    invitation_token VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(30) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    accepted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    invited_by BIGINT,
    FOREIGN KEY (faculty_id) REFERENCES faculties(id) ON DELETE CASCADE,
    FOREIGN KEY (invited_by) REFERENCES users(id) ON DELETE SET NULL
);