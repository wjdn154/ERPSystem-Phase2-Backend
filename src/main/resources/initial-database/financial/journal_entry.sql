INSERT INTO journal_entry (
    journal_entry_type_setup_id,
    code,
    name,
    transaction_type
) VALUES
      (1,'1','현금','Sales'),
      (2,'2','외상','Sales'),
      (3,'3','카드','Sales'),
      (4,'1','현금','Purchase'),
      (5,'2','외상','Purchase'),
      (6,'3','카드','Purchase');


SELECT code, name
FROM journal_entry
GROUP BY code,name;

SELECT DISTINCT code, name
FROM journal_entry
WHERE name IN ('현금', '외상', '카드');