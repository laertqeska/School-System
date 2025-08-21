CREATE TABLE departments(
    id BIGINT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
    faculty_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    department_head_id BIGINT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (faculty_id) REFERENCES faculties(id) ON DELETE CASCADE,
    FOREIGN KEY (department_head_id) REFERENCES users(id) ON DELETE SET NULL
);


CREATE TRIGGER set_updated_at_departments
BEFORE UPDATE ON departments
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();
