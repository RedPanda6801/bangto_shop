import React, { useState, useEffect } from 'react';
import axios from "axios";
import { useNavigate } from 'react-router-dom';
import '../StoreComponent.css'; 
import { resContent } from '../UtilComponent/ResponseData';
import SellerApplyComponent from './SellerApplyComponent';
import StoreListComponent from './StoreListComponent';

const StoreAuthComponent = (props) => 
{ 
  // 판매자 인증 X
  const navigate = useNavigate();
  const [store, setStore] = useState("");
  const [busi, setBusi] = useState("");
  const [storelist, setStorelist] = useState([]);
  const [page, setPage] = useState(1);
  const [selectedMenu, setSelectedMenu] = useState("매장 인증");
  const [selectedSubmenu, setSelectedSubmenu] = useState(null);
  const [sellerName, setSellerName] = useState("");
  const [storeInfo, setStoreInfo] = useState([]);
  const [openMenu, setOpenMenu] = useState("");
  const [itemlist, setItemlist] = useState([]);
  const toggleMenu = (index) =>
  {
    setOpenMenu(openMenu == index ? null : index);
  }

  useEffect(()=>{
    axios.get("http://localhost:9000/seller/get-info", {
           headers: {"Authorization": `Bearer ${localStorage.getItem("token")}`}
         }).then((res)=> {
          setSellerName(resContent(res).name);
          setSelectedMenu("매장 목록");
        axios.get("http://localhost:9000/store/get-list", {
          headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`,
          }
        }).then((res)=> {
          // 유저가 등록한 스토어 정보
          //console.log("스토어 리스트");
          //console.log(resContent(res));
          setStorelist(resContent(res)); 
          setStoreInfo(resContent(res)[0]);
        })
    }).catch((err)=>{
      if(err.status == 403) alert("판매자 인증이 필요합니다.");
      else alert("매장 인증 에러");
      console.log(err);
    })
  },[])
 
  const handleStoreDetail = async(storeId) =>
  {
    const storeResponse = await axios.get(`http://localhost:9000/store/get-detail/${storeId}`,{
      headers: {
        "Authorization": `Bearer ${localStorage.getItem("token")}`,
      }
    })
    setStoreInfo(resContent(storeResponse));
  }

  // 판매자 인증 O
  const sellerMenu = [
    {title: "매장 목록", submenu: storelist},
    {title: "QNA", submenu: storelist},
    {title: "주문 목록", submenu: storelist},
    {title: "공동 구매 관리", submenu: storelist}
  ];

  // 서브메뉴 선택에 따라 box 페이지 변경
  const renderContentForMenu = (menu, submenu) => {
    if("매장 인증" == menu) {
      return <SellerApplyComponent></SellerApplyComponent>
    }
    else if("매장 목록" == menu)
    {
      return <StoreListComponent sellerName={sellerName} storeInfo={storeInfo} ></StoreListComponent>
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
                  <td
                    onClick={() => navigate("/qna/detail")}>2222</td>
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
    else
    {
      return <div>선택 메뉴 없음</div>
    }
  };

  return (
    <>
      <div className="layout_Store">
        <div className="box_Store_Menu">
          <button
            className="btn_StoreMenu_StoreAuth"
            onClick={() => setSelectedMenu("매장 인증")}
            disabled={sellerName}>
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
                {storelist && Array.isArray(storelist) ? 
                  menu.submenu.map((sub) => (
                    <div 
                      className="store_Submenu"
                      style={{color: selectedSubmenu === sub.id ? '#FF3636' : 'black'}}
                      onClick={() => {
                        handleStoreDetail(sub.id);
                        setSelectedMenu(menu.title);
                        setSelectedSubmenu(sub.id);}}>
                      {sub.name}
                    </div>
                  )) : <div>매장 없음</div>
                }
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