# 03 Architecture

```
React UI  ->  Axios (services/api.js)  ->  REST Controller  ->  Service  ->  Repository  ->  MySQL
```

| Layer | Responsibility |
|---|---|
| Controller | Receives HTTP requests, validates with @Valid, calls the service. No SQL, no business logic |
| Service | Business rules (e.g. INCOME transaction needs INCOME category; no duplicate budgets) |
| Repository | Talks to MySQL: JPA derived queries and native SQL report queries |
| Entity | Java classes mapped to tables |
| DTO | Shape of JSON in and out; hides internal entity details |
| Exception | `ResourceNotFoundException` and `GlobalExceptionHandler` return clean JSON errors |

**Why BigDecimal:** double gives rounding errors (0.1 + 0.2 = 0.30000000000000004). Money must be exact.
**Why LocalDate:** transactions only need a day, not a time.
**Why enums:** only INCOME or EXPENSE are allowed, so typos are impossible.
**Why DTOs:** we send only the fields the UI needs, and add calculated values such as `spent`.
**CORS:** controllers allow requests from http://localhost:5173 (the React dev server).
