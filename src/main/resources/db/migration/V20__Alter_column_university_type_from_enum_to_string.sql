ALTER TABLE schools
ALTER COLUMN university_type TYPE VARCHAR(50)
USING university_type::text;

DROP TYPE IF EXISTS school_type_enum;