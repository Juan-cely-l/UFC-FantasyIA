ALTER TABLE fighters
    ADD COLUMN age INTEGER CHECK (age IS NULL OR age > 0);
