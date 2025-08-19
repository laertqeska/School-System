CREATE TABLE grades (
    id BIGINT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
    student_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,         -- References subjects table
    teacher_id BIGINT NOT NULL,
    class_id BIGINT NOT NULL,
    academic_year_id BIGINT NOT NULL,
    grade_type VARCHAR(20) CHECK (grade_type IN ('QUIZ', 'ASSIGNMENT', 'MIDTERM', 'FINAL', 'PROJECT')) NOT NULL,
    score DECIMAL(5,2) NOT NULL,
    max_score DECIMAL(5,2) NOT NULL,
    grade_date DATE NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_grades_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    CONSTRAINT fk_grades_subject FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    CONSTRAINT fk_grades_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE,
    CONSTRAINT fk_grades_class FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
    CONSTRAINT fk_grades_year FOREIGN KEY (academic_year_id) REFERENCES academic_years(id) ON DELETE CASCADE
);