-- Class table
CREATE TABLE classes(
    id BIGINT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
    study_program_id BIGINT NOT NULL,
    academic_year_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    year_level INTEGER NOT NULL,
    max_students INTEGER DEFAULT 30,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (study_program_id) REFERENCES study_programs(id) ON DELETE CASCADE,
    FOREIGN KEY (academic_year_id) REFERENCES academic_years(id) ON DELETE CASCADE
);

CREATE TRIGGER set_updated_at_classes
BEFORE UPDATE ON classes
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();