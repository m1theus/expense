import * as React from "react";
import { Button } from "@mui/material";

import api from "../Api";
import ExpenseList from "./ExpenseList";

export default function Home() {
  const [expenses, setExpenses] = React.useState([]);

  React.useEffect(() => {
    async function fetchExpenses() {
      const { data } = await api.get("/expense");
      setExpenses(data);
    }

    fetchExpenses();
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <Button variant="outlined" href="/expense">
          New Expense
        </Button>
      </header>
      <section>
        <ExpenseList rows={expenses} />
      </section>
    </div>
  );
}
