import fetch from "node-fetch";

const headers = {
  accept: "application/json",
  "Content-Type": "application/json",
};

const BACKEND_URL =
  process.env.BACKEND_URL || "http://localhost:8080/api/expense";

export const createExpense = async (request, _response) => {
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
};

export const findAllExpense = async (_request, _response) => {
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
};

export const findExpenseById = async (request, _response) => {
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
};

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
