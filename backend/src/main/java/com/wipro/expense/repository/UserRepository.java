package com.wipro.expense.repository;

import com.wipro.expense.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);   // derived query: SELECT ... WHERE email = ?
}
