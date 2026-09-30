package com.wipro.expense.repository;

import com.wipro.expense.entity.Budget;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    boolean existsByUserUserIdAndCategoryCategoryIdAndMonthAndYear(Long userId, Long categoryId, Integer month, Integer year);

    // ---- REPORT 7 (JOIN + SUBQUERY): budgets with category name and how much was already spent ----
    @Query(value = """
            SELECT b.budget_id, b.user_id, b.category_id, c.category_name, b.amount, b.month, b.year,(SELECT COALESCE(SUM(t.amount), 0)FROM transactions t
                    WHERE t.user_id = b.user_id AND t.category_id = b.category_id
                    AND t.transaction_type = 'EXPENSE'
                    AND MONTH(t.transaction_date) = b.month
                    AND YEAR(t.transaction_date) = b.year) AS spent
            FROM budgets b
            JOIN categories c ON b.category_id = c.category_id
            ORDER BY b.year DESC, b.month DESC, c.category_name
            """, nativeQuery = true)
    List<Object[]> findAllWithSpent();
}
