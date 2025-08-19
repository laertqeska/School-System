CREATE TYPE degree_level_enum AS ENUM('BACHELOR','MASTER','DOCTORATE');
CREATE TABLE study_programs(
    id BIGINT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
    department_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    degree_level degree_level_enum NOT NULL,
    duration_semesters INTEGER NOT NULL,
    total_credits INTEGER NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE
);

CREATE TRIGGER set_updated_at_study_programs
BEFORE UPDATE ON study_programs
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();