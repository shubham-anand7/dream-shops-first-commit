import axios from "./axios";

export const getDashboard = () => {
  return axios.get(
    "/api/v1/dashboard"
  );
};