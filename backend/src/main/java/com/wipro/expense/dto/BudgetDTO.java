package com.wipro.expense.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/** "spent" is calculated by the backend (sum of expenses in that category and month). */
public record BudgetDTO(
        Long budgetId,
        @NotNull(message = "userId is required") Long userId,
        @NotNull(message = "categoryId is required") Long categoryId,
        String categoryName,
        @NotNull(message = "Budget amount is required") @Positive(message = "Budget amount must be greater than 0") BigDecimal amount,
        @NotNull(message = "Month is required") @Min(value = 1, message = "Month must be 1-12") @Max(value = 12, message = "Month must be 1-12") Integer month,
        @NotNull(message = "Year is required") @Min(value = 2000, message = "Year must be 2000 or later") Integer year,
        BigDecimal spent) {
}
