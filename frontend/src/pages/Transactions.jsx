import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getTransactions, deleteTransaction, errorMessage } from "../services/api";
import TransactionTable from "../components/TransactionTable";
import TransactionForm from "../components/TransactionForm";

export default function Transactions() {
  const [transactions, setTransactions] = useState([]);
  const [filter, setFilter] = useState("");          // "" = all, "INCOME", "EXPENSE"
  const [editing, setEditing] = useState(null);
  const [error, setError] = useState("");

  const load = () =>
    getTransactions(filter).then(setTransactions).catch((e) => setError(errorMessage(e)));

  useEffect(() => { load(); }, [filter]);

  const remove = async (t) => {
    if (!window.confirm(`Delete "${t.description}"?`)) return;
    try { await deleteTransaction(t.transactionId); load(); } catch (e) { setError(errorMessage(e)); }
  };

  return (
    <>
      {error && <div className="alert">{error}</div>}
      {editing && (
        <TransactionForm initial={editing} onCancel={() => setEditing(null)}
          onSaved={() => { setEditing(null); load(); }} />
      )}
      <section className="card">
        <div className="row between">
          <div className="tabs">
            {[["", "All"], ["INCOME", "Income"], ["EXPENSE", "Expense"]].map(([value, label]) => (
              <button key={label} className={filter === value ? "tab active" : "tab"} onClick={() => setFilter(value)}>{label}</button>
            ))}
          </div>
          <Link to="/transactions/new" className="btn">+ Add</Link>
        </div>
        <TransactionTable transactions={transactions} onEdit={setEditing} onDelete={remove} />
      </section>
    </>
  );
}
