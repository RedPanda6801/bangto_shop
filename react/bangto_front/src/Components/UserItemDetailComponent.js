import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import './UserItemDetailComponent.css';

const UserItemDetailComponent = () => 
{
    const navigate = useNavigate();
    const [selectedMenu, setSelectedMenu] = useState("내용");
    
    const renderContentForMenu = (menu) => 
    {
        if("내용" == menu)
        {
            return <div>
                    내용
            </div>;
        }
        else if("후기" == menu)
        {
            return <div className="box_Item_Review">
                    <table className="table_Item_Review">
                        <tr>
                            <td>
                                작성자
                            </td>
                            <td rowSpan={3}>
                                좋다
                            </td>
                        </tr>
                        <tr>
                            <td>
                                *****
                            </td>
                        </tr>
                        <tr>
                            <td>
                                2025.02.28
                            </td>
                        </tr>
                    </table>
                </div>;
            }
        else if("문의" == menu)
        {
            return <div className="box_Btn_Item_QNA">                    
                    <button 
                        className="btn_Regi_QNA"
                        onClick={() => navigate("/user/item/qna")}>
                        문의하기
                    </button>
                    <table className="table_Item_QNA">
                        <tr>
                            <td colSpan={2}>
                                문의자
                            </td>
                        </tr>
                        <tr>
                            <td>
                                내용
                            </td>
                            <td>
                                문의 작성일
                            </td>
                        </tr>
                        <tr>
                            <td>
                                답변
                            </td>
                            <td>
                                답변 작성일
                            </td>
                        </tr>
                    </table>
            </div>;
        }
        else
        {
          return <div>선택 메뉴 없음</div>
        }
    };

    return (
        <div className="layout_Item_Detail_Contents">
            <div className="box_Item_Category">
                카테고리
            </div>
            <div className="box_Item_Img">
                이미지
            </div>
            <div className="box_Item_Store">
                매장 이름
            </div>
            <div className="box_Item_Wishlist">
                찜
            </div>
            <div className="box_Item_Name">
                물품 이름
            </div>
            <div className="box_Item_Opt">
                옵션
            </div>
            <div className="box_Item_Price">
                가격
            </div>
            <div className="box_Item_Amount">
                재고 수량
            </div>
            <div className="box_Item_Cart">
                장바구니
            </div>
            <div className="box_Item_Menu">
                <button
                    className="btn_Item_Menu"
                    onClick={() => setSelectedMenu("내용")}>
                    내용
                </button>
                <button
                    className="btn_Item_Menu btn_Item_Menu_Center"
                    onClick={() => setSelectedMenu("후기")}>
                    후기
                </button>
                <button
                    className="btn_Item_Menu"
                    onClick={() => setSelectedMenu("문의")}>
                    문의
                </button>
            </div>
            <div className="box_Item_Contents">
                {renderContentForMenu(selectedMenu)}
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

export default UserItemDetailComponent;