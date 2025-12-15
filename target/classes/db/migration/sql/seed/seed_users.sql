-- liquibase formatted sql
-- changeset sergey:100 context:dev,test

INSERT INTO users (email, password_hash, role_id, full_name, phone_number, is_active)
VALUES
    ('user1@example.com', '$2a$10$dummyhash1', 2, 'User One', '+10000000001', true),
    ('user2@example.com', '$2a$10$dummyhash2', 2, 'User Two', '+10000000002', true),
    ('user3@example.com', '$2a$10$dummyhash3', 2, 'User Three', '+10000000003', true),
    ('user4@example.com', '$2a$10$dummyhash4', 2, 'User Four', '+10000000004', true),
    ('user5@example.com', '$2a$10$dummyhash5', 2, 'User Five', '+10000000005', true),
    ('user6@example.com', '$2a$10$dummyhash6', 2, 'User Six', '+10000000006', true),
    ('user7@example.com', '$2a$10$dummyhash7', 2, 'User Seven', '+10000000007', true),
    ('user8@example.com', '$2a$10$dummyhash8', 2, 'User Eight', '+10000000008', true),
    ('user9@example.com', '$2a$10$dummyhash9', 2, 'User Nine', '+10000000009', true),
    ('user10@example.com', '$2a$10$dummyhash10', 2, 'User Ten', '+10000000010', true);
