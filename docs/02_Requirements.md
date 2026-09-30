# 02 Requirements

## Functional
1. Add, view, update, delete income and expense transactions
2. Filter transactions by INCOME or EXPENSE
3. Add, view, update, delete categories
4. Create, view, delete monthly budgets per expense category
5. Show total income, total expenses and remaining balance
6. Show this month's expenses
7. Show spending by category and recent transactions
8. Show monthly income vs expense report
9. Validate input (name, email, amount, date, budget) and return clear errors

## Non-functional
- **Simple:** layered architecture that is easy to explain
- **Accurate:** BigDecimal for money, never double
- **Responsive:** UI works on desktop and mobile
- **Maintainable:** Controller / Service / Repository separated
- **Testable:** every endpoint can be tested with Postman
- **Reliable:** proper HTTP status codes (200, 201, 204, 400, 404, 409)
