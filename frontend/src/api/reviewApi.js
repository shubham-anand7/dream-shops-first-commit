import axios from "./axios";


export const addReview = (data) => {

  return axios.post(
    "/api/v1/reviews/add",
    data
  );

};


export const getReviews = (productId) => {

  return axios.get(
    `/api/v1/reviews/${productId}`
  );

};


export const getRating = (productId) => {

  return axios.get(
    `/api/v1/reviews/rating/${productId}`
  );

};