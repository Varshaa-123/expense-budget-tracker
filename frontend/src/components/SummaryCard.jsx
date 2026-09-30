import { formatINR } from "../services/format";

export default function SummaryCard({ title, amount, tone }) {
  return (
    <div className={`card summary ${tone}`}>
      <p className="muted">{title}</p>
      <h2>{formatINR(amount)}</h2>
    </div>
  );
}
