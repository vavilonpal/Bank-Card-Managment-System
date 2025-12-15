CREATE TABLE card_blocks
(
    id            BIGSERIAL PRIMARY KEY,
    card_id       UUID      NOT NULL REFERENCES bank_cards (id) ON DELETE CASCADE,
    requested_by  UUID        NOT NULL,
    reason        VARCHAR(50) NOT NULL,                                              -- Причина блокировки (lost, stolen, fraud, userRequest)
    notes         TEXT,                                                              -- Дополнительные комментарии
    temporary     BOOLEAN                  DEFAULT FALSE,                            -- Временная или постоянная блокировка
    notify_client BOOLEAN                  DEFAULT TRUE,                             -- Нужно ли уведомлять клиента
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT now(),
    blocked_at    TIMESTAMP WITH TIME ZONE,
    status        VARCHAR(20)              DEFAULT 'PENDING'
);