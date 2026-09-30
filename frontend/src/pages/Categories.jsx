import { useEffect, useState } from "react";
import { getCategories, createCategory, updateCategory, deleteCategory, errorMessage } from "../services/api";

const emptyForm = { categoryName: "", categoryType: "EXPENSE" };

export default function Categories() {
  const [categories, setCategories] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [editingId, setEditingId] = useState(null);
  const [error, setError] = useState("");

  const load = () => getCategories().then(setCategories).catch((e) => setError(errorMessage(e)));
  useEffect(() => { load(); }, []);

  const submit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      if (editingId) await updateCategory(editingId, form);
      else await createCategory(form);
      setForm(emptyForm);
      setEditingId(null);
      load();
    } catch (err) { setError(errorMessage(err)); }
  };

  const remove = async (c) => {
    if (!window.confirm(`Delete category "${c.categoryName}"?`)) return;
    setError("");
    try { await deleteCategory(c.categoryId); load(); } catch (err) { setError(errorMessage(err)); }
  };

  return (
    <>
      {error && <div className="alert">{error}</div>}
      <form className="card form" onSubmit={submit}>
        <h3>{editingId ? "Edit Category" : "Add Category"}</h3>
        <div className="grid">
          <label>Name
            <input value={form.categoryName} onChange={(e) => setForm({ ...form, categoryName: e.target.value })} required />
          </label>
          <label>Type
            <select value={form.categoryType} onChange={(e) => setForm({ ...form, categoryType: e.target.value })}>
              <option value="EXPENSE">Expense</option>
              <option value="INCOME">Income</option>
            </select>
          </label>
        </div>
        <div className="row">
          <button className="btn" type="submit">{editingId ? "Update" : "Add"}</button>
          {editingId && <button className="btn secondary" type="button" onClick={() => { setEditingId(null); setForm(emptyForm); }}>Cancel</button>}
        </div>
      </form>
      <section className="card">
        <div className="table-wrap">
          <table>
            <thead><tr><th>Name</th><th>Type</th><th></th></tr></thead>
            <tbody>
              {categories.map((c) => (
                <tr key={c.categoryId}>
                  <td>{c.categoryName}</td>
                  <td><span className={`badge ${c.categoryType === "INCOME" ? "income" : "expense"}`}>{c.categoryType}</span></td>
                  <td className="right actions">
                    <button className="link" onClick={() => { setEditingId(c.categoryId); setForm({ categoryName: c.categoryName, categoryType: c.categoryType }); }}>Edit</button>
                    <button className="link danger" onClick={() => remove(c)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </section>
    </>
  );
}
