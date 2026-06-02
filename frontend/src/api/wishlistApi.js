import axios from "./axios";


// ADD WISHLIST

export const addWishlist = (data) => {

  return axios.post(
    "/api/v1/wishlist/add",
    data
  );

};



// GET USER WISHLIST

export const getWishlist = (userId) => {

  return axios.get(
    `/api/v1/wishlist/${userId}`
  );

};




// REMOVE WISHLIST

export const removeWishlist = (
  userId,
  productId
) => {

  return axios.delete(
    `/api/v1/wishlist/remove/${userId}/${productId}`
  );

};