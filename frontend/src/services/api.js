import axios from 'axios';

// Base URL of the deployed Spring Boot backend API
const API_BASE_URL = import.meta?.env?.VITE_API_BASE_URL || 'https://skillbridge-nt9b.onrender.com/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export default api;
