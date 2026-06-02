import { createContext, useState, useEffect } from "react";

export const CartContext = createContext();

export const CartProvider = ({ children }) => {

  const [cartId, setCartId] = useState(
    localStorage.getItem("cartId") || null
  );

  const [cart, setCart] = useState(null);

  // ✅ FETCH CART
  const fetchCart = async (id) => {

    if (!id) {
  setCart(null);
  return;
}

    try {

      const res = await fetch(
        `http://localhost:8080/api/v1/carts/${id}/my-cart`
      );

      // ✅ INVALID CART
      if (!res.ok) {

        localStorage.removeItem("cartId");

        setCart(null);

        return;
      }

      const data = await res.json();

      setCart(data.data);

    } catch (err) {

      console.error("Fetch cart error", err);
    }
  };

  // ✅ LOAD CART ON START
  useEffect(() => {

    if (cartId) {

      fetchCart(cartId);
    }

  }, [cartId]);

  // ✅ ADD TO CART
  const addToCart = async (productId) => {

    try {

      let url =
        `http://localhost:8080/api/v1/cartItems/item/add?productId=${productId}&quantity=1`;

      if (cartId) {

        url += `&cartId=${cartId}`;
      }

      const res = await fetch(url, {
        method: "POST",
      });

      const data = await res.json();

      console.log("Cart Response:", data);

      const id = data?.data?.id;

      // ✅ NEW CART
      if (!cartId && id) {

        setCartId(id);

        localStorage.setItem("cartId", id);

        await fetchCart(id);

      } else {

        await fetchCart(cartId);
      }

    } catch (err) {

      console.error("Add to cart failed", err);
    }
  };

  // ✅ REMOVE ITEM
  const removeFromCart = async (itemId) => {

    try {

      await fetch(
        `http://localhost:8080/api/v1/cartItems/cart/${cartId}/item/${itemId}/remove`,
        {
          method: "DELETE",
        }
      );

      await fetchCart(cartId);

    } catch (err) {

      console.error("Remove item failed", err);
    }
  };

  return (
    <CartContext.Provider
      value={{
        cart,
        cartId,
        addToCart,
        fetchCart,
        removeFromCart,
      }}
    >
      {children}
    </CartContext.Provider>
  );
};