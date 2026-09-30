import { useEffect, useState } from "react";
import { getBudgets, createBudget, deleteBudget, getUsers, getCategories, errorMessage } from "../services/api";
import { MONTH_NAMES } from "../services/format";
import BudgetProgress from "../components/BudgetProgress";

export default function Budgets() {
  const now = new Date();
  const [budgets, setBudgets] = useState([]);
  const [users, setUsers] = useState([]);
  const [categories, setCategories] = useState([]);
  const [error, setError] = useState("");
  const [form, setForm] = useState({ userId: "", categoryId: "", amount: "", month: now.getMonth() + 1, year: now.getFullYear() });

  const load = () => getBudgets().then(setBudgets).catch((e) => setError(errorMessage(e)));

  useEffect(() => {
    load();
    Promise.all([getUsers(), getCategories()]).then(([u, c]) => {
      setUsers(u);
      setCategories(c.filter((x) => x.categoryType === "EXPENSE"));
      setForm((f) => ({ ...f, userId: u[0]?.userId || "" }));
    }).catch((e) => setError(errorMessage(e)));
  }, []);

  const change = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const submit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      await createBudget({ userId: Number(form.userId), categoryId: Number(form.categoryId),
        amount: Number(form.amount), month: Number(form.month), year: Number(form.year) });
      setForm({ ...form, categoryId: "", amount: "" });
      load();
    } catch (err) { setError(errorMessage(err)); }
  };

  const remove = async (b) => {
    if (!window.confirm(`Delete budget for ${b.categoryName}?`)) return;
    try { await deleteBudget(b.budgetId); load(); } catch (err) { setError(errorMessage(err)); }
  };

  // Group budgets by "Month Year" so they are easier to read
  const groups = {};
  budgets.forEach((b) => {
    const key = `${MONTH_NAMES[b.month - 1]} ${b.year}`;
    (groups[key] = groups[key] || []).push(b);
  });

  return (
    <>
      {error && <div className="alert">{error}</div>}
      <form className="card form" onSubmit={submit}>
        <h3>Create Budget</h3>
        <div className="grid">
          <label>User
            <select name="userId" value={form.userId} onChange={change} required>
              {users.map((u) => <option key={u.userId} value={u.userId}>{u.userName}</option>)}
            </select>
          </label>
          <label>Category
            <select name="categoryId" value={form.categoryId} onChange={change} required>
              <option value="">Select category</option>
              {categories.map((c) => <option key={c.categoryId} value={c.categoryId}>{c.categoryName}</option>)}
            </select>
          </label>
          <label>Amount (₹)
            <input type="number" name="amount" min="1" step="0.01" value={form.amount} onChange={change} required />
          </label>
          <label>Month
            <select name="month" value={form.month} onChange={change}>
              {MONTH_NAMES.map((m, i) => <option key={m} value={i + 1}>{m}</option>)}
            </select>
          </label>
          <label>Year
            <input type="number" name="year" min="2000" value={form.year} onChange={change} required />
          </label>
        </div>
        <button className="btn" type="submit">Create Budget</button>
      </form>

      {Object.entries(groups).map(([label, items]) => (
        <section className="card" key={label}>
          <h3>{label}</h3>
          <div className="list">{items.map((b) => <BudgetProgress key={b.budgetId} budget={b} onDelete={remove} />)}</div>
        </section>
      ))}
    </>
  );
}
