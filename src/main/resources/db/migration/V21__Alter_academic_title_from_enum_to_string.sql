ALTER TABLE teachers
ALTER COLUMN academic_title TYPE VARCHAR(50)
USING academic_title::text;

DROP TYPE IF EXISTS academic_title_enum;