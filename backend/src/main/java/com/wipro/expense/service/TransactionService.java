package com.wipro.expense.service;

import com.wipro.expense.dto.TransactionDTO;
import com.wipro.expense.entity.Category;
import com.wipro.expense.entity.Transaction;
import com.wipro.expense.entity.TransactionType;
import com.wipro.expense.entity.User;
import com.wipro.expense.exception.ResourceNotFoundException;
import com.wipro.expense.repository.CategoryRepository;
import com.wipro.expense.repository.TransactionRepository;
import com.wipro.expense.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository,
                              CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public TransactionDTO createTransaction(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        copyFields(transaction, dto);
        return toDTO(transactionRepository.save(transaction));
    }

    /** type is optional: null = all transactions, otherwise only INCOME or only EXPENSE. */
    public List<TransactionDTO> getAllTransactions(TransactionType type) {
        List<Transaction> list = (type == null)
                ? transactionRepository.findAllByOrderByTransactionDateDescTransactionIdDesc()
                : transactionRepository.findByTransactionTypeOrderByTransactionDateDescTransactionIdDesc(type);
        return list.stream().map(this::toDTO).toList();
    }

    public TransactionDTO getTransactionById(Long id) {
        return toDTO(findTransaction(id));
    }

    public TransactionDTO updateTransaction(Long id, TransactionDTO dto) {
        Transaction transaction = findTransaction(id);
        copyFields(transaction, dto);
        return toDTO(transactionRepository.save(transaction));
    }

    public void deleteTransaction(Long id) {
        transactionRepository.delete(findTransaction(id));
    }

    // ---------- helper methods ----------
    private Transaction findTransaction(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id " + id));
    }

    private void copyFields(Transaction transaction, TransactionDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + dto.userId()));
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + dto.categoryId()));

        // Business rule: an INCOME transaction needs an INCOME category (and same for EXPENSE)
        if (!category.getCategoryType().name().equals(dto.transactionType().name())) {
            throw new IllegalArgumentException("Category '" + category.getCategoryName() + "' is an "
                    + category.getCategoryType() + " category, but the transaction type is " + dto.transactionType());
        }

        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setAmount(dto.amount());
        transaction.setTransactionType(dto.transactionType());
        transaction.setDescription(dto.description());
        transaction.setTransactionDate(dto.transactionDate());
    }

    private TransactionDTO toDTO(Transaction t) {
        return new TransactionDTO(t.getTransactionId(), t.getUser().getUserId(), t.getUser().getUserName(),
                t.getCategory().getCategoryId(), t.getCategory().getCategoryName(),
                t.getAmount(), t.getTransactionType(), t.getDescription(), t.getTransactionDate());
    }
}
