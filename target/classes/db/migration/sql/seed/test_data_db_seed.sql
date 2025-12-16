CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Сиды для таблицы roles
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('USER');


-- Сиды для таблицы users
-- Пароли закодированы с помощью BCryptPasswordEncoder

INSERT INTO users (id, email, password_hash, role_id, full_name, phone_number, is_active)
VALUES
-- Пользователь 1
(
    gen_random_uuid(),
    'admin1@example.com',
    crypt('password123', gen_salt('bf')),
    (SELECT id FROM roles WHERE name = 'ADMIN'),
    'Admin One',
    '+37360000001',
    true
),
-- Пароль: password123

-- Пользователь 2
(
    gen_random_uuid(),
    'admin2@example.com',
    crypt('secret456', gen_salt('bf')),
    (SELECT id FROM roles WHERE name = 'ADMIN'),
    'Admin Two',
    '+37360000002',
    true
),
-- Пароль: secret456

-- Пользователь 3
(
    gen_random_uuid(),
    'user1@example.com',
    crypt('mypass789', gen_salt('bf')),
    (SELECT id FROM roles WHERE name = 'USER'),
    'User One',
    '+37360000003',
    true
),
-- Пароль: mypass789

-- Пользователь 4
(
    gen_random_uuid(),
    'user2@example.com',
    crypt('qwerty123', gen_salt('bf')),
    (SELECT id FROM roles WHERE name = 'USER'),
    'User Two',
    '+37360000004',
    true
),
-- Пароль: qwerty123

-- Пользователь 5
(
    gen_random_uuid(),
    'user3@example.com',
    crypt('12345678', gen_salt('bf')),
    (SELECT id FROM roles WHERE name = 'USER'),
    'User Three',
    '+37360000005',
    true
);
-- Пароль: 12345678



-- Сиды для таблицы bank_cards
-- card_status можно использовать, например, 'ACTIVE'

-- Карты для admin1
INSERT INTO bank_cards (user_id, card_balance, card_number, card_holder, cvv_hash, expiration_month, expiration_year, card_status)
VALUES
    ((SELECT id FROM users WHERE email='admin1@example.com'), 1000.00, '4000000000000001', 'Admin One', '$2a$10$exampleCVVhash1', 12, 2030, 'ACTIVE'),
    ((SELECT id FROM users WHERE email='admin1@example.com'), 2500.00, '4000000000000002', 'Admin One', '$2a$10$exampleCVVhash2', 11, 2031, 'ACTIVE');

-- Карты для admin2
INSERT INTO bank_cards (user_id, card_balance, card_number, card_holder, cvv_hash, expiration_month, expiration_year, card_status)
VALUES
    ((SELECT id FROM users WHERE email='admin2@example.com'), 1500.00, '4000000000000003', 'Admin Two', '$2a$10$exampleCVVhash3', 10, 2030, 'ACTIVE'),
    ((SELECT id FROM users WHERE email='admin2@example.com'), 3000.00, '4000000000000004', 'Admin Two', '$2a$10$exampleCVVhash4', 9, 2032, 'ACTIVE');

-- Карты для user1
INSERT INTO bank_cards (user_id, card_balance, card_number, card_holder, cvv_hash, expiration_month, expiration_year, card_status)
VALUES
    ((SELECT id FROM users WHERE email='user1@example.com'), 500.00, '4000000000000005', 'User One', '$2a$10$exampleCVVhash5', 8, 2029, 'ACTIVE'),
    ((SELECT id FROM users WHERE email='user1@example.com'), 1200.00, '4000000000000006', 'User One', '$2a$10$exampleCVVhash6', 7, 2031, 'ACTIVE');

-- Карты для user2
INSERT INTO bank_cards (user_id, card_balance, card_number, card_holder, cvv_hash, expiration_month, expiration_year, card_status)
VALUES
    ((SELECT id FROM users WHERE email='user2@example.com'), 800.00, '4000000000000007', 'User Two', '$2a$10$exampleCVVhash7', 6, 2030, 'ACTIVE'),
    ((SELECT id FROM users WHERE email='user2@example.com'), 2200.00, '4000000000000008', 'User Two', '$2a$10$exampleCVVhash8', 5, 2032, 'ACTIVE');

-- Карты для user3
INSERT INTO bank_cards (user_id, card_balance, card_number, card_holder, cvv_hash, expiration_month, expiration_year, card_status)
VALUES
    ((SELECT id FROM users WHERE email='user3@example.com'), 300.00, '4000000000000009', 'User Three', '$2a$10$exampleCVVhash9', 4, 2031, 'ACTIVE'),
    ((SELECT id FROM users WHERE email='user3@example.com'), 950.00, '4000000000000010', 'User Three', '$2a$10$exampleCVVhash10', 3, 2033, 'ACTIVE');


