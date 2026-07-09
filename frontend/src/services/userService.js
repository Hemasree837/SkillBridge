import api from './api';

export const getUserById = async (id) => {
  const response = await api.get(`/users/${id}`);
  return response.data;
};

export const getAllUsers = async () => {
  const response = await api.get('/users');
  return response.data;
};

export const updateUser = async (id, data) => {
  const response = await api.put(`/users/${id}`, data);
  return response.data;
};

// filters is an object like { skillName, college, department }
export const searchUsers = async (filters) => {
  const response = await api.get('/users/search', { params: filters });
  return response.data;
};
