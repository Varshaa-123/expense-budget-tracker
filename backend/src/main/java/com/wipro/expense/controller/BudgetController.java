package com.wipro.expense.controller;

import com.wipro.expense.dto.BudgetDTO;
import com.wipro.expense.service.BudgetService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "http://localhost:5173")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetDTO create(@Valid @RequestBody BudgetDTO dto) {
        return budgetService.createBudget(dto);
    }

    @GetMapping
    public List<BudgetDTO> getAll() {
        return budgetService.getBudgets();
    }

    @GetMapping("/{id}")
    public BudgetDTO getById(@PathVariable Long id) {
        return budgetService.getBudgetById(id);
    }

    @PutMapping("/{id}")
    public BudgetDTO update(@PathVariable Long id, @Valid @RequestBody BudgetDTO dto) {
        return budgetService.updateBudget(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        budgetService.deleteBudget(id);
    }
}
