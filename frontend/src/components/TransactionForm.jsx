import { useEffect, useState } from "react";
import { getUsers, getCategories, createTransaction, updateTransaction, errorMessage } from "../services/api";

// Used for both "add" (no initial) and "edit" (initial = existing transaction).
export default function TransactionForm({ initial, onSaved, onCancel }) {
  const [users, setUsers] = useState([]);
  const [categories, setCategories] = useState([]);
  const [error, setError] = useState("");
  const [form, setForm] = useState({
    userId: initial?.userId ?? "",
    transactionType: initial?.transactionType ?? "EXPENSE",
    categoryId: initial?.categoryId ?? "",
    amount: initial?.amount ?? "",
    transactionDate: initial?.transactionDate ?? new Date().toISOString().slice(0, 10),
    description: initial?.description ?? "",
  });

  useEffect(() => {
    Promise.all([getUsers(), getCategories()])
      .then(([u, c]) => {
        setUsers(u);
        setCategories(c);
        setForm((f) => ({ ...f, userId: f.userId || u[0]?.userId || "" }));
      })
      .catch((e) => setError(errorMessage(e)));
  }, []);

  const change = (e) => setForm({ ...form, [e.target.name]: e.target.value });
  // Only show categories that match the selected type
  const options = categories.filter((c) => c.categoryType === form.transactionType);

  const submit = async (e) => {
    e.preventDefault();
    setError("");
    const body = { ...form, userId: Number(form.userId), categoryId: Number(form.categoryId), amount: Number(form.amount) };
    try {
      if (initial) await updateTransaction(initial.transactionId, body);
      else await createTransaction(body);
      onSaved();
    } catch (err) {
      setError(errorMessage(err));
    }
  };

  return (
    <form className="card form" onSubmit={submit}>
      <h3>{initial ? "Edit Transaction" : "New Transaction"}</h3>
      {error && <div className="alert">{error}</div>}
      <div className="grid">
        <label>User
          <select name="userId" value={form.userId} onChange={change} required>
            {users.map((u) => <option key={u.userId} value={u.userId}>{u.userName}</option>)}
          </select>
        </label>
        <label>Type
          <select name="transactionType" value={form.transactionType} onChange={(e) => setForm({ ...form, transactionType: e.target.value, categoryId: "" })}>
            <option value="EXPENSE">Expense</option>
            <option value="INCOME">Income</option>
          </select>
        </label>
        <label>Category
          <select name="categoryId" value={form.categoryId} onChange={change} required>
            <option value="">Select category</option>
            {options.map((c) => <option key={c.categoryId} value={c.categoryId}>{c.categoryName}</option>)}
          </select>
        </label>
        <label>Amount (₹)
          <input type="number" name="amount" min="0.01" step="0.01" value={form.amount} onChange={change} required />
        </label>
        <label>Date
          <input type="date" name="transactionDate" value={form.transactionDate} onChange={change} required />
        </label>
        <label>Description
          <input name="description" value={form.description} onChange={change} maxLength="255" />
        </label>
      </div>
      <div className="row">
        <button className="btn" type="submit">{initial ? "Update" : "Save"}</button>
        {onCancel && <button className="btn secondary" type="button" onClick={onCancel}>Cancel</button>}
      </div>
    </form>
  );
}
