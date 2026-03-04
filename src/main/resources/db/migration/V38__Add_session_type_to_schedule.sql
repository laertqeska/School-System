ALTER TABLE schedules
ADD session_type VARCHAR(50);

UPDATE schedules
SET session_type = 'LECTURE';

ALTER TABLE schedules
ALTER COLUMN session_type SET NOT NULL;