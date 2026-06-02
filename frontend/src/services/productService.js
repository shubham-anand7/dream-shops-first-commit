// services/productService.js
import API from "../api/axios";

export const getAllProducts = () => API.get("/products");

export const getProductById = (id) => API.get(`/products/${id}`);