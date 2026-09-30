package com.wipro.expense.dto;

import java.math.BigDecimal;

public record DashboardSummaryDTO(BigDecimal totalIncome, BigDecimal totalExpenses,
                                  BigDecimal remainingBalance, BigDecimal monthlyExpenses) {
}
