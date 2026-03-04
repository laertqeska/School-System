ALTER TABLE schedules
DROP COLUMN effective_from;

ALTER TABLE schedules
DROP COLUMN effective_until;

ALTER TABLE schedules
ADD COLUMN season_id BIGINT NOT NULL,
ADD CONSTRAINT fk_schedules_season
    FOREIGN KEY (season_id) REFERENCES seasons(id)
    ON DELETE RESTRICT;