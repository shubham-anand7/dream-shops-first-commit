import { useEffect, useState } from "react";

import {
  addProduct,
  deleteProduct,
  getCategories,
  uploadProductImage,
  updateProduct
} from "../api/adminApi";

import { getAllProducts } from "../api/productApi";
import { getDashboard } from "../api/dashboardApi";

function Admin() {
  const [selectedFiles, setSelectedFiles] = useState([]);

  const [editingId, setEditingId] =
  useState(null);

const [isEditing, setIsEditing] =
  useState(false);

  const [products, setProducts] = useState([]);

  const [categories, setCategories] = useState([]);

  const [stats, setStats] = useState({
    totalProducts: 0,
    totalUsers: 0,
    totalOrders: 0,
    totalRevenue: 0,
  });

  const [form, setForm] = useState({
    name: "",
    brand: "",
    price: "",
    inventory: "",
    description: "",
    categoryId: "",
  });

  useEffect(() => {
    loadProducts();
    loadCategories();
    loadDashboard();
  }, []);

  const loadProducts = async () => {
    try {
      const res = await getAllProducts();

      console.log("PRODUCTS 👉", res);

      const productData =
        res.data?.data ||
        res.data ||
        [];

      setProducts(
        Array.isArray(productData)
          ? productData
          : []
      );
    } catch (err) {
      console.error(err);
      setProducts([]);
    }
  };

  const loadCategories = async () => {
    try {
      const res = await getCategories();

      console.log("CATEGORIES 👉", res.data);

      setCategories(
        res.data?.data || []
      );
    } catch (err) {
      console.error(err);
      setCategories([]);
    }
  };

  const loadDashboard = async () => {
    try {
      const res =
        await getDashboard();

      console.log(
        "DASHBOARD 👉",
        res.data
      );

      setStats(
        res.data?.data ||
          res.data || {
            totalProducts: 0,
            totalUsers: 0,
            totalOrders: 0,
            totalRevenue: 0,
          }
      );
    } catch (err) {
      console.error(err);

      setStats({
        totalProducts: 0,
        totalUsers: 0,
        totalOrders: 0,
        totalRevenue: 0,
      });
    }
  };

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]:
        e.target.value,
    });
  };

  const handleEdit = (product) => {

  setIsEditing(true);

  setEditingId(product.id);

  setForm({
    name: product.name,
    brand: product.brand,
    price: product.price,
    inventory: product.inventory,
    description: product.description,
    categoryId:
      product.category?.id || "",
  });

};

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const category =
        categories.find(
          (c) =>
            c.id ==
            form.categoryId
        );

      const payload = {
        name: form.name,
        brand: form.brand,
        price: form.price,
        inventory:
          form.inventory,
        description:
          form.description,
        category,
      };

   let res;

