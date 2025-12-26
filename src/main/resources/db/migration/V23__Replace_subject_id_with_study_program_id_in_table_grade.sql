ALTER TABLE grades DROP CONSTRAINT IF EXISTS fk_grades_subject;

ALTER TABLE grades DROP COLUMN IF EXISTS subject_id;

ALTER TABLE grades ADD COLUMN study_program_subject_id BIGINT NOT NULL;

ALTER TABLE grades
    ADD CONSTRAINT fk_grades_study_program_subject
    FOREIGN KEY (study_program_subject_id)
    REFERENCES study_program_subjects(id)
    ON DELETE CASCADE;
