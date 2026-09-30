# 04 Database Design (database: expense_tracker)

## Tables
**users:** user_id (PK), user_name, email (unique)
**categories:** category_id (PK), category_name (unique), category_type (INCOME/EXPENSE)
**transactions:** transaction_id (PK), user_id (FK), category_id (FK), amount DECIMAL(12,2), transaction_type, description, transaction_date
**budgets:** budget_id (PK), user_id (FK), category_id (FK), amount, month, year (unique per user+category+month+year)

## Relationships
```
users 1 ---- * transactions        categories 1 ---- * transactions
users 1 ---- * budgets             categories 1 ---- * budgets
```
In JPA each child uses `@ManyToOne` + `@JoinColumn`. The parent side (`@OneToMany`) is intentionally left out to keep JSON simple and avoid infinite loops.

## Report queries (in the repositories)
| # | Query | Concepts |
|---|---|---|
| 1 | Recent transactions with user and category | JOIN (2 joins) |
| 2 | Total income / total expenses | SUM, COALESCE |
| 3 | Monthly expenses | SUM + MONTH/YEAR filter |
| 4 | Spending by category | JOIN, GROUP BY, SUM, COUNT |
| 5 | Categories above average spending | JOIN, GROUP BY, HAVING, subquery |
| 6 | Income vs expenses per month | GROUP BY, SUM with CASE |
| 7 | Budgets with category name and amount spent | JOIN + correlated subquery |
