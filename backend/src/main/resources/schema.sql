-- Tables (IF NOT EXISTS so restarting never fails)
CREATE TABLE IF NOT EXISTS users (
    user_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    email     VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS categories (
    category_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE,
    category_type VARCHAR(20)  NOT NULL          -- INCOME or EXPENSE
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT         NOT NULL,
    category_id      BIGINT         NOT NULL,
    amount           DECIMAL(12,2)  NOT NULL,
    transaction_type VARCHAR(20)    NOT NULL,     -- INCOME or EXPENSE
    description      VARCHAR(255),
    transaction_date DATE           NOT NULL,
    FOREIGN KEY (user_id)     REFERENCES users(user_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE IF NOT EXISTS budgets (
    budget_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT        NOT NULL,
    category_id BIGINT        NOT NULL,
    amount      DECIMAL(12,2) NOT NULL,
    month       INT           NOT NULL,
    year        INT           NOT NULL,
    UNIQUE (user_id, category_id, month, year),
    FOREIGN KEY (user_id)     REFERENCES users(user_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
