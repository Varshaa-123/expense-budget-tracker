# Expense & Budget Tracker

A full-stack web app to record income and expenses, organise them by category, set monthly budgets and view dashboard reports.

## Features
- Income and expense transactions (add, view, edit, delete, filter by type)
- Categories (INCOME / EXPENSE) with full CRUD
- Monthly budgets with progress bars (green / amber / red)
- Dashboard: total income, total expenses, balance, this month's expenses, category spending, recent transactions
- Reports: monthly income vs expenses, spending by category, categories above average spending

## Technology Stack
| Layer | Technology |
|---|---|
| Frontend | React 18, Vite, React Router, Axios, plain CSS |
| Backend | Java 21, Spring Boot 3.5, Spring Web, Spring Data JPA, Validation |
| Database | MySQL |
| API testing | Postman |

## Architecture
```
React (port 5173) -> Axios -> Controller -> Service -> Repository -> MySQL (port 3306)
                                 (Spring Boot REST API, port 8091)
```

## Database
Tables: `users`, `categories`, `transactions`, `budgets`. See `docs/04_Database_Design.md`.
Tables are created by `schema.sql`; sample data is loaded from `data.sql` (user Varsha, 8 categories, two months of transactions, budgets).

## How to run the backend
1. Install Java 21, Maven and MySQL. Start MySQL.
2. Open `backend/src/main/resources/application.properties` and replace `YOUR_PASSWORD` with your MySQL root password.
3. Run:
   ```
   cd backend
   mvn spring-boot:run
   ```
4. Check: http://localhost:8091/api/reports/dashboard should return JSON.

## How to run the frontend
```
cd frontend
npm install
npm run dev
```
Open http://localhost:5173 (the backend must be running).

## API endpoints
| Resource | Endpoints |
|---|---|
| Users | `POST/GET /api/users`, `GET/PUT/DELETE /api/users/{id}` |
| Categories | `POST/GET /api/categories`, `GET/PUT/DELETE /api/categories/{id}` |
| Transactions | `POST/GET /api/transactions` (`?type=INCOME` or `EXPENSE`), `GET/PUT/DELETE /api/transactions/{id}` |
| Budgets | `POST/GET /api/budgets`, `GET/PUT/DELETE /api/budgets/{id}` |
| Reports | `GET /api/reports/dashboard`, `total-income`, `total-expenses`, `category-spending`, `above-average-categories`, `monthly-summary`, `recent-transactions?limit=5` |

## Postman testing
Full request list with JSON bodies: `docs/08_Postman_Testing_Guide.md`.

## Future Deployment (not done yet)
Docker, Docker Compose and cloud deployment are planned after the local version is verified. See `docs/07_Future_Enhancements.md`.
