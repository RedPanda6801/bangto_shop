import React from 'react';
import { useNavigate } from 'react-router-dom';
import './UserCartComponent.css';

const UserCartComponent = () => 
{
    const navigate = useNavigate();

    return <div className="layout_User_Cart">
            <div className="box_User_Cart">
                <div className="box_User_Cart_SelectAll">
                    <input
                        type="checkbox"
                        className="btn_User_Cart_SelectAll"/>
                    전체 선택
                </div>                
                <div className="box_User_Cart_Item">
                    <table>
                        <tr>
                            <td rowSpan={2}>
                                <input
                                    type="checkbox"
                                    className="btn_User_Cart_Select"/>
                            </td>
                            <td rowSpan={2}>
                                <input
                                    type="image"
                                    className="btn_User_Cart_Img"/>
                            </td>
                            <td colSpan={2}>
                                물품 설명
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <button
                                    className="btn_User_Cart_Opt_Modi">
                                    옵션 수정
                                </button>
                            </td>
                            <td>
                                옵션
                            </td>
                            <td>
                                개수
                            </td>
                        </tr>
                    </table>
                    <table>
                        <tr>
                            <td rowSpan={2}>
                                <input
                                    type="checkbox"
                                    className="btn_User_Cart_Select"/>
                            </td>
                            <td rowSpan={2}>
                                <input
                                    type="image"
                                    className="btn_User_Cart_Img"/>
                            </td>
                            <td colSpan={2}>
                                물품 설명
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <button
                                    className="btn_User_Cart_Opt_Modi">
                                    옵션 수정
                                </button>
                            </td>
                            <td>
                                옵션
                            </td>
                            <td>
                                개수
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div className="box_User_Cart_Info">
                <table>
                    <tr>
                        <td>물품 가격</td>
                        <td>1,150,000 원</td>
                    </tr>
                    <tr>
                        <td>배송비</td>
                        <td>3,000 원</td>
                    </tr>
                    <tr>
                        <td>결제 예상 금액</td>
                        <td>1,153,000 원</td>
                    </tr>
                    <tr>
                        <td colSpan={2}>
                            <button
                                onClick={() =>{navigate("/user/pay")}}>구매하기</button>
                        </td>
                    </tr>                    
                </table>
            </div>
        </div>
}

export default UserCartComponent;