import { useLocation, Link } from "react-router-dom";

const titles = {
  "/": "Dashboard",
  "/transactions": "Transactions",
  "/transactions/new": "Add Transaction",
  "/categories": "Categories",
  "/budgets": "Budgets",
  "/reports": "Reports",
};

export default function Header() {
  const { pathname } = useLocation();
  const today = new Date().toLocaleDateString("en-IN", { weekday: "long", day: "numeric", month: "long", year: "numeric" });
  return (
    <header className="header">
      <div>
        <h1>{titles[pathname] || "Expense Tracker"}</h1>
        <p className="muted">{today}</p>
      </div>
      <Link to="/transactions/new" className="btn">+ Add Transaction</Link>
    </header>
  );
}
