package com.wipro.expense.controller;

import com.wipro.expense.dto.TransactionDTO;
import com.wipro.expense.entity.TransactionType;
import com.wipro.expense.service.TransactionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDTO create(@Valid @RequestBody TransactionDTO dto) {
        return transactionService.createTransaction(dto);
    }

    /** GET /api/transactions  or  GET /api/transactions?type=INCOME  or  ?type=EXPENSE */
    @GetMapping
    public List<TransactionDTO> getAll(@RequestParam(required = false) TransactionType type) {
        return transactionService.getAllTransactions(type);
    }

    @GetMapping("/{id}")
    public TransactionDTO getById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @PutMapping("/{id}")
    public TransactionDTO update(@PathVariable Long id, @Valid @RequestBody TransactionDTO dto) {
        return transactionService.updateTransaction(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
