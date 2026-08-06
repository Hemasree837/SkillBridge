import axios from 'axios';

// Use Vite environment variable when available, otherwise default to localhost (useful for local dev)
// For Vite set: VITE_API_BASE_URL=https://example.com/api
const API_BASE_URL = import.meta?.env?.VITE_API_BASE_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export default api;
