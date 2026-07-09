import api from './api';

export const sendSwapRequest = async (data) => {
  const response = await api.post('/requests', data);
  return response.data;
};

export const getSentRequests = async (userId) => {
  const response = await api.get('/requests', { params: { sentBy: userId } });
  return response.data;
};

export const getReceivedRequests = async (userId) => {
  const response = await api.get('/requests', { params: { receivedBy: userId } });
  return response.data;
};

export const acceptRequest = async (id) => {
  const response = await api.put(`/requests/${id}/accept`);
  return response.data;
};

export const rejectRequest = async (id) => {
  const response = await api.put(`/requests/${id}/reject`);
  return response.data;
};
