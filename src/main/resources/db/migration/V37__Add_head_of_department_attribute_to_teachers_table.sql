ALTER TABLE teachers
ADD head_of_department BIGINT NULL;

ALTER TABLE teachers
ADD CONSTRAINT fk_teachers_head_of_department FOREIGN KEY (head_of_department) REFERENCES departments(id)
ON DELETE SET NULL;