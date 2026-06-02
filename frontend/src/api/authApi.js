import axios from "./axios";

export const loginUser = (
  data,
  cartId
) => {

  return axios.post(
    `/api/v1/auth/login?cartId=${cartId || ""}`,
    data
  );
};

export const registerUser = (data) => {
  return axios.post("/api/v1/auth/register", data);
};