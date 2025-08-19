CREATE TABLE students(
    id BIGINT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    school_id BIGINT NOT NULL,
    study_program_id BIGINT NOT NULL,
    student_id VARCHAR(50) UNIQUE,
    enrollment_date DATE,
    current_year INTEGER,
    current_semester INTEGER,
    status ENUM('ACTIVE','SUSPENDED','GRADUATED','DROPPED OUT') DEFAULT 'ACTIVE',
    personal_number VARCHAR(20),
    date_of_birth DATE,
    gender ENUM('MALE','FEMALE'),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (school_id) REFERENCES schools(id) ON DELETE CASCADE
);