if (isEditing) {

  res =
    await updateProduct(
      editingId,
      payload
    );

} else {

  res =
    await addProduct(
      payload
    );

}

      const productId =
        res.data?.data?.id;

      if (
        selectedFiles.length > 0 &&
        productId
      ) {
        await uploadProductImage(
          productId,
          selectedFiles
        );
      }

      alert(
  isEditing
    ? "Product Updated ✅"
    : "Product Added ✅"
);

      setForm({
        name: "",
        brand: "",
        price: "",
        inventory: "",
        description: "",
        categoryId: "",
      });

      setSelectedFiles([]);

      loadProducts();
      loadDashboard();
    } catch (err) {
      console.error(err);

      alert(
        "Failed To Add Product ❌"
      );
    }
  };

  const handleDelete =
    async (id) => {
      const confirmDelete =
        window.confirm(
          "Delete this product?"
        );

      if (!confirmDelete)
        return;

      try {
        await deleteProduct(id);

        alert(
          "Product Deleted ✅"
        );

        loadProducts();
        loadDashboard();
      } catch (err) {
        console.error(err);

        alert(
          "Delete Failed ❌"
        );
      }
    };

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h1 className="text-4xl font-bold mb-8">
        Admin Dashboard
      </h1>

      {/* DASHBOARD CARDS */}

      <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
        <div className="bg-white shadow p-6 rounded-xl">
          <h2 className="text-gray-500">
            Products
          </h2>

          <p className="text-3xl font-bold">
            {
              stats.totalProducts
            }
          </p>
        </div>

        <div className="bg-white shadow p-6 rounded-xl">
          <h2 className="text-gray-500">
            Users
          </h2>

          <p className="text-3xl font-bold">
            {stats.totalUsers}
          </p>
        </div>

        <div className="bg-white shadow p-6 rounded-xl">
          <h2 className="text-gray-500">
            Orders
          </h2>

          <p className="text-3xl font-bold">
            {
              stats.totalOrders
            }
          </p>
        </div>

        <div className="bg-white shadow p-6 rounded-xl">
          <h2 className="text-gray-500">
            Revenue
          </h2>

          <p className="text-3xl font-bold">
            ₹
            {
              stats.totalRevenue
            }
          </p>
        </div>
      </div>

      {/* ADD PRODUCT */}

      <div className="bg-white p-6 rounded-xl shadow mb-10">
        <h2 className="text-2xl font-bold mb-4">
          Add Product
        </h2>

        <form
          onSubmit={
            handleSubmit
          }
          className="grid md:grid-cols-2 gap-4"
        >
          <input
            name="name"
            placeholder="Product Name"
            value={form.name}
            onChange={
              handleChange
            }
            className="border p-3 rounded"
            required
          />

          <input
            name="brand"
            placeholder="Brand"
            value={form.brand}
            onChange={
              handleChange
            }
            className="border p-3 rounded"
            required
          />

          <input
            type="number"
            name="price"
            placeholder="Price"
            value={form.price}
            onChange={
              handleChange
            }
            className="border p-3 rounded"
            required
          />

          <input
            type="number"
            name="inventory"
            placeholder="Inventory"
            value={
              form.inventory
            }
            onChange={
              handleChange
            }
            className="border p-3 rounded"
            required
          />

          <select
            name="categoryId"
            value={
              form.categoryId
            }
            onChange={
              handleChange
            }
            className="border p-3 rounded"
            required
          >
            <option value="">
              Select Category
            </option>

            {categories.map(
              (cat) => (
                <option
                  key={
                    cat.id
                  }
                  value={
                    cat.id
                  }
                >
                  {cat.name}
                </option>
              )
            )}
          </select>

          <textarea
            name="description"
            placeholder="Description"
            value={
              form.description
            }
            onChange={
              handleChange
            }
            className="border p-3 rounded md:col-span-2"
          />

          <input
            type="file"
            multiple
            onChange={(
              e
            ) =>
              setSelectedFiles(
                Array.from(
                  e.target.files
                )
              )
            }
            className="border p-3 rounded md:col-span-2"
          />

         <button className="bg-black text-white py-3 rounded-lg hover:bg-gray-800 md:col-span-2">

  {isEditing
    ? "Update Product"
    : "Add Product"}

</button>
        </form>
      </div>

      {/* PRODUCTS TABLE */}

      <div className="bg-white rounded-xl shadow overflow-hidden">
        <table className="w-full">
          <thead className="bg-black text-white">
            <tr>
              <th className="p-4">
                Name
              </th>

              <th className="p-4">
                Brand
              </th>

              <th className="p-4">
                Price
              </th>

              <th className="p-4">
                Inventory
              </th>

              <th className="p-4">
                Actions
              </th>
            </tr>
          </thead>

          <tbody>
            {Array.isArray(
              products
            ) &&
              products.map(
                (
                  product
                ) => (
                  <tr
                    key={
                      product.id
                    }
                    className="border-b"
                  >
                    <td className="p-4">
                      {
                        product.name
                      }
                    </td>

                    <td className="p-4">
                      {
                        product.brand
                      }
                    </td>

                    <td className="p-4">
                      ₹
                      {
                        product.price
                      }
                    </td>

                    <td className="p-4">
                      {
                        product.inventory
                      }
                    </td>

                   <td className="p-4 flex gap-2">

  <button
    onClick={() =>
      handleEdit(product)
    }
    className="bg-blue-500 text-white px-4 py-2 rounded"
  >
    Edit
  </button>

  <button
    onClick={() =>
      handleDelete(
        product.id
      )
    }
    className="bg-red-500 text-white px-4 py-2 rounded"
  >
    Delete
  </button>

</td>
                  </tr>
                )
              )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Admin;