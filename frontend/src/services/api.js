import axios from "axios";

// ONE place for all backend calls. Change the URL here if the backend port changes.
const api = axios.create({
  baseURL: "https://expense-budget-tracker-production.up.railway.app/api",
});

const data = (promise) => promise.then((res) => res.data);

// Users
export const getUsers = () => data(api.get("/users"));

// Categories
export const getCategories = () => data(api.get("/categories"));
export const createCategory = (c) => data(api.post("/categories", c));
export const updateCategory = (id, c) => data(api.put(`/categories/${id}`, c));
export const deleteCategory = (id) => api.delete(`/categories/${id}`);

// Transactions (type = "INCOME" | "EXPENSE" | undefined)
export const getTransactions = (type) =>
  data(api.get("/transactions", { params: type ? { type } : {} }));

export const createTransaction = (t) =>
  data(api.post("/transactions", t));

export const updateTransaction = (id, t) =>
  data(api.put(`/transactions/${id}`, t));

export const deleteTransaction = (id) =>
  api.delete(`/transactions/${id}`);

// Budgets
export const getBudgets = () => data(api.get("/budgets"));
export const createBudget = (b) => data(api.post("/budgets", b));
export const deleteBudget = (id) => api.delete(`/budgets/${id}`);

// Reports
export const getDashboard = () =>
  data(api.get("/reports/dashboard"));

export const getCategorySpending = () =>
  data(api.get("/reports/category-spending"));

export const getRecentTransactions = (limit = 5) =>
  data(api.get("/reports/recent-transactions", { params: { limit } }));

export const getMonthlySummary = () =>
  data(api.get("/reports/monthly-summary"));

export const getAboveAverageCategories = () =>
  data(api.get("/reports/above-average-categories"));

// Turns an Axios error into a message we can show on screen
export const errorMessage = (error) =>
  error.response?.data?.message ||
  "Cannot reach the backend. Is Spring Boot running on port 8091?";