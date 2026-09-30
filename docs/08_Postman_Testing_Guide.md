# 08 Postman Testing Guide

Base URL: `http://localhost:8091`. For POST/PUT choose Body > raw > JSON.
Sample data already has user 1 (Varsha) and categories 1-8 (1 Food, 2 Transport, 3 Shopping, 4 Entertainment, 5 Education, 6 Salary, 7 Freelance, 8 Bills). Income categories: 6, 7.

## 1. Create user: POST /api/users  (201)
```json
{ "userName": "Arun", "email": "arun@example.com" }
```
## 2. Get users: GET /api/users  (200)
## 3. Create category: POST /api/categories  (201)
```json
{ "categoryName": "Health", "categoryType": "EXPENSE" }
```
## 4. Get categories: GET /api/categories  (200)
## 5. Create income transaction: POST /api/transactions  (201)
```json
{ "userId": 1, "categoryId": 7, "amount": 5000, "transactionType": "INCOME",
  "description": "Logo design", "transactionDate": "2026-09-15" }
```
## 6. Create expense transaction: POST /api/transactions  (201)
```json
{ "userId": 1, "categoryId": 1, "amount": 500, "transactionType": "EXPENSE",
  "description": "Lunch", "transactionDate": "2026-09-16" }
```
## 7. Get transactions: GET /api/transactions  (also `?type=INCOME` and `?type=EXPENSE`)
## 8. Update transaction: PUT /api/transactions/{id}  (200)
```json
{ "userId": 1, "categoryId": 1, "amount": 650, "transactionType": "EXPENSE",
  "description": "Lunch with friends", "transactionDate": "2026-09-16" }
```
## 9. Delete transaction: DELETE /api/transactions/{id}  (204)
## 10. Create budget: POST /api/budgets  (201)
```json
{ "userId": 1, "categoryId": 8, "amount": 5000, "month": 10, "year": 2026 }
```
## 11. Get budgets: GET /api/budgets  (each has `spent`)
## 12. Dashboard: GET /api/reports/dashboard
## 13. Total income: GET /api/reports/total-income
## 14. Total expenses: GET /api/reports/total-expenses
## 15. Category spending: GET /api/reports/category-spending
## 16. Recent transactions: GET /api/reports/recent-transactions?limit=5

## Error tests
| Request | Expected |
|---|---|
| GET /api/transactions/9999 | 404 "Transaction not found with id 9999" |
| POST /api/users with `"email": "abc"` | 400 email format invalid |
| POST /api/transactions with `"amount": -5` | 400 amount must be greater than 0 |
| POST an INCOME transaction with categoryId 1 (Food) | 400 category/type mismatch |
| DELETE /api/categories/1 (has transactions) | 409 record in use |
| POST the same budget twice | 400 budget already exists |
