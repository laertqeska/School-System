CREATE TYPE status_enum AS ENUM('ACTIVE','SUSPENDED','GRADUATED','DROPPED OUT');
CREATE TYPE gender_enum AS ENUM('MALE','FEMALE');

CREATE TABLE students(
    id BIGINT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    school_id BIGINT NOT NULL,
    study_program_id BIGINT NOT NULL,
    student_id VARCHAR(50) UNIQUE,
    enrollment_date DATE,
    current_year INTEGER,
    current_semester INTEGER,
    status status_enum DEFAULT 'ACTIVE' NOT NULL,
    personal_number VARCHAR(20),
    date_of_birth DATE,
    gender gender_enum NOT NULL,
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (school_id) REFERENCES schools(id) ON DELETE CASCADE
);

CREATE TRIGGER set_updated_at_students
BEFORE UPDATE ON students
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();