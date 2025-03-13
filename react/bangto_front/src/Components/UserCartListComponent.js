import { useNavigate } from "react-router-dom";

const UserCartListComponent = () => {
  const navigate = useNavigate();

  return <div className="box_Cartlist">
    <div className="cart_Item">
        <input 
            type="image" 
            alt="물품 이미지"/>
        <input 
            type="image" 
            alt="물품 이미지"/>
        <button 
            className="btn_cartlist"
            onClick={() => navigate("/user/cart")}>
            장바구니로이동
        </button>
    </div>
  </div>;
}

export default UserCartListComponent;