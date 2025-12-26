
ALTER TABLE students
ADD COLUMN class_id BIGINT,
ADD FOREIGN KEY (class_id) REFERENCES classes(id);