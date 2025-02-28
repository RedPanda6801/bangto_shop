import React from "react";
import { useNavigate } from 'react-router-dom';
import './UserGroupItemComponent.css';

const UserGroupItemComponent = () => 
{
    const navigate = useNavigate();

    return (
        <div className="layout_Group_Contents">   
            <div className="box_Group_Promote">
                공동구매 홍보 문구 / 사진
            </div> 
            <div className="box_Group_Date">
                공동 구매 남은 기간
            </div>
            <div className="box_Group_Guide">
                공동 구매 이용 안내 문구
            </div>
            
            <div className="box_Group_Itemlist">
                <div className="table_Group_Itemlist">
                    <input 
                        onClick={() => navigate("/user/gorupitem/detail")}
                        type="image" 
                        alt="물품 이미지"/><br/><br/>
                    물품 이름<br/><br/>
                    물품 가격<br/><br/>
                    남은 수량 / 전체 수량
                </div>
                <div className="table_Group_Itemlist">
                    <input 
                        type="image" 
                        alt="물품 이미지"/><br/><br/>
                    물품 이름<br/><br/>
                    물품 가격<br/><br/>
                    남은 수량 / 전체 수량
                </div>
                <div className="table_Group_Itemlist">
                    <input 
                        type="image" 
                        alt="물품 이미지"/><br/><br/>
                    물품 이름<br/><br/>
                    물품 가격<br/><br/>
                    남은 수량 / 전체 수량
                </div>
                <div className="table_Group_Itemlist">
                    <input 
                        type="image" 
                        alt="물품 이미지"/><br/><br/>
                    물품 이름<br/><br/>
                    물품 가격<br/><br/>
                    남은 수량 / 전체 수량
                </div>
                <div className="table_Group_Itemlist">
                    <input 
                        type="image" 
                        alt="물품 이미지"/><br/><br/>
                    물품 이름<br/><br/>
                    물품 가격<br/><br/>
                    남은 수량 / 전체 수량
                </div>
                <div className="table_Group_Itemlist">
                    <input 
                        type="image" 
                        alt="물품 이미지"/><br/><br/>
                    물품 이름<br/><br/>
                    물품 가격<br/><br/>
                    남은 수량 / 전체 수량
                </div>
            </div>
        </div>
    );
}

export default UserGroupItemComponent;