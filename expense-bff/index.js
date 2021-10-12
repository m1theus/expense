const express = require("express");
const { json, Router } = require("express");
const cors = require("cors");
const { config } = require("dotenv");
const fetch = (...args) =>
  import("node-fetch").then(({ default: fetch }) => fetch(...args));

config();

const app = express();
const routes = Router();
const headers = {
  accept: "application/json",
  "Content-Type": "application/json",
};

const BACKEND_URL =
  process.env.BACKEND_URL || "http://localhost:8080/api/expense";

routes.get("/", (_req, response) => response.json({ version: "1.0.0" }));

routes.post("/expense", async (request, _response) => {
  console.log(request.body);
  if (!request.body) {
    _response.status(400);
  }

  const { personName, description, expenseDate, expenseValue, tags } =
    request.body;

  const req = {
    person_name: personName,
    expense_description: description,
    expense_date: expenseDate,
    expense_value: formatNumber(expenseValue),
    expense_tags: tags,
  };

  try {
    const response = await fetch(BACKEND_URL, {
      method: "post",
      body: JSON.stringify(req),
      headers,
    });

    const data = await response.json();
    _response.status(201).json(convertExpense(data));
  } catch (error) {
    console.log(error);
    _response.status(500);
  }
});

routes.get("/expense", async (_request, _response) => {
  try {
    const response = await fetch(BACKEND_URL, {
      method: "get",
      headers,
    });
    const body = await response.json();
    const data = body.map(convertExpense);
    _response.status(200).json(data);
  } catch (error) {
    console.log(error);
    _response.status(500);
  }
});

routes.get("/expense/:id", async (request, _response) => {
  const { id } = request.params;

  if (!id) {
    _response.status(400).json({
      error: "missing id",
    });
  }

  try {
    const response = await fetch(`${BACKEND_URL}/${id}`, {
      method: "get",
      headers,
    });
    const data = await response.json();
    _response.status(200).json(convertExpense(data));
  } catch (error) {
    console.log(error);
    _response.status(500);
  }
});

app.use(json());

app.use(
  cors({
    methods: ["GET", "POST", "OPTIONS"],
    allowedHeaders: ["Content-Type", "Access-Control-Allow-Origin", "Accept"],
    origin: "*",
    credentials: true,
    optionsSuccessStatus: 200,
  })
);

app.use(routes);

const convertExpense = (expense = {}) => ({
  id: expense.id,
  personName: expense.person_name,
  description: expense.expense_description,
  expenseDate: formatDate(expense.expense_date),
  expenseValue: formatToBRL(expense.expense_value),
  tags: formatTags(expense.expense_tags),
  createdAt: formatDate(expense.created_at),
});

const formatToBRL = (currency = "") => `R$ ${currency}`.replace(/[.]/, ",");

const formatTags = (tags = []) => tags.join(", ").toUpperCase();

const formatDate = (date = "") => {
  const newDate = new Date(date);
  const day = newDate.getDate();
  const month = newDate.getMonth() + 1;
  const year = newDate.getFullYear();

  return `${addZero(day)}/${addZero(month)}/${addZero(year)}`;
};

const addZero = (num) => (num <= 9 ? `0${num}` : num);

const formatNumber = (num = "") => Number(num.replace(/[,]/, "."));

app.listen(process.env.PORT || 3000, () => console.log("app started!"));
