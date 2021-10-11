import { Router } from "express";
import {
  createExpense,
  findExpenseById,
  findAllExpense,
} from "../controllers/index.js";

const routes = Router();

routes.post("/expense", createExpense);
routes.get("/expense", findAllExpense);
routes.get("/expense/:id", findExpenseById);

export default routes;
