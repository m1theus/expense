import * as React from "react";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { Button } from "@mui/material";

export default function ExpenseList({ rows = [] }) {
  if (!rows) {
    return <></>;
  }
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>Person Name</TableCell>
            <TableCell>Description</TableCell>
            <TableCell>Expense Date</TableCell>
            <TableCell>Expense Value</TableCell>
            <TableCell align="right">Tags</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow
              key={row.id}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell component="th" scope="row">
                {row.personName}
              </TableCell>
              <TableCell>{row.description}</TableCell>
              <TableCell>{row.expenseDate}</TableCell>
              <TableCell>{row.expenseValue}</TableCell>
              <TableCell align="right">{row.tags}</TableCell>
              <TableCell align="right">
                <Button variant="outlined" href={`/expense/${row.id}`}>
                  View Expense
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
