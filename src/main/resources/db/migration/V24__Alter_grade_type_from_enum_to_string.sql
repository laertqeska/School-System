ALTER TABLE grades
ALTER COLUMN grade_type TYPE VARCHAR(50)
USING grade_type::text;

DROP TYPE IF EXISTS grade_type_enum;