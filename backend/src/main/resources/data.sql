-- Sample data. INSERT IGNORE + fixed IDs = no duplicates when the app restarts.
-- Dates are relative to today so the dashboard always shows "this month" data.

INSERT IGNORE INTO users (user_id, user_name, email) VALUES (1, 'Varsha', 'varsha@example.com');

INSERT IGNORE INTO categories (category_id, category_name, category_type) VALUES
(1, 'Food', 'EXPENSE'), (2, 'Transport', 'EXPENSE'), (3, 'Shopping', 'EXPENSE'),
(4, 'Entertainment', 'EXPENSE'), (5, 'Education', 'EXPENSE'), (6, 'Salary', 'INCOME'),
(7, 'Freelance', 'INCOME'), (8, 'Bills', 'EXPENSE');

-- THIS MONTH: income 45000, expenses 17800
INSERT IGNORE INTO transactions (transaction_id, user_id, category_id, amount, transaction_type, description, transaction_date) VALUES
(1, 1, 6, 45000, 'INCOME',  'Monthly salary',        DATE_FORMAT(CURDATE(), '%Y-%m-01')),
(2, 1, 1, 2200,  'EXPENSE', 'Groceries',             DATE_ADD(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 1 DAY)),
(3, 1, 1, 1800,  'EXPENSE', 'Restaurant and snacks', DATE_ADD(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 5 DAY)),
(4, 1, 1, 2200,  'EXPENSE', 'Weekly groceries',      DATE_ADD(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 12 DAY)),
(5, 1, 2, 1500,  'EXPENSE', 'Metro card recharge',   DATE_ADD(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 2 DAY)),
(6, 1, 2, 1000,  'EXPENSE', 'Cab rides',             DATE_ADD(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 9 DAY)),
(7, 1, 3, 4300,  'EXPENSE', 'New shoes',             DATE_ADD(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 7 DAY)),
(8, 1, 4, 1800,  'EXPENSE', 'Movie and streaming',   DATE_ADD(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 10 DAY)),
(9, 1, 5, 3000,  'EXPENSE', 'Online course fee',     DATE_ADD(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 3 DAY));

-- LAST MONTH (for monthly reports)
INSERT IGNORE INTO transactions (transaction_id, user_id, category_id, amount, transaction_type, description, transaction_date) VALUES
(10, 1, 6, 45000, 'INCOME',  'Monthly salary',       DATE_SUB(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 28 DAY)),
(11, 1, 7, 8000,  'INCOME',  'Freelance project',    DATE_SUB(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 20 DAY)),
(12, 1, 1, 5800,  'EXPENSE', 'Food for the month',   DATE_SUB(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 25 DAY)),
(13, 1, 2, 2200,  'EXPENSE', 'Fuel and cabs',        DATE_SUB(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 22 DAY)),
(14, 1, 3, 3500,  'EXPENSE', 'Clothes',              DATE_SUB(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 18 DAY)),
(15, 1, 8, 4000,  'EXPENSE', 'Electricity and internet', DATE_SUB(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 15 DAY)),
(16, 1, 4, 1500,  'EXPENSE', 'Weekend outing',       DATE_SUB(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 12 DAY)),
(17, 1, 5, 2000,  'EXPENSE', 'Books',                DATE_SUB(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL 8 DAY));

-- Budgets: this month (Shopping is intentionally over budget) and last month
INSERT IGNORE INTO budgets (budget_id, user_id, category_id, amount, month, year) VALUES
(1, 1, 1, 7000, MONTH(CURDATE()), YEAR(CURDATE())),
(2, 1, 2, 3000, MONTH(CURDATE()), YEAR(CURDATE())),
(3, 1, 3, 4000, MONTH(CURDATE()), YEAR(CURDATE())),
(4, 1, 4, 2000, MONTH(CURDATE()), YEAR(CURDATE())),
(5, 1, 5, 3000, MONTH(CURDATE()), YEAR(CURDATE())),
(6, 1, 1, 6000, MONTH(DATE_SUB(CURDATE(), INTERVAL 1 MONTH)), YEAR(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))),
(7, 1, 2, 2500, MONTH(DATE_SUB(CURDATE(), INTERVAL 1 MONTH)), YEAR(DATE_SUB(CURDATE(), INTERVAL 1 MONTH)));
