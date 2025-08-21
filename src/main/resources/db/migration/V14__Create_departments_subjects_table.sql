CREATE TABLE department_subjects (
    id BIGINT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
    department_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    is_primary_department BOOLEAN DEFAULT FALSE,  -- Which dept "owns" the subject
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_dept_subjects_dept FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE,
    CONSTRAINT fk_dept_subjects_subject FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    CONSTRAINT uq_dept_subject UNIQUE (department_id, subject_id)
);

CREATE TRIGGER set_updated_at_department_subjects
BEFORE UPDATE ON department_subjects
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();