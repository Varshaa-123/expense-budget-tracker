package com.wipro.expense.dto;

import java.math.BigDecimal;

public record CategorySpendingDTO(String categoryName, BigDecimal amount, Long transactionCount) {
}
