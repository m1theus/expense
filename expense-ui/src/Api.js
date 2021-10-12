import axios from "axios";
import https from "https";

const api = axios.create({
  baseURL: process.env.REACT_APP_BACKEND_API ?? "http://localhost:1337",
  timeout: 5000,
  headers: {
    "Content-Type": "application/json",
    accept: "application/json",
  },
  httpsAgent: new https.Agent({
    rejectUnauthorized: false,
  }),
});

export default api;
