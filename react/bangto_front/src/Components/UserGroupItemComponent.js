import React from "react";
import { useNavigate } from 'react-router-dom';
import './UserGroupItemComponent.css';

const UserGroupItemComponent = () => 
{
    const navigate = useNavigate();
    return (
        <div className="layout_Main_Contents">
            <>
         <div className="box_Group_Promote">
             공동구매 홍보 문구 / 사진
         </div> 
        <div className="box_Group_Date">
             공동 구매 남은 기간
         </div>
         <div className="box_Group_Guide">
             공동 구매 이용 안내 문구
          </div>
            {/* <div className="box_Slide">
                <div className="box_item box_slider">
                    <div className="slider_track">
                        <img
                            src={slides[currentSlide]}
                            alt={`슬라이드 ${currentSlide + 1}`}
                            style={{ display: "block" }}/>
                        <button 
                            onClick={moveToPrevSlide} 
                            className="slider_arrow left">
                            &#10094;
                        </button>
                        <button 
                            onClick={moveToNextSlide} 
                            className="slider_arrow right">
                            &#10095;
                        </button>
                    </div>
                </div>
            </div> */}
            <div className="box_Itemlist">
                <div className="text_Recommend">
                    현재 공동 구매 물품 목록
                </div>
                <div className="table_Itemlist">
                            <input
                                onClick={() => navigate("/user/item")}
                                type="image" 
                                alt="물품 이미지"/><br/><br/>
                            물품 이름<br/><br/>
                            물품 가격
                </div>
                <div className="table_Itemlist">
                            <input 
                                type="image" 
                                alt="물품 이미지"/><br/><br/>
                            물품 이름<br/><br/>
                            물품 가격
                </div>
                <div className="table_Itemlist">
                            <input 
                                type="image" 
                                alt="물품 이미지"/><br/><br/>
                            물품 이름<br/><br/>
                            물품 가격
                </div>
                <div className="table_Itemlist">
                            <input 
                                type="image" 
                                alt="물품 이미지"/><br/><br/>
                            물품 이름<br/><br/>
                            물품 가격
                </div>
                <div className="table_Itemlist">
                            <input 
                                type="image" 
                                alt="물품 이미지"/><br/><br/>
                            물품 이름<br/><br/>
                            물품 가격
                </div>
                <div className="table_Itemlist">
                            <input 
                                type="image" 
                                alt="물품 이미지"/><br/><br/>
                            물품 이름<br/><br/>
                            물품 가격
                </div>
                <div className="table_Itemlist">
                            <input 
                                type="image" 
                                alt="물품 이미지"/><br/><br/>
                            물품 이름<br/><br/>
                            물품 가격
                </div>
                <div className="table_Itemlist">
                            <input 
                                type="image" 
                                alt="물품 이미지"/><br/><br/>
                            물품 이름<br/><br/>
                            물품 가격
                </div>
                <div className="table_Itemlist">
                            <input 
                                type="image" 
                                alt="물품 이미지"/><br/><br/>
                            물품 이름<br/><br/>
                            물품 가격
                </div>
                <div className="table_Itemlist">
                            <input 
                                type="image" 
                                alt="물품 이미지"/><br/><br/>
                            물품 이름<br/><br/>
                            물품 가격
                </div>
                <div className="table_Itemlist">
                            <input 
                                type="image" 
                                alt="물품 이미지"/><br/><br/>
                            물품 이름<br/><br/>
                            물품 가격
                </div>
                <div className="table_Itemlist">
                            <input 
                                type="image" 
                                alt="물품 이미지"/><br/><br/>
                            물품 이름<br/><br/>
                            물품 가격
                </div>
            </div>
            </> 
        </div>
    );
}

export default UserGroupItemComponent;