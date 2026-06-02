import axios from "./axios";

export const addProduct = (data) => {
  return axios.post(
    "/api/v1/products/add",
    data
  );
};

export const updateProduct = (
  id,
  data
) => {
  return axios.put(
    `/api/v1/products/product/${id}/update`,
    data
  );
};

export const deleteProduct = (id) => {
  return axios.delete(
    `/api/v1/products/product/${id}/delete`
  );
};

export const getCategories = () => {
  return axios.get(
    "/api/v1/categories/all"
  );
};

export const uploadProductImage = (
  productId,
  files
) => {

  const formData =
    new FormData();

  files.forEach((file) => {
    formData.append(
      "files",
      file
    );
  });

  return axios.post(
    `/api/v1/images/upload?productId=${productId}`,
    formData,
    {
      headers: {
        "Content-Type":
          "multipart/form-data",
      },
    }
  );
};