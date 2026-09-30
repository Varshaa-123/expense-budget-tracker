package com.wipro.expense.dto;

import java.math.BigDecimal;

public record MonthlySummaryDTO(Integer year, Integer month, BigDecimal income, BigDecimal expenses) {
}
