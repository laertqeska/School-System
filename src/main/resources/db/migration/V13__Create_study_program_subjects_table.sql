CREATE TABLE study_program_subjects (
    id BIGINT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
    study_program_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    credits INTEGER NOT NULL,
    semester INTEGER NOT NULL,
    year_level INTEGER NOT NULL,
    prerequisites JSON,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (study_program_id) REFERENCES study_programs(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    UNIQUE KEY unique_program_subject (study_program_id, subject_id)
);