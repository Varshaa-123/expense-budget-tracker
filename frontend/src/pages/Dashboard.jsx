import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getDashboard, getCategorySpending, getRecentTransactions, getBudgets, errorMessage } from "../services/api";
import SummaryCard from "../components/SummaryCard";
import CategorySpending from "../components/CategorySpending";
import BudgetProgress from "../components/BudgetProgress";
import TransactionTable from "../components/TransactionTable";

export default function Dashboard() {
  const [summary, setSummary] = useState(null);
  const [spending, setSpending] = useState([]);
  const [recent, setRecent] = useState([]);
  const [budgets, setBudgets] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    Promise.all([getDashboard(), getCategorySpending(), getRecentTransactions(5), getBudgets()])
      .then(([d, s, r, b]) => {
        const now = new Date();
        setSummary(d);
        setSpending(s);
        setRecent(r);
        setBudgets(b.filter((x) => x.month === now.getMonth() + 1 && x.year === now.getFullYear()));
      })
      .catch((e) => setError(errorMessage(e)));
  }, []);

  if (error) return <div className="alert">{error}</div>;
  if (!summary) return <p className="muted">Loading...</p>;

  return (
    <>
      <div className="cards">
        <SummaryCard title="Total Income" amount={summary.totalIncome} tone="green" />
        <SummaryCard title="Total Expenses" amount={summary.totalExpenses} tone="red" />
        <SummaryCard title="Remaining Balance" amount={summary.remainingBalance} tone="blue" />
        <SummaryCard title="This Month's Expenses" amount={summary.monthlyExpenses} tone="amber" />
      </div>
      <div className="two-col">
        <section className="card"><h3>Category Spending</h3><CategorySpending items={spending} /></section>
        <section className="card">
          <h3>Budget Progress (this month)</h3>
          {budgets.length === 0 ? <p className="muted">No budgets for this month.</p> :
            <div className="list">{budgets.map((b) => <BudgetProgress key={b.budgetId} budget={b} />)}</div>}
        </section>
      </div>
      <section className="card">
        <div className="row between"><h3>Recent Transactions</h3><Link to="/transactions">View all</Link></div>
        <TransactionTable transactions={recent} />
      </section>
    </>
  );
}
