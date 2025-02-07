import React, { useState, useEffect } from 'react';
import axios from "axios";
import { useNavigate } from 'react-router-dom';
import './StoreAuthComponent.css'; 

const StoreAuthComponent = (props) => 
{ 
  // 판매자 인증 X
  const navigate = useNavigate();
  const [Seller, setSeller] = useState("");
  const [Store, setStore] = useState("");
  const [Busi, setBusi] = useState("");
  const [storelist, setStorelist] = useState([]);
  const [isSeller, setIsSeller] = useState(false);
  const [page, setPage] = useState(1);

  useEffect(()=>{
    axios.get("http://localhost:9000/seller/get-info", {
           headers: {
             "Authorization": `Bearer ${localStorage.getItem("token")}`,
           }
         }).then((res)=> {
        axios.get("http://localhost:9000/store/get-list", {
          headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`,
          }
        }).then((res)=> {
          setStorelist(res.data); 
          setIsSeller(true); 
        })
    }).catch((err)=>{
        console.log(err);
        setIsSeller(false);
    })
  },[])

  // 필수 입력 항목 확인
  const isFormValid = Seller && Store && Busi;

  // 입력 항목 유효성 검사
  const handleStoreAuth = async () => 
  {    
    // 유효성 검사 기준
    const validChar = /^[a-zA-Z가-힣0-9]+$/;
    const busiChar = /^\d{3}-\d{2}-\d{5}$/;

    let isSellerValid = false;
    let isStoreValid = false;
    let isBusiValid = false;
  
    // 유효성 검사
    if( 1 < Seller.length && 11 > Seller.length && validChar.test(Seller) ) isSellerValid = true;
    if( 0 < Store.length  && 11 > Store.length  && validChar.test(Store)  ) isStoreValid = true;
    if( busiChar.test(Busi) )                                               isBusiValid = true;

    // 유효성 검사 결과
    if     (!isSellerValid) { alert("판매자 이름 입력이 잘못되었습니다. (2 ~ 10자로 입력 가능, 특수문자 입력 불가)"); }  
    else if(!isStoreValid)  { alert("매장 이름 입력이 잘못되었습니다. (1 ~ 10자로 입력 가능, 특수문자 입력 불가)");   }   
    else if(!isBusiValid)   { alert("사업자 번호 입력이 잘못되었습니다. (xxx-xx-xxxxx로 구성된 10자리 숫자)");       }
    else if(isSellerValid && isStoreValid && isBusiValid)
    {
      alert("판매자 인증 신청이 완료되었습니다."); 
      navigate("/");
    }
  };

  const handleStoreDetail = async(storeId) =>
  {
    const storeResponse = await axios.get(`http://localhost:9000/store/get-detail/${storeId}`,{
      headers: {
        "Authorization": `Bearer ${localStorage.getItem("token")}`,
      }
    })
    
    const itemResponse = await axios.get(`http://localhost:9000/item/get-items/filter/${storeId}/${page}`,{
      headers: {
        "Authorization": `Bearer ${localStorage.getItem("token")}`,
      }
    })

    console.log(itemResponse);
  }

  // 판매자 인증 O
  const [openMenu, setOpenMenu] = useState("");

  const sellerMenu = 
  [
    {title: "매장 목록", submenu: storelist},
    {title: "QNA", submenu: storelist},
    {title: "주문 목록", submenu: storelist},
    {title: "공동 구매 관리", submenu: storelist}
  ];

  const toggleMenu = (index) =>
  {
    setOpenMenu(openMenu == index ? null : index);
  }
  
  return (
    <>
    {isSeller != true ? (
      // 판매자 인증 X
      <div className="layout_StoreAuthApply">
        <div className="box_StoreAuthApply">
          <table>
            <tr>
              <td>
                <input
                  className="Auth_Seller_Name"
                  type="text"
                  placeholder="* 판매자 이름"
                  value={Seller}
                  onChange={(e) => setSeller(e.target.value)}/>
              </td>
            </tr>
          </table>
          <table>
            <tr>
              <td>
                <input
                  className="Auth_Store_name"
                  type="text"
                  placeholder="* 매장 이름"
                  value={Store}
                  onChange={(e) => setStore(e.target.value)}/>
              </td>
            </tr>
          </table>
          <table>
            <tr>
              <td>
                <input
                  className="Auth_Busi_Num"
                  type="text"
                  placeholder="* 사업자 번호"
                  value={Busi}
                  onChange={(e) => setBusi(e.target.value)}
                  onKeyDown={(e) => { if (e.key === 'Enter') { handleStoreAuth(); } }}/>
              </td>
            </tr>
          </table>
          <input 
            className="btn_Store_Auth"
            disabled={!isFormValid}
            type="button" 
            value="인증 신청"
            onClick={handleStoreAuth}/>
          {!isFormValid && <p className="StoreAuth_Ck_Error_Message">* 필수 입력 항목을 작성해주세요</p>}
        </div>
      </div>   
    ) : (        
      // 판매자 인증 O
      <div className="layout_StoreAuth">
        <div className="box_Store_Menu">
          <button
            className="btn_StoreMenu_StoreAuth"
            onClick={() => (navigate("/seller/apply"))}> 
            매장 인증
          </button>
          {sellerMenu.map((menu, index) => (
            <div 
              key={index} 
              className="store_Menu">
              <div 
                className="store_Menu_Title" 
                onClick={() => toggleMenu(index)}>
                <div> {menu.title} </div>
                <div> {openMenu == index ? "∧" : "∨"} </div>
              </div>
              {openMenu == index && (
                <div className="submenu_list">
                  {menu.submenu.map((sub, subIndex) => (
                    <div 
                      key={subIndex} 
                      className="store_Submenu"
                      onClick={() => handleStoreDetail(sub.id)}>
                      {sub.name}
                    </div>
                  ))}
                </div>
              )}
            </div>
          ))}
        </div>
        <div className="box_Store_Content">
          
        </div>
      </div>
    )}
    </>
  );
}

export default StoreAuthComponent;