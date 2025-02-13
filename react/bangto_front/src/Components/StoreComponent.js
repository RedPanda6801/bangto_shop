import React, { useState, useEffect } from 'react';
import axios from "axios";
import { useNavigate } from 'react-router-dom';
import './StoreComponent.css'; 

const StoreAuthComponent = (props) => 
{ 
  // 판매자 인증 X
  const navigate = useNavigate();
  const [store, setStore] = useState("");
  const [busi, setBusi] = useState("");
  const [storelist, setStorelist] = useState([]);
  const [isSeller, setIsSeller] = useState(false);
  const [page, setPage] = useState(1);
  const [selectedMenu, setSelectedMenu] = useState("매장 인증");
  const [selectedSubmenu, setSelectedSubmenu] = useState(null);
  const [sellerName, setSellerName] = useState("");
  const [itemlist, setItemlist] = useState([]);
  const [storeInfo, setStoreInfo] = useState([]);
  const [openMenu, setOpenMenu] = useState("");

  const toggleMenu = (index) =>
  {
    setOpenMenu(openMenu == index ? null : index);
  }

  useEffect(()=>{
    axios.get("http://localhost:9000/seller/get-info", {
           headers: {
             "Authorization": `Bearer ${localStorage.getItem("token")}`,
           }
         }).then((res)=> {
          // 유저 정보
          console.log("유저 정보");
          console.log(res.data);
          setSellerName(res.data.name);
        axios.get("http://localhost:9000/store/get-list", {
          headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`,
          }
        }).then((res)=> {
          // 유저가 등록한 스토어 정보
          console.log("스토어 리스트");
          console.log(res.data);
          setStorelist(res.data); 
          setIsSeller(true); 
        })
    }).catch((err)=>{
        console.log(err);
        setIsSeller(false);
    })
  },[])

  // 필수 입력 항목 확인
  const isFormValid = store && busi;

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
    if( 0 < store.length  && 11 > store.length  && validChar.test(store)  ) isStoreValid = true;
    if( busiChar.test(busi) )                                               isBusiValid = true;

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
    // 스토어 정보
    console.log("클릭한 스토어 정보");
    console.log(storeResponse.data);
    setStoreInfo(storeResponse.data)

    const itemResponse = await axios.get(`http://localhost:9000/item/get-itemlist/${storeId}/${page}`,{
      headers: {
        "Authorization": `Bearer ${localStorage.getItem("token")}`,
      }
    })
    
      // 선택한 스토어의 상품
      console.log(storeId);
      console.log("클릭한 스토어 상품 정보");
      console.log(itemResponse.data);
      setItemlist(itemResponse.data);
  }

  // 판매자 인증 O
  const sellerMenu = 
  [
    {title: "매장 목록", submenu: storelist},
    {title: "QNA", submenu: storelist},
    {title: "주문 목록", submenu: storelist},
    {title: "공동 구매 관리", submenu: storelist}
  ];

  // 서브메뉴 선택에 따라 box 페이지 변경
  const renderContentForMenu = (menu, submenu) => {
    if("매장 인증" == menu)
    {
      return <div className="box_Storeauth_Apply">
              <table>
                <tr>
                  <td>
                    <input
                      className="Auth_Store_name"
                      type="text"
                      placeholder="* 매장 이름"
                      value={store}
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
                      value={busi}
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
            </div>;
    }
    else if("매장 목록" == menu)
    {
      return <div className="box_Store_Detail">
              <div className="box_Store_Info">
                <table>
                  <tr>
                    <th>판매자 이름</th>
                    <th>매장 이름</th>
                    <th>사업자 번호</th>
                  </tr>
                  <tr>
                    <td>{sellerName}</td>
                    <td>{storeInfo.name}</td>
                    <td>{storeInfo.busiNum}</td>
                  </tr>
                </table>
                <button
                  onClick={() => navigate(`/seller/storemodi?sellerName=${sellerName}&storeName=${storeInfo.name}&busiNum=${storeInfo.busiNum}`)}>
                  매장 정보 수정
                </button>
              </div>
              <button className="btn_Store_Add">판매 물품 등록</button>
              <div className="box_Item_Info">
                <table>
                  <tr>
                    <th>상품명</th>
                    <th>상품 설명</th>
                    <th>상품 가격</th>
                    <th>공동구매 등록</th>
                    <th>상품 삭제</th>
                  </tr>
                    {itemlist.map((item) => (
                      <tr>
                      <td>{item.title}</td>
                      <td>{item.content}</td>
                      <td>{item.price}</td>
                      <td><button>공동구매 등록</button></td>
                      <td><button>삭제</button></td>
                      </tr>
                    ))}
                </table>
              </div>
            </div>;
    }
    else if("QNA" == menu)
    {
      return <div className="box_QNA_Detail">              
              <table>
                <tr>
                  <th>상품명</th>
                  <th>질문내용</th>
                  <th>답변 상태</th>
                </tr>
                <tr>
                  <td>1111</td>
                  <td>2222</td>
                  <td>답변상태</td>
                </tr>
                <tr>
                  <td>3333</td>
                  <td>4444</td>
                  <td>답변상태</td>
                </tr>
              </table>
            </div>;
    }
    else if("주문 목록" == menu)
    {
      return <div className="box_Sold_Detail">              
              <table>
                <tr>
                  <th>상품명</th>
                  <th>상품 설명</th>
                  <th>배송 상태</th>
                </tr>
                <tr>
                  <td>1111</td>
                  <td>2222</td>
                  <td><button>배송 정보</button></td>
                </tr>
                <tr>
                  <td>3333</td>
                  <td>4444</td>
                  <td><button>배송 정보</button></td>
                </tr>
              </table>
            </div>;
    }    
    else if("공동 구매 관리" == menu)
      {
        return <div className="box_Group_Detail">              
                <table>
                  <tr>
                    <th>상품명</th>
                    <th>결제 인원</th>
                    <th>일괄 배송</th>
                  </tr>
                  <tr>
                    <td>1111</td>
                    <td>2222</td>
                    <td><button>일괄 배송</button></td>
                  </tr>
                  <tr>
                    <td>3333</td>
                    <td>4444</td>
                    <td><button>일괄 배송</button></td>
                  </tr>
                </table>
              </div>;
      }
  };

  return (
    <>
      <div className="layout_Store">
        <div className="box_Store_Menu">
          <button
            className="btn_StoreMenu_StoreAuth"
            onClick={() => setSelectedMenu("매장 인증")}>
            매장 인증
          </button>
          {sellerMenu.map((menu, index) => (
            <div 
              className="store_Menu">
              <div 
                className="store_Menu_Title" 
                onClick={() => toggleMenu(index)}>
                <div> {menu.title} </div>
                <div> {openMenu == index ? "∧" : "∨"} </div>
              </div>
              {openMenu == index && (
                <div className="submenu_list">
                {menu.submenu.map((sub) => (
                  <div 
                    className="store_Submenu"
                    style={{color: selectedSubmenu === sub.id ? '#FF3636' : 'black'}}
                    onClick={() => {
                      handleStoreDetail(sub.id);
                      setSelectedMenu(menu.title);
                      setSelectedSubmenu(sub.id);}}>
                    {sub.name}
                  </div>
                  ))}
                </div>
              )}
            </div>
          ))}
        </div>
        <div className="layout_Contents">
          {renderContentForMenu(selectedMenu, selectedSubmenu)}
        </div>
      </div>
    </>
  );
}

export default StoreAuthComponent;