CREATE TABLE seasons(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY ,
    name VARCHAR(50) NOT NULL,
    code VARCHAR(20) NOT NULL,
    season_type VARCHAR(15) NOT NULL,
    academic_year_id BIGINT NOT NULL,
    faculty_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    season_status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMP NULL,
    deleted_by BIGINT NULL,

    CONSTRAINT fk_seasons_academic_year
                    FOREIGN KEY (academic_year_id) REFERENCES academic_years(id),
    CONSTRAINT fk_seasons_faculty
                    FOREIGN KEY (faculty_id) REFERENCES faculties(id),
    CONSTRAINT fk_seasons_deleted_by
        FOREIGN KEY (deleted_by) REFERENCES users(id),
    CONSTRAINT ck_seasons_dates CHECK (end_date > start_date),
    CONSTRAINT uk_seasons_faculty_code UNIQUE (faculty_id, code)
);