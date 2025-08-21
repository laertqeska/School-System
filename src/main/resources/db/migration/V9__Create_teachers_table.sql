CREATE TYPE academic_title_enum AS ENUM('ASISTENT','LEKTOR','PROFESOR_ASOCIUAR','PROFESOR');

CREATE TABLE teachers(
    id BIGINT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    school_id BIGINT NOT NULL,
    department_id BIGINT,
    employee_id VARCHAR(50) UNIQUE,
    academic_title academic_title_enum NOT NULL,
    qualification VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (school_id) REFERENCES schools(id) ON DELETE CASCADE
);

CREATE TRIGGER set_updated_at_teachers
BEFORE UPDATE ON teachers
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();