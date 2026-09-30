import { formatINR } from "../services/format";

// onEdit and onDelete are optional: the dashboard shows the table without action buttons.
export default function TransactionTable({ transactions, onEdit, onDelete }) {
  if (transactions.length === 0) return <p className="muted">No transactions found.</p>;
  return (
    <div className="table-wrap">
      <table>
        <thead>
          <tr>
            <th>Date</th><th>Description</th><th>Category</th><th>Type</th><th className="right">Amount</th>
            {onEdit && <th></th>}
          </tr>
        </thead>
        <tbody>
          {transactions.map((t) => (
            <tr key={t.transactionId}>
              <td>{t.transactionDate}</td>
              <td>{t.description}</td>
              <td>{t.categoryName}</td>
              <td><span className={`badge ${t.transactionType === "INCOME" ? "income" : "expense"}`}>{t.transactionType}</span></td>
              <td className={`right amount ${t.transactionType === "INCOME" ? "pos" : "neg"}`}>
                {t.transactionType === "INCOME" ? "+" : "-"}{formatINR(t.amount)}
              </td>
              {onEdit && (
                <td className="right actions">
                  <button className="link" onClick={() => onEdit(t)}>Edit</button>
                  <button className="link danger" onClick={() => onDelete(t)}>Delete</button>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
