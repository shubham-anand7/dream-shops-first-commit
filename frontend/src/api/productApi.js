import API from "./axios";

export const getAllProducts = async () => {
  const response = await API.get("/api/v1/products/all");
  return response.data.data;
};

export const getProductById = async (id) => {
  const response = await API.get(
    `/api/v1/products/product/${id}/product`
  );

  return response.data.data;
};