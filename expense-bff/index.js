import express, { json } from "express";
import cors from "cors";
import { config } from "dotenv";

import routes from "./routes/index.js";

config();

const app = express();

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
