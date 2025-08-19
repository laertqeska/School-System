--Academic years table
CREATE TABLE academic_years(
   id BIGINT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
   school_id BIGINT NOT NULL, --needed for schools with different academic years
   start_date DATE NOT NULL,
   end_date DATE NOT NULL,
   is_current BOOLEAN DEFAULT FALSE,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY (school_id) REFERENCES schools(id) ON DELETE CASCADE
);

CREATE TRIGGER set_updated_at_academic_years
BEFORE UPDATE ON academic_years
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();