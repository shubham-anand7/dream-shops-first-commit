import { useState } from "react";
import { loginUser } from "../api/authApi";
import { useNavigate } from "react-router-dom";

function Login() {
  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
  e.preventDefault();

  try {

    const cartId =
  localStorage.getItem("cartId");

const res = await loginUser(
  form,
  cartId
);

    // ✅ SAVE USER SESSION
    localStorage.setItem(
      "token",
      res.data.token
    );

    localStorage.setItem(
      "userId",
      res.data.userId
    );

    localStorage.setItem(
      "firstName",
      res.data.firstName
    );

    localStorage.setItem(
      "email",
      res.data.email
    );

    alert("Login successful ✅");

    navigate("/");

    window.location.reload();

  } catch (err) {

    console.error(err);

    alert("Login failed ❌");
  }
};

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">Login</h2>

      <form onSubmit={handleSubmit} className="flex flex-col gap-4 max-w-md">
        <input
          type="email"
          name="email"
          placeholder="Email"
          value={form.email}
          onChange={handleChange}
          className="border p-2"
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
          className="border p-2"
        />

        <button className="bg-black text-white py-2">
          Login
        </button>
      </form>
    </div>
  );
}

export default Login;