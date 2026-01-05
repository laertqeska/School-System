-- 1. Drop the existing column
ALTER TABLE study_programs DROP COLUMN degree_level;

-- 2. Recreate as VARCHAR
ALTER TABLE study_programs
ADD COLUMN degree_level VARCHAR(50) NOT NULL;

-- 3. Drop the enum type if not used elsewhere
DROP TYPE IF EXISTS degree_level_enum;