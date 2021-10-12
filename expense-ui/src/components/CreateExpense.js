import * as React from "react";
import Container from "@mui/material/Container";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import { useHistory } from "react-router-dom";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import {
  Button,
  Fab,
  Grid,
  InputAdornment,
  List,
  ListItem,
  ListItemText,
  TextField,
} from "@mui/material";
import { Box } from "@mui/system";
import AddIcon from "@mui/icons-material/Add";
import AdapterDateFns from "@mui/lab/AdapterDateFns";
import LocalizationProvider from "@mui/lab/LocalizationProvider";
import DatePicker from "@mui/lab/DatePicker";

import api from "../Api";

const theme = createTheme({
  components: {
    MuiAppBar: {
      defaultProps: {
        enableColorOnDark: true,
      },
    },
  },
});

export default function CreateExpense() {
  const history = useHistory();
  const [values, setValues] = React.useState({
    personName: "",
    description: "",
    expenseDate: "",
    expenseValue: "",
    tagInput: "",
    tags: [],
  });

  async function handleSubmit(event) {
    event.preventDefault();

    try {
      const response = await api.post("/expense", {
        ...values,
      });
      if (response.status === 201) {
        history.push("/");
      }
    } catch (error) {
      console.log(error);
    }
  }

  function handleTagSubmit(event) {
    event.preventDefault();
    values.tags.push(values.tagInput);
    setValues({ ...values, tagInput: "" });
  }

  const handleChange = (prop) => (event) => {
    setValues({ ...values, [prop]: event?.target?.value });
  };

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
          <Typography
            mb={5}
            component="h1"
            variant="h4"
            align="center"
          ></Typography>
          <React.Fragment>
            <Box
              component="form"
              validate
              onSubmit={handleSubmit}
              sx={{ mt: 1 }}
            >
              <Grid container spacing={3}>
                <Grid item xs={12} sm={6}>
                  <TextField
                    required
                    id="personName"
                    name="personName"
                    label="Seu Nome"
                    fullWidth
                    autoComplete="given-name"
                    variant="outlined"
                    value={values.personName}
                    onChange={handleChange("personName")}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    required
                    id="expenseValue"
                    name="expenseValue"
                    label="Valor"
                    fullWidth
                    variant="outlined"
                    value={values.expenseValue}
                    onChange={handleChange("expenseValue")}
                    InputProps={{
                      inputMode: "numeric",
                      pattern: ".*",
                      startAdornment: (
                        <InputAdornment position="start">R$</InputAdornment>
                      ),
                    }}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <DatePicker
                      label="Expense Date"
                      value={values.expenseDate}
                      onChange={(newValue) => {
                        setValues({ ...values, expenseDate: newValue });
                      }}
                      renderInput={(params) => <TextField {...params} />}
                    />
                  </LocalizationProvider>
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    id="outlined-multiline-static"
                    label="Description"
                    multiline
                    rows={4}
                    variant="outlined"
                    style={{ width: 500 }}
                    value={values.description}
                    onChange={handleChange("description")}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    id="tags"
                    name="tags"
                    label="Tag"
                    fullWidth
                    autoComplete="given-name"
                    variant="outlined"
                    value={values.tagInput}
                    onChange={handleChange("tagInput")}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <Box align="center">
                    <Fab
                      color="primary"
                      aria-label="add"
                      onClick={handleTagSubmit}
                    >
                      <AddIcon />
                    </Fab>
                  </Box>
                </Grid>
                <Grid item xs={12} sm={6}>
                  <List disablePadding>
                    {values?.tags?.map((tag, index) => (
                      <ListItem key={`${tag}${index}`} sx={{ py: 1, px: 0 }}>
                        <ListItemText primary={"Tag"} />
                        <Typography variant="body2">{tag}</Typography>
                      </ListItem>
                    ))}
                  </List>
                </Grid>

                <Grid item xs={12}>
                  <Box textAlign="center">
                    <Button type="submit" variant="contained">
                      Submit
                    </Button>
                  </Box>
                </Grid>
              </Grid>
            </Box>
          </React.Fragment>
        </Paper>
      </Container>
    </ThemeProvider>
  );
}
