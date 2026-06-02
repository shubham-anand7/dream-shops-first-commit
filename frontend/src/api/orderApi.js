import axios from "./axios";

export const placeOrder = (userId) => {
  return axios.post(
    `/api/v1/orders/order?userId=${userId}`
  );
};

export const getUserOrders = (userId) => {
  return axios.get(
    `/api/v1/orders/${userId}/orders`
  );
};