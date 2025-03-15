import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from 'react-router-dom';
import '../UserItemDetailComponent.css';
//import UserCartListComponent from "./UserCartListComponent";
import axios from "axios";
import { resContent } from "../UtilComponent/ResponseData";
import { CategoryType } from "../UtilComponent/DataFormat";

const UserGroupItemDetailComponent = (props) => 
{
    const navigate = useNavigate();
    const location = useLocation();
    const [selectedMenu, setSelectedMenu] = useState("내용");
    const [totalImage, setTotalImage] = useState(0);
    const [currentImage, setCurrentImage] = useState(0);
    const [amount, setAmount] = useState(0);
    const [payModal, setPayModal] = useState(false);

    const handleGroupPay = (item) => {
        if(amount <= 0 || amount > item.limitPerBuyer){
            alert("개수를 확인해주세요.")
            return;
        }else{
            localStorage.setItem("group-item", JSON.stringify(item));
            navigate(`/group-item/pay?amount=${amount}`);
        }
    }


    return (
        <>
        {
            props.groupItem === undefined ? null:
            <div className="layout_Item_Detail_Contents">
                <div className="box_Item_Detail">
                    <div className="box_Item_Category">
                        {CategoryType[props.groupItem.item.category]}
                    </div>
                    <div className="box_Item_Infomation">
                        <div className="box_Item_Img">
                            <img className="item_Img" src={`${process.env.REACT_APP_IMG_PUBLIC_URI}/03_upload/${props.groupItem.item.img?.split("/")[currentImage] || ""}`} alt="물품 이미지" />
                            <button
                                onClick={() => setCurrentImage(currentImage - 1)}
                                className="imglist_arrow arrow_left"
                                style={currentImage === 0 ? {display:"none"} : {}}>
                                &#10094;
                            </button>
                            <button
                                onClick={() => setCurrentImage(currentImage + 1)}
                                className="imglist_arrow arrow_right"
                                style={currentImage === totalImage - 1 ? {display:"none"} : {}}>
                                &#10095;
                            </button>
                        </div>
                        <div className="box_Item_Infobox">
                            
                            <div className="box_Item_Store">
                                {props.groupItem.item.store?.name}
                            </div>
                            <div className="box_Item_Name">
                                {props.groupItem.item.title}
                            </div>
                            <div className="box_Item_Price">
                                <div style={{display: "flex", flexDirection: "column"}}>
                                    가격 : 
                                    <del style={{fontSize: "15px"}}>{props.groupItem.item.price + props.groupItem.option.addPrice}원</del>
                                    <div style={{color: "red"}}>{Number((props.groupItem.item.price + props.groupItem.option.addPrice) * 0.9)}원</div>
                                    <br></br> 공동구매 10% 할인!
                                </div>
                            </div>
                            <div className="box_Item_Amount">
                                재고 수량 : {props.groupItem.nowAmount ? props.groupItem.nowAmount : props.groupItem.maxAmount}개
                            </div>
                            <div className="box_Item_Amount">
                                옵션 정보 : {props.groupItem.option.optionInfo}
                            </div>
                            <div>
                                <div className="text_Amount">
                                <input type="number" onChange={(e)=> setAmount(e.target.value)} value={amount} className="input_Amount"/>개
                                </div>
                                <div>최대 {props.groupItem.limitPerBuyer}개까지 구매 가능합니다.</div>
                                <div
                                    className="box_Item_Cart"
                                    onClick={()=> handleGroupPay(props.groupItem)}
                                >
                                    결제하기
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="box_Item_Content">
                        <div className="box_Item_Menu">
                            <button
                                className="btn_Item_Menu"
                                onClick={() => setSelectedMenu("내용")}>
                                내용
                            </button>
                        </div>
                        <div className="box_Item_Contents">
                            <div className="box_Content">
                                {props.groupItem.item.content}
                            </div>
                        </div>              
                    </div>
                </div>
            </div>
            }
            <div
        className="box_Item_Cart"
        onClick={()=> props.setGroupItem(null)}
        >
        뒤로가기
        </div>
        <br></br>
        </>
    );
}

export default UserGroupItemDetailComponent;