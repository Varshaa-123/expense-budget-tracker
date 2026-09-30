import { formatINR } from "../services/format";

// Green bar = within budget, amber = over 80%, red = over budget.
export default function BudgetProgress({ budget, onDelete }) {
  const spent = Number(budget.spent);
  const total = Number(budget.amount);
  const percent = Math.min(100, (spent / total) * 100);
  const tone = spent > total ? "over" : percent >= 80 ? "warn" : "ok";
  return (
    <div className="bar-row">
      <div className="bar-top">
        <span>{budget.categoryName}</span>
        <span>
          <strong>{formatINR(spent)}</strong> / {formatINR(total)}
          {onDelete && <button className="link danger" onClick={() => onDelete(budget)}>Delete</button>}
        </span>
      </div>
      <div className="bar"><div className={`fill ${tone}`} style={{ width: `${percent}%` }} /></div>
      {spent > total && <small className="neg">Over budget by {formatINR(spent - total)}</small>}
    </div>
  );
}
