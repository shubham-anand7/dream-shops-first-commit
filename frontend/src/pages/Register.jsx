import { useState } from "react";
import { registerUser } from "../api/authApi";
import { useNavigate } from "react-router-dom";

function Register() {

  const [user, setUser] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    setUser({
      ...user,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {

      await registerUser(user);

      alert("Registered successfully ✅");

      navigate("/login");

    } catch (err) {
      console.error(err);

      alert("Registration failed ❌");
    }
  };

  return (
    <div className="min-h-screen flex justify-center items-center bg-gray-100">

      <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-md">

        <h2 className="text-3xl font-bold mb-6 text-center">
          Register
        </h2>

        <form
          onSubmit={handleSubmit}
          className="space-y-4"
        >

          <input
            type="text"
            name="firstName"
            placeholder="First Name"
            onChange={handleChange}
            className="w-full border p-3 rounded"
          />

          <input
            type="text"
            name="lastName"
            placeholder="Last Name"
            onChange={handleChange}
            className="w-full border p-3 rounded"
          />

          <input
            type="email"
            name="email"
            placeholder="Email"
            onChange={handleChange}
            className="w-full border p-3 rounded"
          />

          <input
            type="password"
            name="password"
            placeholder="Password"
            onChange={handleChange}
            className="w-full border p-3 rounded"
          />

          <button
            className="w-full bg-black text-white py-3 rounded-lg hover:bg-gray-800"
          >
            Register
          </button>

        </form>
      </div>
    </div>
  );
}

export default Register;