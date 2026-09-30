package com.wipro.expense.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** Maps to the "categories" table. */
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank(message = "Category name is required")
    private String categoryName;

    @NotNull(message = "Category type is required (INCOME or EXPENSE)")
    @Enumerated(EnumType.STRING)      // store the text "INCOME", not the number 0
    private CategoryType categoryType;

    public Category() { }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public CategoryType getCategoryType() { return categoryType; }
    public void setCategoryType(CategoryType categoryType) { this.categoryType = categoryType; }
}
