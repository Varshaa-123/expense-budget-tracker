package com.wipro.expense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Entry point. @SpringBootApplication scans this package and all sub-packages. */
@SpringBootApplication
public class ExpenseBudgetTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExpenseBudgetTrackerApplication.class, args);
    }
}
