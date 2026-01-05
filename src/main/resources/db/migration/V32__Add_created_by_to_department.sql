ALTER TABLE departments
ADD COLUMN created_by BIGINT;

ALTER TABLE departments
ADD CONSTRAINT fk_departments_created_by
FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE;