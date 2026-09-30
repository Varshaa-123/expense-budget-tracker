package com.wipro.expense.repository;

import com.wipro.expense.entity.Transaction;
import com.wipro.expense.entity.TransactionType;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // ---- Simple derived queries (Spring writes the SQL from the method name) ----
    List<Transaction> findAllByOrderByTransactionDateDescTransactionIdDesc();

    List<Transaction> findByTransactionTypeOrderByTransactionDateDescTransactionIdDesc(TransactionType type);

    // ---- REPORT 1 (JOIN): transactions with user and category information ----
    @Query(value = """
            SELECT t.transaction_id, t.user_id, u.user_name, t.category_id, c.category_name,
                   t.amount, t.transaction_type, t.description, t.transaction_date
            FROM transactions t
            JOIN users u      ON t.user_id = u.user_id
            JOIN categories c ON t.category_id = c.category_id
            ORDER BY t.transaction_date DESC, t.transaction_id DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<Object[]> findRecentWithDetails(@Param("limit") int limit);

    // ---- REPORT 2 (SUM): total for one type (INCOME or EXPENSE) ----
    @Query(value = "SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE transaction_type = :type",
            nativeQuery = true)
    BigDecimal sumByType(@Param("type") String type);

    // ---- REPORT 3 (SUM + month filter): used for "monthly expenses" ----
    @Query(value = """
            SELECT COALESCE(SUM(amount), 0) FROM transactions
            WHERE transaction_type = :type
              AND MONTH(transaction_date) = :month AND YEAR(transaction_date) = :year
            """, nativeQuery = true)
    BigDecimal sumByTypeAndMonth(@Param("type") String type, @Param("month") int month, @Param("year") int year);

    // ---- REPORT 4 (JOIN + GROUP BY + SUM + COUNT): spending by category ----
    @Query(value = """
            SELECT c.category_name, SUM(t.amount) AS total, COUNT(*) AS txn_count
            FROM transactions t
            JOIN categories c ON t.category_id = c.category_id
            WHERE t.transaction_type = 'EXPENSE'
            GROUP BY c.category_id, c.category_name
            ORDER BY total DESC
            """, nativeQuery = true)
    List<Object[]> findCategorySpending();

    // ---- REPORT 5 (JOIN + GROUP BY + HAVING + SUBQUERY): categories spending MORE than the average category ----
    @Query(value = """
            SELECT c.category_name, SUM(t.amount) AS total, COUNT(*) AS txn_count
            FROM transactions t
            JOIN categories c ON t.category_id = c.category_id
            WHERE t.transaction_type = 'EXPENSE'
            GROUP BY c.category_id, c.category_name
            HAVING SUM(t.amount) > (
                SELECT AVG(category_total) FROM (
                    SELECT SUM(amount) AS category_total
                    FROM transactions
                    WHERE transaction_type = 'EXPENSE'
                    GROUP BY category_id
                ) AS totals
            )
            ORDER BY total DESC
            """, nativeQuery = true)
    List<Object[]> findCategoriesAboveAverageSpending();

    // ---- REPORT 6 (GROUP BY month): income vs expenses for every month ----
    @Query(value = """
            SELECT YEAR(transaction_date) AS yr, MONTH(transaction_date) AS mth,
                   SUM(CASE WHEN transaction_type = 'INCOME'  THEN amount ELSE 0 END) AS income,
                   SUM(CASE WHEN transaction_type = 'EXPENSE' THEN amount ELSE 0 END) AS expenses
            FROM transactions
            GROUP BY YEAR(transaction_date), MONTH(transaction_date)
            ORDER BY yr, mth
            """, nativeQuery = true)
    List<Object[]> findMonthlySummary();

    // ---- Used by budgets: how much was spent in one category in one month ----
    @Query(value = """
            SELECT COALESCE(SUM(amount), 0) FROM transactions
            WHERE user_id = :userId AND category_id = :categoryId AND transaction_type = 'EXPENSE'
              AND MONTH(transaction_date) = :month AND YEAR(transaction_date) = :year
            """, nativeQuery = true)
    BigDecimal sumExpenseForBudget(@Param("userId") Long userId, @Param("categoryId") Long categoryId,
                                   @Param("month") int month, @Param("year") int year);
}
