INSERT INTO unresolved_voucher (
    account_id,
    transaction_description,
    voucher_number,
    voucher_type,
    debit_amount,
    credit_amount,
    voucher_date,
    voucher_registration_time,
    approval_status,
    voucher_kind
) VALUES
      ('138', '차변테스트1', '1', 'Debit', 50000000.00, 0.00, '2024-05-07', '2024-08-17 15:34:48', 'Pending','General'),
      ('138', '대변테스트2', '1', 'Credit', 0.00, 50000000.00, '2024-05-07', '2024-08-17 15:34:48', 'Pending','General'),
      ('138', '차변테스트1', '1', 'Debit', 50000000.00, 0.00, '2024-05-08', '2024-08-17 15:34:48', 'Pending','General'),
      ('138', '대변테스트2', '1', 'Credit', 0.00, 50000000.00, '2024-05-08', '2024-08-17 15:34:48', 'Pending','General'),
      ('138', '차변테스트1', '2', 'Debit', 50000000.00, 0.00, '2024-05-07', '2024-08-17 15:34:48', 'Pending','General'),
      ('138', '대변테스트2', '2', 'Credit', 0.00, 50000000.00, '2024-05-07', '2024-08-17 15:34:48', 'Pending','General'),
      ('138', '차변테스트1', '1', 'Debit', 50000000.00, 0.00, '2024-05-10', '2024-08-17 15:34:48', 'Pending','General'),
      ('138', '대변테스트2', '1', 'Credit', 0.00, 50000000.00, '2024-05-10', '2024-08-17 15:34:48', 'Pending','General');