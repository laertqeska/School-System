ALTER TABLE students
    ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP NULL,
    ADD COLUMN deleted_by BIGINT NULL;

ALTER TABLE users
    ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP NULL,
    ADD COLUMN deleted_by BIGINT NULL;

ALTER TABLE teachers
    ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP NULL,
    ADD COLUMN deleted_by BIGINT NULL;

ALTER TABLE classes
    ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP NULL,
    ADD COLUMN deleted_by BIGINT NULL;

ALTER TABLE study_programs
    ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP NULL,
    ADD COLUMN deleted_by BIGINT NULL;

ALTER TABLE students
    ADD CONSTRAINT fk_students_deleted_by
        FOREIGN KEY (deleted_by) REFERENCES users(id)
        ON DELETE SET NULL;

ALTER TABLE teachers
    ADD CONSTRAINT fk_teachers_deleted_by
        FOREIGN KEY (deleted_by) REFERENCES users(id)
        ON DELETE SET NULL;

ALTER TABLE classes
    ADD CONSTRAINT fk_classes_deleted_by
        FOREIGN KEY (deleted_by) REFERENCES users(id)
        ON DELETE SET NULL;

ALTER TABLE study_programs
    ADD CONSTRAINT fk_study_programs_deleted_by
        FOREIGN KEY (deleted_by) REFERENCES users(id)
        ON DELETE SET NULL;


ALTER TABLE schools
    ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP NULL,
    ADD COLUMN deleted_by BIGINT NULL;

ALTER TABLE schools
    ADD CONSTRAINT fk_schools_deleted_by
            FOREIGN KEY (deleted_by) REFERENCES users(id)
            ON DELETE SET NULL;


ALTER TABLE school_admins
    ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP NULL,
    ADD COLUMN deleted_by BIGINT NULL;

ALTER TABLE school_admins
    ADD CONSTRAINT fk_school_admins_deleted_by
            FOREIGN KEY (deleted_by) REFERENCES users(id)
            ON DELETE SET NULL;

ALTER TABLE faculties
    ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP NULL,
    ADD COLUMN deleted_by BIGINT NULL;

ALTER TABLE faculties
    ADD CONSTRAINT fk_faculties_deleted_by
        FOREIGN KEY (deleted_by) REFERENCES  users(id)
        ON DELETE SET NULL;

ALTER TABLE departments
    ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP NULL,
    ADD COLUMN deleted_by BIGINT NULL;

ALTER TABLE departments
    ADD CONSTRAINT fk_departments_deleted_by
        FOREIGN KEY (deleted_by) REFERENCES  users(id)
        ON DELETE SET NULL;

ALTER TABLE study_program_subjects
    ADD COLUMN is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN deleted_at TIMESTAMP NULL,
    ADD COLUMN deleted_by BIGINT NULL;

ALTER TABLE study_program_subjects
    ADD CONSTRAINT fk_study_program_subjects_deleted_by
        FOREIGN KEY (deleted_by) REFERENCES users(id)
        ON DELETE SET NULL;