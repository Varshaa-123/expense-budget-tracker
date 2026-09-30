# 06 Workflow

1. User opens the React app. The Dashboard calls `/api/reports/dashboard`, `category-spending`, `recent-transactions` and `budgets`.
2. User clicks **Add Transaction**, fills the form and presses Save.
3. Axios sends `POST /api/transactions`.
4. `TransactionController` validates the JSON (`@Valid`) and calls `TransactionService`.
5. The service finds the user and category, checks the category type matches the transaction type, then saves through `TransactionRepository`.
6. MySQL stores the row. The API returns 201 and the saved transaction.
7. The dashboard re-reads the report queries, so totals, charts and budget bars are always up to date.

If anything is wrong, `GlobalExceptionHandler` returns a clear JSON message and the React form shows it in a red box.
