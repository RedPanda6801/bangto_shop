import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import './UserMainComponent.css';

const UserMainComponent = () => 
{
    const navigate = useNavigate();
    const [searchText, setSearchText] = useState("");
    const [currentSlide, setCurrentSlide] = useState(0); 
    const [currentWishlist, setCurrentWishlist] = useState(0);

    const slides = ["/images/03_main/main_01.jpg", "/images/03_main/main_02.jpg", "/images/03_main/main_03.jpg"];
    const wishlistItems = [
        { name: "물품 1", price: "10,000 원" },
        { name: "물품 2", price: "20,000 원" },
        { name: "물품 3", price: "30,000 원" },
        { name: "물품 4", price: "40,000 원" },
        { name: "물품 5", price: "50,000 원" },
        { name: "물품 6", price: "60,000 원" },
        { name: "물품 7", price: "70,000 원" },
        { name: "물품 8", price: "80,000 원" },
        { name: "물품 9", price: "90,000 원" },
        { name: "물품 10", price: "100,000 원" },
        { name: "물품 11", price: "10,000 원" }
    ];
    const totalSlides = slides.length;
    const totalWishlistItems = wishlistItems.length;
    const itemsPerPage = 5;

    const moveToNextSlide = () => { setCurrentSlide((prev) => (prev + 1) % totalSlides);                };
    const moveToPrevSlide = () => { setCurrentSlide((prev) => (prev - 1 + totalSlides) % totalSlides);  };

    useEffect(() => 
    {
        const autoSlide = setInterval(moveToNextSlide, 3000);
        return () => clearInterval(autoSlide);
    }, []);

    const moveToNextWishlist = () => 
    {
        if (currentWishlist < Math.ceil(totalWishlistItems / itemsPerPage) - 1) 
        {
            setCurrentWishlist((prev) => prev + 1);
        }
    };

    const moveToPrevWishlist = () => 
    {
        if (currentWishlist > 0) 
        {
            setCurrentWishlist((prev) => prev - 1);
        }
    };

    return (
        //<div className="full_Screen">
            <div className="layout_Main_Contents">
                <div className="box">
                    <div className="box_Category">
                        <input
                            className="btn_Category"
                            type="image"
                            src={`/images/02_icon/icon_03.jpg`}
                            alt="카테고리버튼">
                        </input>
                        <div className="list_Category">
                            <button
                                className="btn_Main_Category">
                                의류
                            </button>
                            <button
                                className="btn_Main_Category">
                                화장품
                            </button>
                            <button
                                className="btn_Main_Category">
                                전자기기
                            </button>
                            <button
                                className="btn_Main_Category">
                                가구
                            </button>
                            <button
                                className="btn_Main_Category">
                                식품
                            </button>
                            <button
                                className="btn_Main_Category">
                                스포츠 용품
                            </button>
                            <button
                                className="btn_Main_Category">
                                유아 용품
                            </button>
                            <button
                                className="btn_Main_Category">
                                차량 용품
                            </button>
                            <button
                                className="btn_Main_Category">
                                반려동물 용품
                            </button>
                            <button
                                className="btn_Main_Category">
                                도서
                            </button>
                        </div>
                    </div>
                    <div className="box_Search">
                        <div className="box_Search_Container">
                            <input
                                type="text"
                                className="main_Search_Input"
                                placeholder="검색어를 입력하세요"
                                value={searchText}
                                onChange={(e) => setSearchText(e.target.value)}/>
                            <button 
                                className="main_Search_Btn">
                                검색
                            </button>
                        </div>
                    </div>
                </div>   
                <div className="box_Slide">
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
                </div>
                <div className="box_Wishlist">
                    <div className="text_Wishlist">
                        찜 목록
                    </div>
                    <div className="wishlist_Item">
                        <button 
                            className="wishlist_arrow left"
                            onClick={moveToPrevWishlist}
                            style={{ cursor: currentWishlist === 0 ? 'default' : 'pointer' }}>
                            &#10094;
                        </button>
                        <div className="wishlist_Row">
                            <div className="wishlist_Item_Container">
                                {wishlistItems
                                    .slice(currentWishlist * itemsPerPage, (currentWishlist + 1) * itemsPerPage)
                                    .map((item) => (
                                        <div className="wishlist_Item_Card">
                                            <input 
                                                type="image" 
                                                alt="물품 이미지"/>
                                            <div>{item.name}</div>
                                            <div>{item.price}</div>
                                        </div>
                                    ))}
                            </div>
                        </div>
                        {currentWishlist < Math.ceil(totalWishlistItems / itemsPerPage) - 1 && (
                            <button 
                                className="wishlist_arrow right"
                                onClick={moveToNextWishlist}>
                                &#10095;
                            </button>
                        )}                    
                    </div>
                </div>
                <div className="box_Itemlist">
                    <div className="text_Recommend">
                        추천상품
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
        //</div>
    );
}

export default UserMainComponent;