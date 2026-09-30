import { formatINR } from "../services/format";

export default function CategorySpending({ items }) {
  if (items.length === 0) return <p className="muted">No expenses yet.</p>;
  const max = Math.max(...items.map((i) => Number(i.amount)));
  return (
    <div className="list">
      {items.map((i) => (
        <div key={i.categoryName} className="bar-row">
          <div className="bar-top"><span>{i.categoryName}</span><strong>{formatINR(i.amount)}</strong></div>
          <div className="bar"><div className="fill" style={{ width: `${(Number(i.amount) / max) * 100}%` }} /></div>
        </div>
      ))}
    </div>
  );
}
