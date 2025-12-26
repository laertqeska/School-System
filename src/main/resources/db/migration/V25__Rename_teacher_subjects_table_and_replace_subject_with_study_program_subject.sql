ALTER TABLE teacher_subjects
RENAME TO teacher_study_program_subjects;

ALTER TABLE teacher_study_program_subjects
DROP CONSTRAINT fk_teacher_subjects_subject;

ALTER TABLE teacher_study_program_subjects
RENAME COLUMN subject_id TO study_program_subject_id;

ALTER TABLE teacher_study_program_subjects
ADD CONSTRAINT fk_teacher_sps FOREIGN KEY (study_program_subject_id)
REFERENCES study_program_subjects(id) ON DELETE CASCADE;

ALTER TABLE teacher_study_program_subjects
DROP CONSTRAINT uq_teacher_assignment;

ALTER TABLE teacher_study_program_subjects
ADD CONSTRAINT uq_teacher_assignment_sps UNIQUE (teacher_id, study_program_subject_id, class_id, academic_year_id);

ALTER TRIGGER set_updated_at_teacher_subjects
ON teacher_study_program_subjects
RENAME TO set_updated_at_teacher_sps;
