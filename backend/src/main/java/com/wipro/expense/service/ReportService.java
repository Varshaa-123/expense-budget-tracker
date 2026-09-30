package com.wipro.expense.service;

import com.wipro.expense.dto.CategorySpendingDTO;
import com.wipro.expense.dto.DashboardSummaryDTO;
import com.wipro.expense.dto.MonthlySummaryDTO;
import com.wipro.expense.dto.TransactionDTO;
import com.wipro.expense.entity.TransactionType;
import com.wipro.expense.repository.TransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

/** Turns the raw rows of the report queries into DTOs. */
@Service
public class ReportService {

    private final TransactionRepository transactionRepository;

    public ReportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal getTotalIncome() {
        return transactionRepository.sumByType("INCOME");
    }

    public BigDecimal getTotalExpenses() {
        return transactionRepository.sumByType("EXPENSE");
    }

    public DashboardSummaryDTO getDashboardSummary() {
        BigDecimal income = getTotalIncome();
        BigDecimal expenses = getTotalExpenses();
        LocalDate today = LocalDate.now();
        BigDecimal monthlyExpenses = transactionRepository.sumByTypeAndMonth(
                "EXPENSE", today.getMonthValue(), today.getYear());
        return new DashboardSummaryDTO(income, expenses, income.subtract(expenses), monthlyExpenses);
    }

    public List<CategorySpendingDTO> getCategorySpending() {
        return transactionRepository.findCategorySpending().stream().map(this::toCategorySpending).toList();
    }

    public List<CategorySpendingDTO> getCategoriesAboveAverage() {
        return transactionRepository.findCategoriesAboveAverageSpending().stream()
                .map(this::toCategorySpending).toList();
    }

    public List<MonthlySummaryDTO> getMonthlySummary() {
        return transactionRepository.findMonthlySummary().stream().map(row -> new MonthlySummaryDTO(
                ((Number) row[0]).intValue(), ((Number) row[1]).intValue(),
                new BigDecimal(row[2].toString()), new BigDecimal(row[3].toString()))).toList();
    }

    public List<TransactionDTO> getRecentTransactions(int limit) {
        return transactionRepository.findRecentWithDetails(limit).stream().map(row -> new TransactionDTO(
                ((Number) row[0]).longValue(),
                ((Number) row[1]).longValue(), (String) row[2],
                ((Number) row[3]).longValue(), (String) row[4],
                new BigDecimal(row[5].toString()),
                TransactionType.valueOf(row[6].toString()),
                (String) row[7],
                LocalDate.parse(row[8].toString().substring(0, 10)))).toList();
    }

    private CategorySpendingDTO toCategorySpending(Object[] row) {
        return new CategorySpendingDTO((String) row[0], new BigDecimal(row[1].toString()),
                ((Number) row[2]).longValue());
    }
}
