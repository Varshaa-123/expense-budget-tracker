package com.wipro.expense.controller;

import com.wipro.expense.dto.CategorySpendingDTO;
import com.wipro.expense.dto.DashboardSummaryDTO;
import com.wipro.expense.dto.MonthlySummaryDTO;
import com.wipro.expense.dto.TransactionDTO;
import com.wipro.expense.service.ReportService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:5173")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/dashboard")
    public DashboardSummaryDTO dashboard() {
        return reportService.getDashboardSummary();
    }

    @GetMapping("/total-income")
    public Map<String, BigDecimal> totalIncome() {
        return Map.of("totalIncome", reportService.getTotalIncome());
    }

    @GetMapping("/total-expenses")
    public Map<String, BigDecimal> totalExpenses() {
        return Map.of("totalExpenses", reportService.getTotalExpenses());
    }

    @GetMapping("/category-spending")
    public List<CategorySpendingDTO> categorySpending() {
        return reportService.getCategorySpending();
    }

    @GetMapping("/above-average-categories")
    public List<CategorySpendingDTO> aboveAverage() {
        return reportService.getCategoriesAboveAverage();
    }

    @GetMapping("/monthly-summary")
    public List<MonthlySummaryDTO> monthlySummary() {
        return reportService.getMonthlySummary();
    }

    @GetMapping("/recent-transactions")
    public List<TransactionDTO> recent(@RequestParam(defaultValue = "5") int limit) {
        return reportService.getRecentTransactions(limit);
    }
}
