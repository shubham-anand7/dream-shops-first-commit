import { useContext } from "react";
import { CartContext } from "../context/CartContext";
import { placeOrder } from "../api/orderApi";
import { useNavigate } from "react-router-dom";


function Checkout(){

const {
cart
} = useContext(CartContext);


const navigate = useNavigate();


const orderNow = async()=>{


try{


const userId =
localStorage.getItem("userId");


await placeOrder(userId);


alert(
"Order placed successfully ✅"
);


localStorage.removeItem(
"cartId"
);


navigate("/orders");


}catch(error){

console.log(error);

alert(
"Order failed ❌"
);

}

};



if(!cart){

return <h2>Loading...</h2>

}


return(

<div className="p-8">


<h1 className="text-3xl font-bold mb-6">

Checkout

</h1>


{
cart.items.map(item=>(


<div
key={item.id}
className="bg-white p-4 shadow mb-3"
>

<h2>
{item.product.name}
</h2>


<p>
Quantity : {item.quantity}
</p>


<p>
₹ {item.totalPrice}
</p>


</div>


))
}


<h2 className="text-xl font-bold">

Total : ₹ {cart.totalAmount}

</h2>


<button

onClick={orderNow}

className="
mt-5 bg-green-600
text-white px-6 py-3 rounded
"

>

Place Order

</button>


</div>


)

}


export default Checkout;