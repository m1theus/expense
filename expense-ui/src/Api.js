import axios from "axios";

const api = axios.create({
  baseURL: process.env.REACT_APP_BACKEND_API ?? "http://localhost:1337",
  timeout: 5000,
});

export default api;
