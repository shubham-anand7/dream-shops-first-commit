import API from "./axios";

export const fetchCartApi = async (cartId) => {
  return API.get(`/api/v1/carts/${cartId}/my-cart`);
};

export const addToCartApi = async (
  cartId,
  productId,
  quantity = 1
) => {
  let url = `/api/v1/cartItems/item/add?productId=${productId}&quantity=${quantity}`;

  if (cartId) {
    url += `&cartId=${cartId}`;
  }

  return API.post(url);
};

export const updateCartItemApi = async (
  cartId,
  itemId,
  quantity
) => {
  return API.put(
    `/api/v1/cartItems/cart/${cartId}/item/${itemId}/update?quantity=${quantity}`
  );
};

export const removeCartItemApi = async (
  cartId,
  itemId
) => {
  return API.delete(
    `/api/v1/cartItems/cart/${cartId}/item/${itemId}/remove`
  );
};