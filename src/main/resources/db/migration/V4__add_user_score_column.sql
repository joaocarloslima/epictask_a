ALTER TABLE epicuser
    ADD score INTEGER DEFAULT 0;

UPDATE epicuser
    SET score = 0;