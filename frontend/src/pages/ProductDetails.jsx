import { useParams } from "react-router-dom";
import { useEffect, useState, useContext } from "react";
import axios from "../api/axios";
import { CartContext } from "../context/CartContext";

import {
  getReviews,
  addReview,
} from "../api/reviewApi";


function ProductDetails() {

  const { id } = useParams();


  const [product, setProduct] =
    useState(null);

  const [loading, setLoading] =
    useState(true);


  const [reviews, setReviews] =
    useState([]);


  const [comment, setComment] =
    useState("");


  const [rating, setRating] =
    useState(5);


  const { addToCart } =
    useContext(CartContext);



  useEffect(() => {


    // LOAD PRODUCT
    axios
      .get(`/api/v1/products/product/${id}/product`)
      .then((res) => {

        console.log(
          "DETAIL 👉",
          res.data
        );

        setProduct(
          res.data.data
        );

      })
      .catch((err) =>
        console.error(err)
      )
      .finally(() =>
        setLoading(false)
      );



    // LOAD REVIEWS
    getReviews(id)
      .then((res) => {

        console.log(
          "REVIEWS 👉",
          res.data
        );


        setReviews(
          res.data.data || []
        );

      })
      .catch((err) => {

        console.log(err);

        setReviews([]);

      });


  }, [id]);





  const submitReview = async () => {

    try {


      const userId =
        localStorage.getItem(
          "userId"
        );


      await addReview({

        productId: id,

        userId: userId,

        rating: rating,

        comment: comment,

      });



      alert(
        "Review added ⭐"
      );


      setComment("");



      const res =
        await getReviews(id);



      setReviews(
        res.data.data || []
      );


    }

    catch (err) {

      console.log(err);

      alert(
        "Review failed ❌"
      );

    }

  };





  if (loading) {

    return (

      <div className="p-6 text-xl font-semibold">

        Loading product...

      </div>

    );

  }



  if (!product) {

    return (

      <div className="p-6 text-red-500">

        Product not found

      </div>

    );

  }





  const image =

    product.images?.[0]?.downloadUrl

      ? `http://localhost:8080${product.images[0].downloadUrl}`

      : "https://picsum.photos/500";





  return (

    <div className="min-h-screen bg-gray-100 p-6">


      {/* PRODUCT CARD */}

      <div className="max-w-6xl mx-auto bg-white rounded-2xl shadow-lg overflow-hidden grid md:grid-cols-2 gap-8 p-6">



        {/* IMAGE */}

        <div>

          <img

            src={image}

            alt={product.name}

            className="w-full h-[450px] object-cover rounded-xl"

          />

        </div>





        {/* DETAILS */}

        <div className="flex flex-col justify-center">


          <h1 className="text-4xl font-bold mb-4">

            {product.name}

          </h1>



          <p className="text-gray-500 text-lg mb-2">

            Brand: {product.brand}

          </p>



          <p className="text-gray-500 text-lg mb-4">

            Category: {product.category?.name}

          </p>



          <p className="text-3xl font-bold mb-6">

            ₹ {product.price}

          </p>



          <p className="text-gray-700 leading-7 mb-6">

            {product.description}

          </p>



          <p className="mb-6">

            <b>Stock:</b>{" "}

            {product.inventory}

          </p>



          <button

            onClick={() =>
              addToCart(product.id)
            }

            className="bg-black text-white py-3 rounded-xl"

          >

            Add To Cart

          </button>


        </div>


      </div>





      {/* REVIEW SECTION */}

      <div className="max-w-6xl mx-auto bg-white mt-6 p-6 rounded-xl shadow">



        <h2 className="text-2xl font-bold mb-4">

          Customer Reviews ⭐

        </h2>





        {reviews?.length === 0 ? (

          <p className="text-gray-500">

            No reviews yet

          </p>

        ) : (

          reviews?.map((r) => (

            <div

              key={r.id}

              className="border-b py-3"

            >


              <p>

                {"⭐".repeat(r.rating)}

              </p>


              <p>

                {r.comment}

              </p>


            </div>

          ))

        )}






        <h2 className="mt-6 font-bold">

          Add Review

        </h2>





        <select

          value={rating}

          onChange={(e) =>
            setRating(e.target.value)
          }

          className="border p-2 mt-3"

        >


          <option value="5">

            ⭐⭐⭐⭐⭐

          </option>


          <option value="4">

            ⭐⭐⭐⭐

          </option>


          <option value="3">

            ⭐⭐⭐

          </option>


          <option value="2">

            ⭐⭐

          </option>


          <option value="1">

            ⭐

          </option>


        </select>





        <textarea


          value={comment}


          onChange={(e) =>
            setComment(
              e.target.value
            )
          }


          placeholder="Write review..."


          className="border w-full p-3 mt-3 rounded"


        />





        <button


          onClick={submitReview}


          className="bg-black text-white px-6 py-2 mt-3 rounded"


        >


          Submit Review


        </button>


      </div>


    </div>

  );

}


export default ProductDetails;