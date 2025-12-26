ALTER TABLE subjects
ADD COLUMN department_id BIGINT;

ALTER TABLE subjects
ADD CONSTRAINT fk_subjects_department
FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE;