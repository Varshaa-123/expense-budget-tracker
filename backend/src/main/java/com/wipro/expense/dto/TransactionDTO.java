package com.wipro.expense.dto;

import com.wipro.expense.entity.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO = Data Transfer Object. It is the exact shape of JSON we send/receive, so we
 * never expose full User/Category entities. A "record" is a short Java class with
 * only fields (getters like transactionId() are created automatically).
 * userName and categoryName are only used in responses; requests just send the ids.
 */
public record TransactionDTO(
        Long transactionId,
        @NotNull(message = "userId is required") Long userId,
        String userName,
        @NotNull(message = "categoryId is required") Long categoryId,
        String categoryName,
        @NotNull(message = "Amount is required") @Positive(message = "Amount must be greater than 0") BigDecimal amount,
        @NotNull(message = "Transaction type is required (INCOME or EXPENSE)") TransactionType transactionType,
        @Size(max = 255, message = "Description is too long") String description,
        @NotNull(message = "Transaction date is required") LocalDate transactionDate) {
}
