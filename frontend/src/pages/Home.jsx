import { useEffect, useState, useContext } from "react";
import { getAllProducts } from "../api/productApi";
import { CartContext } from "../context/CartContext";
import { Link } from "react-router-dom";
import { getCategories } from "../api/adminApi";
import { getRating } from "../api/reviewApi";


function Home() {

  const [products, setProducts] = useState([]);

  const [search, setSearch] = useState("");

  const [category, setCategory] = useState("");

  const [sort, setSort] = useState("");

  const [categories, setCategories] = useState([]);


  const { addToCart } =
    useContext(CartContext);



  useEffect(() => {

    loadProducts();

    loadCategories();

  }, []);




  const loadProducts = async () => {

    try {

      const res =
        await getAllProducts();


      console.log(
        "PRODUCTS 👉",
        res
      );


      const productsData =
        Array.isArray(res)
          ? res
          : [];



      const productsWithRating =
        await Promise.all(


          productsData.map(

            async (p) => {


              try {


                const ratingRes =
                  await getRating(
                    p.id
                  );



                return {

                  ...p,


                  averageRating:
                    ratingRes.data.data.averageRating,


                  totalReviews:
                    ratingRes.data.data.totalReviews

                };


              }


              catch (error) {


                return {

                  ...p,

                  averageRating: 0,

                  totalReviews: 0

                };


              }


            }

          )

        );



      setProducts(
        productsWithRating
      );



    }


    catch (err) {

      console.error(
        "Error fetching products:",
        err
      );

    }

  };






  const loadCategories = async () => {

    try {

      const res =
        await getCategories();


      console.log(
        "CATEGORIES 👉",
        res.data
      );


      setCategories(
        res?.data?.data || []
      );


    }

    catch (err) {

      console.error(err);

    }

  };






  const filteredProducts =
    [...products]

      .filter((p) => {


        const matchesSearch =

          p?.name
            ?.toLowerCase()
            .includes(
              search.toLowerCase()
            );



        const matchesCategory =

          category === ""

            ? true

            : p?.category?.name === category;




        return (
          matchesSearch &&
          matchesCategory
        );


      })


      .sort((a, b) => {


        if (sort === "low-high") {

          return (
            Number(a.price) -
            Number(b.price)
          );

        }



        if (sort === "high-low") {

          return (
            Number(b.price) -
            Number(a.price)
          );

        }


        return 0;

      });








  return (

    <div className="p-6 bg-gray-100 min-h-screen">


      <h2 className="text-2xl font-bold mb-6">

        Products

      </h2>






      <div className="flex flex-col md:flex-row gap-4 mb-8">


        <input

          type="text"

          placeholder="Search products..."

          value={search}

          onChange={(e) =>
            setSearch(
              e.target.value
            )
          }

          className="border p-3 rounded-lg flex-1"

        />






        <select

          value={category}

          onChange={(e) =>
            setCategory(
              e.target.value
            )
          }

          className="border p-3 rounded-lg"

        >


          <option value="">

            All Categories

          </option>



          {categories.map((cat) => (

            <option

              key={cat.id}

              value={cat.name}

            >

              {cat.name}

            </option>

          ))}


        </select>







        <select

          value={sort}

          onChange={(e) =>
            setSort(
              e.target.value
            )
          }

          className="border p-3 rounded-lg"

        >


          <option value="">

            Sort By

          </option>



          <option value="low-high">

            Price Low → High

          </option>



          <option value="high-low">

            Price High → Low

          </option>


        </select>



      </div>









      {filteredProducts.length === 0 ? (


        <p className="text-gray-500">

          No matching products found

        </p>


      ) : (


        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">


          {filteredProducts.map((p) => (


            <div

              key={p.id}

              className="bg-white shadow-md rounded-xl overflow-hidden hover:shadow-xl transition duration-300"

            >



              <img

                src={

                  p.images?.[0]?.downloadUrl

                    ? `http://localhost:8080${p.images[0].downloadUrl}`

                    : "https://picsum.photos/300"

                }

                alt={p.name}

                className="w-full h-40 object-cover"

              />







              <div className="p-4">



                <Link to={`/product/${p.id}`}>


                  <h3 className="text-lg font-semibold hover:underline cursor-pointer">

                    {p.name}

                  </h3>


                </Link>





                <p className="text-gray-500">

                  {p.brand}

                </p>






                {/* ⭐ RATING */}

                <div className="mt-2">


                  <span>

                    {"⭐".repeat(
                      Math.round(
                        p.averageRating
                      )
                    )}

                  </span>



                  <span className="text-gray-600 ml-2">


                    {p.averageRating.toFixed(1)}

                    {" "}

                    ({p.totalReviews} reviews)


                  </span>


                </div>








                <p className="text-sm text-gray-400">

                  {p.category?.name ||
                    "No Category"}

                </p>






                <p className="text-xl font-bold mt-2">

                  ₹ {p.price}

                </p>







                <button

                  onClick={() =>
                    addToCart(
                      p.id
                    )
                  }

                  className="mt-3 w-full bg-black text-white py-2 rounded-lg hover:bg-gray-800"

                >

                  Add To Cart

                </button>



              </div>



            </div>


          ))}


        </div>


      )}


    </div>

  );

}



export default Home;