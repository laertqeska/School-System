--Academic years table
CREATE TABLE academic_years(
   id BIGINT GENERATED ALWAYS as IDENTITY PRIMARY KEY,
   school_id BIGINT NOT NULL, --needed for schools with different academic years
   start_date DATE NOT NULL,
   end_date DATE NOT NULL,
   is_current BOOLEAN DEFAULT FALSE,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   FOREIGN KEY (school_id) REFERENCES schools(id) ON DELETE CASCADE
);