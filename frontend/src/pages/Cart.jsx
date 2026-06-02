import { useContext, useState } from "react";
import { CartContext } from "../context/CartContext";
import { placeOrder } from "../api/orderApi";
import { Link } from "react-router-dom";

function Cart() {
  const {
    cart,
    fetchCart,
    cartId,
    removeFromCart,
  } = useContext(CartContext);

  const [loadingId, setLoadingId] = useState(null);

  // ✅ Loading state
  if (!cart) {
    return (
      <div className="p-6">
        Loading cart...
      </div>
    );
  }

  const updateQty = async (itemId, qty) => {
    try {
      setLoadingId(itemId);

      // ✅ Remove item if qty becomes 0
      if (qty <= 0) {
        await removeFromCart(itemId);
        return;
      }

      const res = await fetch(
        `http://localhost:8080/api/v1/cartItems/cart/${cartId}/item/${itemId}/update?quantity=${qty}`,
        {
          method: "PUT",
        }
      );

      if (!res.ok) {
        throw new Error("Quantity update failed");
      }

      await fetchCart(cartId);

    } catch (err) {
      console.error(err);
    } finally {
      setLoadingId(null);
    }
  };

  const handleCheckout = async () => {

  try {

    const userId =
      localStorage.getItem("userId");

    if (!userId) {

      alert("Please login first");

      return;
    }

    const res = await placeOrder(userId);

    console.log(res.data);

    alert("Order placed successfully ✅");

    // ✅ CLEAR LOCAL CART
    localStorage.removeItem("cartId");

    // ✅ RESET CART UI
    await fetchCart(null);

    window.location.reload();

  } catch (err) {

    console.error(err);

    alert("Checkout failed ❌");
  }
};

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-6">
        Your Cart
      </h2>

      {cart.items?.length === 0 ? (
        <div className="text-center py-20">
          <h2 className="text-2xl font-bold mb-4">
            Your cart is empty
          </h2>

          <p className="text-gray-500">
            Add products to continue shopping
          </p>
        </div>
      ) : (
        <>
          {cart.items.map((item) => (
            <div
              key={item.id}
              className="flex justify-between items-center bg-white p-4 mb-4 shadow rounded"
            >
              <div>
                <h3 className="font-semibold">
                  {item.product.name}
                </h3>

                <p>₹ {item.product.price}</p>

                <div className="flex gap-2 items-center mt-2">

                  {/* MINUS */}
                  <button
                    disabled={loadingId === item.id}
                    onClick={() =>
                      updateQty(
                        item.id,
                        item.quantity - 1
                      )
                    }
                    className="px-3 py-1 bg-gray-300 rounded"
                  >
                    -
                  </button>

                  {/* QUANTITY */}
                  <span className="font-semibold">
                    {item.quantity}
                  </span>

                  {/* PLUS */}
                  <button
                    disabled={loadingId === item.id}
                    onClick={() =>
                      updateQty(
                        item.id,
                        item.quantity + 1
                      )
                    }
                    className="px-3 py-1 bg-gray-300 rounded"
                  >
                    + 
                  </button>

                  {/* REMOVE */}
                  <button
                    disabled={loadingId === item.id}
                    onClick={async () => {
                      try {
                        setLoadingId(item.id);

                        await removeFromCart(item.id);

                        await fetchCart(cartId);

                      } catch (err) {
                        console.error(err);
                      } finally {
                        setLoadingId(null);
                      }
                    }}
                    className="bg-red-500 text-white px-3 py-1 rounded ml-3"
                  >
                    {loadingId === item.id
                      ? "Removing..."
                      : "Remove"}
                  </button>
                </div>
              </div>
            </div>
          ))}

          {/* TOTAL */}
      {/* TOTAL */}
<div className="mt-6">

  <h2 className="text-xl font-bold mb-4">
    Total: ₹ {cart.totalAmount}
  </h2>

  <Link to="/checkout">

    <button
      className="bg-green-600 text-white px-6 py-3 rounded-lg"
    >
      Proceed To Checkout
    </button>

  </Link>

</div>

          <button
  onClick={handleCheckout}
  className="mt-6 bg-black text-white px-6 py-3 rounded-lg hover:bg-gray-800"
>
  Checkout
</button>
        </>
      )}
    </div>
  );
}

export default Cart;