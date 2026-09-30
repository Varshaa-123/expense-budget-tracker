import { NavLink } from "react-router-dom";

const links = [
  { to: "/", label: "Dashboard" },
  { to: "/transactions", label: "Transactions" },
  { to: "/categories", label: "Categories" },
  { to: "/budgets", label: "Budgets" },
  { to: "/reports", label: "Reports" },
];

export default function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="brand">Expense<span>Tracker</span></div>
      <nav>
        {links.map((l) => (
          <NavLink key={l.to} to={l.to} end={l.to === "/"} className={({ isActive }) => (isActive ? "active" : "")}>
            {l.label}
          </NavLink>
        ))}
      </nav>
    </aside>
  );
}
