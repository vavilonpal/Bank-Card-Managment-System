CREATE TABLE users
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    email         VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    role          VARCHAR(50)         NOT NULL,
    full_name     VARCHAR(150),
    phone_number  VARCHAR(30)  UNIQUE NOT NULL,
    is_active     BOOLEAN          DEFAULT TRUE,

    created_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP        DEFAULT CURRENT_TIMESTAMP
);