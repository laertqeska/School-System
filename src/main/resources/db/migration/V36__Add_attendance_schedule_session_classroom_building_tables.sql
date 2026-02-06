CREATE TABLE buildings(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    faculty_id BIGINT NOT NULL,
    building_name VARCHAR(100) NOT NULL,
    building_code VARCHAR(20) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP NULL,
    deleted_by BIGINT NULL,

    CONSTRAINT fk_building_faculty
        FOREIGN KEY (faculty_id) REFERENCES faculties(id),
    CONSTRAINT fk_building_deleted_by
                      FOREIGN KEY (deleted_by) REFERENCES users(id)
);

CREATE TABLE classrooms(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    building_id BIGINT NOT NULL,
    classroom_number VARCHAR(10) NOT NULL,
    classroom_name VARCHAR(100) NOT NULL,
    classroom_type VARCHAR(100) NOT NULL,
    capacity INT NOT NULL,
    has_projector BOOLEAN NOT NULL DEFAULT FALSE,
    has_audio_system BOOLEAN NOT NULL DEFAULT FALSE,
    is_accessible BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP NULL,
    deleted_by BIGINT NULL,

    CONSTRAINT fk_classroom_building
                       FOREIGN KEY (building_id) REFERENCES buildings(id),
    CONSTRAINT fk_classroom_deleted_by
                       FOREIGN KEY (deleted_by) REFERENCES users(id),
    CONSTRAINT uk_classroom_building_number
        UNIQUE (building_id, classroom_number)

);

CREATE TABLE schedules(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    teacher_subject_id BIGINT NOT NULL,
    day_of_week VARCHAR(10) NOT NULL,
    classroom_id BIGINT NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    effective_from DATE NOT NULL,
    effective_until DATE NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP NULL,
    deleted_by BIGINT NULL,

    CONSTRAINT fk_schedule_teacher_subject
                      FOREIGN KEY (teacher_subject_id) REFERENCES teacher_study_program_subjects(id),
    CONSTRAINT fk_schedule_classroom
                      FOREIGN KEY (classroom_id) REFERENCES classrooms(id),
    CONSTRAINT fk_schedule_deleted_by
                      FOREIGN KEY (deleted_by) REFERENCES users(id)
);

CREATE TABLE class_sessions(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    schedule_id BIGINT NOT NULL,
    session_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    topic TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    classroom_id BIGINT NULL,
    notes TEXT,

    CONSTRAINT fk_class_session_schedule
                           FOREIGN KEY (schedule_id) REFERENCES schedules(id),
    CONSTRAINT fk_class_session_classroom
                           FOREIGN KEY (classroom_id) REFERENCES classrooms(id)

);

CREATE TABLE attendance_records(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    student_id BIGINT NOT NULL,
    class_session_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,

    CONSTRAINT fk_attendance_records_student
                               FOREIGN KEY (student_id) REFERENCES students(id),
    CONSTRAINT fk_attendance_records_class_session
                               FOREIGN KEY (class_session_id) REFERENCES class_sessions(id),
    CONSTRAINT fk_attendance_records_user
                               FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT uk_attendance_student_session
        UNIQUE (student_id, class_session_id)
);