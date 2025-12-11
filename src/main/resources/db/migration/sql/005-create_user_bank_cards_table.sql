CREATE TABLE user_bank_cards
(
    user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    card_id BIGINT NOT NULL REFERENCES bank_cards (id) ON DELETE CASCADE,

    PRIMARY KEY (user_id, card_id)
);