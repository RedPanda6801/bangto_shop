import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from 'react-router-dom';
import './UserItemDetailComponent.css';
import UserCartListComponent from "./UserCartListComponent";
import axios from "axios";
import { resContent } from "./UtilComponent/ResponseData";
import { CategoryType } from "./UtilComponent/DataFormat";

const UserItemDetailComponent = () => 
{
    const navigate = useNavigate();
    const location = useLocation();
    const itemPk = location.state.itemPk;
    const [selectedMenu, setSelectedMenu] = useState("내용");
    const [item, setItem] = useState({});
    const [totalImage, setTotalImage] = useState(0);
    const [currentImage, setCurrentImage] = useState(0);
    const [isFavored, setIsFavored] = useState(false);
    const [isFavorite20, setIsFavorite20] = useState(false);
    const [amount, setAmount] = useState(0);
    const [selectedOption, setSelectedOption] = useState({});

    useEffect(() => {
        axios.get(`${process.env.REACT_APP_BACKEND_SERVER_PORT}/item/get-detail/${itemPk}`)
        .then((res) => {
            const rescontent = resContent(res);
            setItem(rescontent);
            setTotalImage(rescontent.img.split("/").length);
            setCurrentImage(0);
            console.log(rescontent);
            axios.get(`${process.env.REACT_APP_BACKEND_SERVER_PORT}/favorite/get-list/1`,
                {   
                    withCredentials: true,
                    headers: {"Authorization": `Bearer ${localStorage.getItem("token")}`}
                }
            ).then((res) => {
                const rescontent = resContent(res);
                let favored = rescontent.some(content => content.item.id === itemPk);
                setIsFavored(favored);
                setIsFavorite20(rescontent.length >= 20);
            }).catch((err) => {
                setIsFavored(false);
                setIsFavorite20(false);
            })
        })
        
    }, [itemPk])

    const handleWishlist = () => {
        if(isFavorite20) {
            alert("찜은 최대 20개까지 할 수 있습니다.");
            return;
        }
        axios.post("http://localhost:9000/favorite/add", {
            id : itemPk
        }, { withCredentials : true,
            headers: {
                "Authorization": `Bearer ${localStorage.getItem("token")}`,
            }
        });
        setIsFavored(true);
    }

    const handleUnwishlist = () => {
        axios.post("http://localhost:9000/favorite/delete", {
            id : itemPk
        }, { withCredentials : true,
            headers: {
                "Authorization": `Bearer ${localStorage.getItem("token")}`,
            }
        });
        setIsFavored(false);
    }

    const handleCart = () => {
        axios.post("http://localhost:9000/cart/add", {
            optionPk : itemPk,
            amount
        }, { withCredentials : true,
            headers: {
                "Authorization": `Bearer ${localStorage.getItem("token")}`,
            }
        }).catch((err) => {
            alert(err.response.data);
        });
    }

    const handleOption = (e) => {
        const selectedId = e.target.value;
        const selectedObj = item.options.find(option => option.id.toString() === selectedId);
        setSelectedOption(selectedObj);
        if(selectedObj.amount < amount) {
            setAmount(selectedObj.amount);
        }
    }

    const handleAmount = (e) => {
        setAmount(selectedOption.amount < e.target.value ? selectedOption.amount : (e.target.value < 0 ? 0 : e.target.value));
    }
    
    const renderContentForMenu = (menu) => 
    {
        if("내용" == menu)
        {
            return <div className="box_Content">
                {item.content}
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
            item === undefined ? null:
            <div className="layout_Item_Detail_Contents">
                <div className="box_Item_Detail">
                    <div className="box_Item_Category">
                        {CategoryType[item.category]}
                    </div>
                    <div className="box_Item_Infomation">
                        <div className="box_Item_Img">
                            <img className="item_Img" src={`${process.env.REACT_APP_IMG_PUBLIC_URI}/03_upload/${item.img?.split("/")[currentImage] || ""}`} alt="물품 이미지" />
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
                            {isFavored ?
                            <div 
                                className="box_Item_Unwishlist"
                                onClick={handleUnwishlist}
                            >
                                찜 해제
                            </div>:
                            <div 
                                className="box_Item_Wishlist"
                                onClick={handleWishlist}
                            >
                                찜
                            </div>}
                            <div className="box_Item_Store">
                                {item.store?.name}
                            </div>
                            <div className="box_Item_Name">
                                {item.title}
                            </div>
                            <div className="box_Item_Price">
                                가격 : {item.price}원
                            </div>
                            <div className="box_Item_Opt">
                                <select value={selectedOption?.id || ""} onChange={handleOption}>
                                    {
                                        item.options?.map((option) => {
                                            return <option key={option.id} value={option.id}>
                                                + {option.addPrice}원 | {option.optionInfo} | 재고 : {option.amount}개
                                            </option>
                                        })
                                    }
                                </select>
                            </div>
                            <div className="box_Item_Amount">
                                재고 수량 : {item.amount}개
                            </div>
                            <div className="box_Add_Cart">
                                <input type="number" onChange={handleAmount} value={amount} className="input_Amount"/>
                                <div className="text_Amount">
                                    개
                                </div>
                                <div
                                    className="box_Item_Cart"
                                    onClick={handleCart}
                                >
                                    장바구니에 담기
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
                        
                    </div>
                </div>
                <UserCartListComponent />
            </div>
        
    );
}

export default UserItemDetailComponent;