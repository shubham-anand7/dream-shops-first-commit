import { Link, useNavigate } from "react-router-dom";
import { useContext } from "react";
import { CartContext } from "../context/CartContext";

function Navbar() {

  const { cart } = useContext(CartContext);

  const token = localStorage.getItem("token");

  const firstName =
    localStorage.getItem("firstName");

  const navigate = useNavigate();

  const logout = () => {

    // ✅ CLEAR EVERYTHING
    localStorage.clear();

    navigate("/login");

    window.location.reload();
  };

  return (
    <nav className="bg-black text-white p-4 flex justify-between items-center">

      {/* LOGO */}
      <Link to="/">
        <h1 className="text-2xl font-bold">
          DreamShop
        </h1>
      </Link>

      {/* NAV LINKS */}
      <div className="flex gap-6 items-center">

        <Link to="/">
          Home
        </Link>

        {/* ✅ CART ALWAYS VISIBLE */}
        <Link to="/cart">
          Cart (
          {cart?.items?.reduce(
            (total, item) =>
              total + item.quantity,
            0
          ) || 0}
          )
        </Link>

        {/* NOT LOGGED IN */}
        {!token ? (
          <>
            <Link to="/login">
              Login
            </Link>

            <Link to="/register">
              Register
            </Link>
          </>
        ) : (

          /* LOGGED IN */
          <div className="flex items-center gap-4">

            <span className="font-semibold">
  Hi{firstName ? `, ${firstName}` : ""}
</span>

<Link to="/orders">
  Orders
</Link>

            <Link to="/admin">
              Admin
            </Link>

            <button
              onClick={logout}
              className="bg-red-500 px-4 py-1 rounded hover:bg-red-600"
            >
              Logout
            </button>

          </div>
        )}

      </div>
    </nav>
  );
}

export default Navbar;