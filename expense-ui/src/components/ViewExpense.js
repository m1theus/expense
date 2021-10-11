import * as React from "react";
import Typography from "@mui/material/Typography";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import Grid from "@mui/material/Grid";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { useHistory, useParams } from "react-router-dom";
import Container from "@mui/material/Container";
import Paper from "@mui/material/Paper";
import api from "../Api";
import { Button } from "@mui/material";

const theme = createTheme({
  components: {
    MuiAppBar: {
      defaultProps: {
        enableColorOnDark: true,
      },
    },
  },
});

export default function ViewExpense() {
  const history = useHistory();
  const { id } = useParams();
  const [expense, setExpense] = React.useState({});

  if (!id) {
    history.push("/");
  }

  React.useEffect(() => {
    async function fetchExpense() {
      const { data } = await api.get(`/expense/${id}`);
      if (data) {
        setExpense(data);
      }
    }

    fetchExpense();
  }, [id]);
  return (
    <ThemeProvider theme={theme}>
      <Button variant="outlined" href="/" color="warning">
        Back
      </Button>
      <Container component="main" maxWidth="sm" sx={{ mb: 4 }}>
        <Paper
          variant="outlined"
          sx={{ my: { xs: 3, md: 6 }, p: { xs: 2, md: 3 } }}
        >
          <React.Fragment>
            <Typography variant="h4" gutterBottom>
              {expense?.personName}
            </Typography>
            <Typography variant="h6" gutterBottom>
              {expense?.description}
            </Typography>
            <List disablePadding>
              {
                <ListItem key={expense?.name} sx={{ py: 1, px: 0 }}>
                  <ListItemText primary={"Tags"} />
                  <Typography variant="body2">{expense?.tags}</Typography>
                </ListItem>
              }

              <ListItem sx={{ py: 1, px: 0 }}>
                <ListItemText primary="Total" />
                <Typography variant="subtitle1" sx={{ fontWeight: 700 }}>
                  {expense?.expenseValue}
                </Typography>
              </ListItem>
            </List>
            <Grid container spacing={2}>
              <Grid item xs={12} sm={6}>
                <Typography variant="h6" gutterBottom sx={{ mt: 2 }}>
                  Date
                </Typography>
                <Typography gutterBottom>{expense?.expenseDate}</Typography>
              </Grid>
            </Grid>
          </React.Fragment>
        </Paper>
      </Container>
    </ThemeProvider>
  );
}
