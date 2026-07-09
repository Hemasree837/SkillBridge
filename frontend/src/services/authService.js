import api from './api';

// Registers a new user
export const registerUser = async (data) => {
  const response = await api.post('/auth/register', data);
  return response.data;
};

// Logs in a user and saves them to localStorage so the app remembers who is logged in
export const loginUser = async (data) => {
  const response = await api.post('/auth/login', data);
  localStorage.setItem('skillbridge_user', JSON.stringify(response.data));
  return response.data;
};

// Removes the logged-in user from localStorage
export const logoutUser = () => {
  localStorage.removeItem('skillbridge_user');
};

// Returns the currently logged-in user, or null if nobody is logged in
export const getCurrentUser = () => {
  const stored = localStorage.getItem('skillbridge_user');
  return stored ? JSON.parse(stored) : null;
};
