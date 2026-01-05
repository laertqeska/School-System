ALTER TABLE subjects
ADD COLUMN created_by BIGINT;

ALTER TABLE study_programs
ADD COLUMN created_by BIGINT;

ALTER TABLE study_program_subjects
ADD COLUMN created_by BIGINT;

ALTER TABLE subjects
ADD CONSTRAINT fk_subjects_created_by
FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE study_programs
ADD CONSTRAINT fk_study_programs_created_by
FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE study_program_subjects
ADD CONSTRAINT fk_study_program_subjects_created_by
FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE;