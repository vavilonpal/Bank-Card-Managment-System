CREATE TABLE bank_cards
(
    id               UUID PRIMARY KEY            DEFAULT gen_random_uuid(),
    user_id          UUID               NOT NULL REFERENCES users (id) ON DELETE CASCADE,

    card_balance     NUMERIC(19, 2)     NOT NULL DEFAULT 0.00,
    card_number      VARCHAR(19) UNIQUE NOT NULL,
    card_holder      VARCHAR(150)       NOT NULL,
    cvv_hash         VARCHAR(255)       NOT NULL,
    expiration_month INT                NOT NULL CHECK (expiration_month BETWEEN 1 AND 12),
    expiration_year  INT                NOT NULL CHECK (expiration_year >= EXTRACT(YEAR FROM CURRENT_DATE)),

    card_status      VARCHAR(50)        NOT NULL,

    created_at       TIMESTAMP                   DEFAULT CURRENT_TIMESTAMP
);