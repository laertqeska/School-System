-- Schools table
CREATE TYPE school_type_enum AS ENUM('PUBLIC','PRIVATE');

CREATE TABLE schools (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    university_type school_type_enum NOT NULL,
    license_number VARCHAR(100), -- MASH license number
    rector_id BIGINT,
    address TEXT,
    city VARCHAR(100) NOT NULL,
    postal_code VARCHAR(10),
    phone VARCHAR(20),
    email VARCHAR(255),
    website_link VARCHAR(255),
    establishment_year INTEGER,
    is_active BOOLEAN DEFAULT TRUE,
    created_by BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (rector_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TRIGGER set_updated_at_schools
BEFORE UPDATE ON schools
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();
