-- Initial schema for events, fighters, weight divisions, and fights.

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(150) NOT NULL,
    date DATE NOT NULL,
    location VARCHAR(255) NOT NULL
);

CREATE TABLE fighters (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(150) NOT NULL,
    nickname VARCHAR(100),
    height_cm INTEGER CHECK (height_cm IS NULL OR height_cm > 0),
    reach_cm INTEGER CHECK (reach_cm IS NULL OR reach_cm > 0),
    country VARCHAR(100)
);

CREATE TABLE weight_divisions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE fighter_weight_divisions (
    fighter_id UUID NOT NULL,
    weight_division_id UUID NOT NULL,

    CONSTRAINT pk_fighter_weight_divisions
        PRIMARY KEY (fighter_id, weight_division_id),
    CONSTRAINT fk_fighter_weight_divisions_fighter
        FOREIGN KEY (fighter_id) REFERENCES fighters (id),
    CONSTRAINT fk_fighter_weight_divisions_weight_division
        FOREIGN KEY (weight_division_id) REFERENCES weight_divisions (id)
);

CREATE TABLE fights (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    event_id UUID NOT NULL,
    weight_division_id UUID NOT NULL,
    fighter1_id UUID NOT NULL,
    fighter2_id UUID NOT NULL,
    scheduled_rounds INTEGER NOT NULL CHECK (scheduled_rounds > 0),
    title_fight BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_fight_event
        FOREIGN KEY (event_id) REFERENCES events (id),
    CONSTRAINT fk_fight_weight_division
        FOREIGN KEY (weight_division_id) REFERENCES weight_divisions (id),
    CONSTRAINT fk_fight_fighter1
        FOREIGN KEY (fighter1_id) REFERENCES fighters (id),
    CONSTRAINT fk_fight_fighter2
        FOREIGN KEY (fighter2_id) REFERENCES fighters (id),
    CONSTRAINT chk_fight_distinct_fighters
        CHECK (fighter1_id <> fighter2_id)
);

CREATE INDEX idx_fights_event_id
    ON fights (event_id);

CREATE INDEX idx_fights_weight_division_id
    ON fights (weight_division_id);

CREATE INDEX idx_fights_fighter1_id
    ON fights (fighter1_id);

CREATE INDEX idx_fights_fighter2_id
    ON fights (fighter2_id);

CREATE INDEX idx_fighter_weight_divisions_weight_division_id
    ON fighter_weight_divisions (weight_division_id);
