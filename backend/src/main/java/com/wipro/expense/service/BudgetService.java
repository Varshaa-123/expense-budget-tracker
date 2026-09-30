package com.wipro.expense.service;

import com.wipro.expense.dto.BudgetDTO;
import com.wipro.expense.entity.Budget;
import com.wipro.expense.entity.Category;
import com.wipro.expense.entity.CategoryType;
import com.wipro.expense.entity.User;
import com.wipro.expense.exception.ResourceNotFoundException;
import com.wipro.expense.repository.BudgetRepository;
import com.wipro.expense.repository.CategoryRepository;
import com.wipro.expense.repository.TransactionRepository;
import com.wipro.expense.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public BudgetService(BudgetRepository budgetRepository, UserRepository userRepository,
                         CategoryRepository categoryRepository, TransactionRepository transactionRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    public BudgetDTO createBudget(BudgetDTO dto) {
        if (budgetRepository.existsByUserUserIdAndCategoryCategoryIdAndMonthAndYear(
                dto.userId(), dto.categoryId(), dto.month(), dto.year())) {
            throw new IllegalArgumentException("A budget for this category and month already exists");
        }
        Budget budget = new Budget();
        copyFields(budget, dto);
        return toDTO(budgetRepository.save(budget));
    }

    /** Uses the JOIN + subquery report so each budget comes with its "spent" value. */
    public List<BudgetDTO> getBudgets() {
        return budgetRepository.findAllWithSpent().stream().map(row -> new BudgetDTO(
                ((Number) row[0]).longValue(),
                ((Number) row[1]).longValue(),
                ((Number) row[2]).longValue(),
                (String) row[3],
                new BigDecimal(row[4].toString()),
                ((Number) row[5]).intValue(),
                ((Number) row[6]).intValue(),
                new BigDecimal(row[7].toString()))).toList();
    }

    public BudgetDTO getBudgetById(Long id) {
        return toDTO(findBudget(id));
    }

    public BudgetDTO updateBudget(Long id, BudgetDTO dto) {
        Budget budget = findBudget(id);
        copyFields(budget, dto);
        return toDTO(budgetRepository.save(budget));
    }

    public void deleteBudget(Long id) {
        budgetRepository.delete(findBudget(id));
    }

    // ---------- helper methods ----------
    private Budget findBudget(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id " + id));
    }

    private void copyFields(Budget budget, BudgetDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + dto.userId()));
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + dto.categoryId()));
        if (category.getCategoryType() != CategoryType.EXPENSE) {
            throw new IllegalArgumentException("Budgets can only be created for EXPENSE categories");
        }
        budget.setUser(user);
        budget.setCategory(category);
        budget.setAmount(dto.amount());
        budget.setMonth(dto.month());
        budget.setYear(dto.year());
    }

    private BudgetDTO toDTO(Budget b) {
        BigDecimal spent = transactionRepository.sumExpenseForBudget(
                b.getUser().getUserId(), b.getCategory().getCategoryId(), b.getMonth(), b.getYear());
        return new BudgetDTO(b.getBudgetId(), b.getUser().getUserId(), b.getCategory().getCategoryId(),
                b.getCategory().getCategoryName(), b.getAmount(), b.getMonth(), b.getYear(), spent);
    }
}
