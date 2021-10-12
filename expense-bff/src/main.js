import express, { json } from "express";
import cors from "cors";
import { config } from "dotenv";
import { Router } from "express";
import {
  createExpense,
  findExpenseById,
  findAllExpense,
} from "../controllers/index.js";

config();

const app = express();
const routes = Router();

routes.get("/", (_req, response) => response.json({ version: "1.0.0" }));
routes.post("/expense", createExpense);
routes.get("/expense", findAllExpense);
routes.get("/expense/:id", findExpenseById);

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

app.listen(process.env.PORT || 3000, () => console.log("app started!"));
