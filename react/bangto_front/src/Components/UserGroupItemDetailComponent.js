import React from "react";
import { useNavigate } from 'react-router-dom';
import './UserGroupItemDetailComponent.css';

const UserGroupItemDetailComponent = () => 
{
    const navigate = useNavigate();
    
    return (
        <div className="layout_Group_Item_Detail_Contents">
            <div className="box_Group_Item_Category">
                카테고리
            </div>
            <div className="box_Group_Item_Img">
                이미지
            </div>
            <div className="box_Group_Item_Store">
                매장 이름
            </div>
            <div className="box_Group_Item_Name">
                물품 이름
            </div>
            <div className="box_Group_Item_Opt">
                옵션
            </div>
            <div className="box_Group_Item_Limit">
                1인 구매 제한 수량
            </div>
            <div className="box_Group_Item_Amount">
                남은 수량 / 전체 수량
            </div>
            <div className="box_Group_Item_Price">
                할인 정보
            </div>
            <div className="box_Group_Item_Pay">
                구매하기
            </div>
            <div className="box_Group_Item_Contents">
                제품 설명
            </div>            
            <div className="box_Cartlist">
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
            </div>
        </div>
    );
}

export default UserGroupItemDetailComponent;