import { useNavigate } from "react-router-dom";
import TransactionForm from "../components/TransactionForm";

export default function AddTransaction() {
  const navigate = useNavigate();
  return <TransactionForm onSaved={() => navigate("/transactions")} onCancel={() => navigate("/transactions")} />;
}
