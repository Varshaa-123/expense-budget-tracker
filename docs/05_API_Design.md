# 05 API Design (base URL: http://localhost:8091)

| Method | URL | Description | Success code |
|---|---|---|---|
| POST | /api/users | Create user | 201 |
| GET | /api/users, /api/users/{id} | List / get user | 200 |
| PUT | /api/users/{id} | Update user | 200 |
| DELETE | /api/users/{id} | Delete user | 204 |
| POST/GET/PUT/DELETE | /api/categories[/{id}] | Category CRUD | 201/200/200/204 |
| POST/GET/PUT/DELETE | /api/transactions[/{id}] | Transaction CRUD. `GET ?type=INCOME` filters | 201/200/200/204 |
| POST/GET/PUT/DELETE | /api/budgets[/{id}] | Budget CRUD (GET includes `spent`) | 201/200/200/204 |
| GET | /api/reports/dashboard | Totals and balance | 200 |
| GET | /api/reports/total-income | Total income | 200 |
| GET | /api/reports/total-expenses | Total expenses | 200 |
| GET | /api/reports/category-spending | Spending per category | 200 |
| GET | /api/reports/above-average-categories | Categories above average | 200 |
| GET | /api/reports/monthly-summary | Income/expenses per month | 200 |
| GET | /api/reports/recent-transactions?limit=5 | Latest transactions | 200 |

## Error format
```json
{ "timestamp": "...", "status": 404, "error": "Not Found", "message": "Transaction not found with id 99" }
```
400 = validation/business rule, 404 = id not found, 409 = record in use or duplicate.
