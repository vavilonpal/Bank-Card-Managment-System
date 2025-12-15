INSERT INTO bank_cards (user_id, card_balance, card_number, card_holder, cvv_hash, expiration_month, expiration_year, card_status)
SELECT id, 1000.00, '4111131111111' || LPAD(i::text, 2, '0'), full_name, 'dummycvvhash' || i::text,
       (i % 12) + 1, 2028 + (i % 3), 'ACTIVE'
FROM users, generate_series(1,2) AS i
ORDER BY id, i;
