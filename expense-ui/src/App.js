import * as React from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Link from "@mui/material/Link";
import Typography from "@mui/material/Typography";

import Home from "./components/Home";
import ViewExpense from "./components/ViewExpense";
import CreateExpense from "./components/CreateExpense";
import NotFound from "./components/NotFound";

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/">
          <Home />
        </Route>
        <Route exact path="/expense">
          <CreateExpense />
        </Route>
        <Route path="/expense/:id">
          <ViewExpense />
        </Route>
        <Route path="*">
          <NotFound />
        </Route>
      </Switch>
      <footer>
        <Copyright />
      </footer>
    </Router>
  );
}

function Copyright() {
  return (
    <Typography variant="body2" color="text.secondary" align="center">
      {"Copyright © "}
      <Link target="_blank" color="inherit" href="https://github.com/m1theus">
        Matheus Martins
      </Link>{" "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

export default App;
