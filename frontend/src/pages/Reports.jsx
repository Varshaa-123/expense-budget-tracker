import { useEffect, useState } from "react";
import { getMonthlySummary, getCategorySpending, getAboveAverageCategories, getDashboard, errorMessage } from "../services/api";
import { formatINR, MONTH_NAMES } from "../services/format";
import CategorySpending from "../components/CategorySpending";

export default function Reports() {
  const [monthly, setMonthly] = useState([]);
  const [spending, setSpending] = useState([]);
  const [above, setAbove] = useState([]);
  const [summary, setSummary] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    Promise.all([getMonthlySummary(), getCategorySpending(), getAboveAverageCategories(), getDashboard()])
      .then(([m, s, a, d]) => { setMonthly(m); setSpending(s); setAbove(a); setSummary(d); })
      .catch((e) => setError(errorMessage(e)));
  }, []);

  if (error) return <div className="alert">{error}</div>;
  if (!summary) return <p className="muted">Loading...</p>;

  const max = Math.max(1, ...monthly.flatMap((m) => [Number(m.income), Number(m.expenses)]));

  return (
    <>
      <section className="card">
        <h3>Income vs Expense (all time)</h3>
        <div className="compare">
          <div><span className="muted">Income</span><strong className="pos">{formatINR(summary.totalIncome)}</strong></div>
          <div><span className="muted">Expenses</span><strong className="neg">{formatINR(summary.totalExpenses)}</strong></div>
          <div><span className="muted">Balance</span><strong>{formatINR(summary.remainingBalance)}</strong></div>
        </div>
      </section>

      <section className="card">
        <h3>Monthly Spending</h3>
        <div className="legend"><span className="dot green" /> Income <span className="dot red" /> Expenses</div>
        <div className="month-chart">
          {monthly.map((m) => (
            <div className="month" key={`${m.year}-${m.month}`}>
              <div className="month-bars">
                <div className="mbar green" style={{ height: `${(Number(m.income) / max) * 100}%` }} title={formatINR(m.income)} />
                <div className="mbar red" style={{ height: `${(Number(m.expenses) / max) * 100}%` }} title={formatINR(m.expenses)} />
              </div>
              <small>{MONTH_NAMES[m.month - 1]} {String(m.year).slice(2)}</small>
              <small className="neg">{formatINR(m.expenses)}</small>
            </div>
          ))}
        </div>
      </section>

      <div className="two-col">
        <section className="card"><h3>Spending by Category</h3><CategorySpending items={spending} /></section>
        <section className="card">
          <h3>Above-Average Categories</h3>
          <p className="muted">Categories where total spending is higher than the average category.</p>
          <CategorySpending items={above} />
        </section>
      </div>
    </>
  );
}
