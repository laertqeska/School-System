CREATE TABLE teacher_subjects (
    id BIGINT GENERATED ALWAYS as IDENTITY,
    teacher_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,         -- References subjects table
    class_id BIGINT NOT NULL,
    academic_year_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_teacher_subjects_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE,
    CONSTRAINT fk_teacher_subjects_subject FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    CONSTRAINT fk_teacher_subjects_class FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
    CONSTRAINT fk_teacher_subjects_year FOREIGN KEY (academic_year_id) REFERENCES academic_years(id) ON DELETE CASCADE,
    CONSTRAINT uq_teacher_assignment UNIQUE (teacher_id, subject_id, class_id, academic_year_id)
);