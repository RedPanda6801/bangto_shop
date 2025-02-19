import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ManagerComponent from './Components/ManagerComponent';
import UserMainComponent from './Components/UserMainComponent';
import UserAuthComponent from './Components/UserAuthComponent';
import UserSignComponent from './Components/UserSignComponent';
import StoreComponent from './Components/StoreComponent';
import StoreModiComponent from './Components/StoreModiComponent';
import StoreItemRegisterComponent from './Components/StoreItemRegisterComponent';
import StoreGroupItemRegisterComponent from './Components/StoreGroupItemRegisterComponent';
import UserCartComponent from './Components/UserCartComponent';
import './Components/LayoutComponent.css';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

function App() {
  const [userName, setUserName] = useState("");
  const [userEmail, setUserEmail] = useState("");
  const navigate = useNavigate();

  const rootEmail = process.env.REACT_APP_ROOT_EMAIL;

  useEffect(()=>{
    axios.get("http://localhost:9000/user/get-info", {
           headers: {
             "Authorization": `Bearer ${localStorage.getItem("token")}`,
           }
         }).then((res)=> {
        setUserEmail(res.data.email);
        setUserName(res.data.name);
    }).catch((err)=>{
        console.log(err);
    })
  },[localStorage.getItem("token")])

  const logout = () => {
    //카카오톡 소셜 로그인 사용자일 경우
    if(localStorage.getItem("kakaoAccessToken") !== null) {
      // 1. 기본 로그아웃
      // axios.post(
      //   "https://kapi.kakao.com/v1/user/logout", {},
      //   {
      //     headers: {
      //       "Authorization": `Bearer ${localStorage.getItem("kakaoAccessToken")}`,
      //     }
      //   }
      // );
      // 2. 카카오 계정과 함께 로그아웃(무슨 차이지?)
      // axios.get(
      //   "https://kauth.kakao.com/oauth/logout",
      //   {
      //     params: {
      //       client_id: process.env.REACT_APP_API_KEY,
      //       logout_redirect_uri: process.env.REACT_APP_LOGOUT_REDIRECT_URI
      //     }
      //   }
      // );
      // 3. 연결 끊기(회원 탈퇴인 듯?)
      axios.post(
        "https://kapi.kakao.com/v1/user/unlink", {},
        {
          headers: {
            "Authorization": `Bearer ${localStorage.getItem("kakaoAccessToken")}`,
          }
        }
      )
      localStorage.removeItem("kakaoAccessToken");
    }
    
    localStorage.removeItem("token");
    setUserEmail("");
    setUserName("");    
    navigate("/");
  }

  return (  
    <div className="layout_Main">
      <div className="layout_Header">
        <button className="header_Logo" onClick={() => (navigate("/"))}>
          <img src={`/images/01_logo/logo_01.jpg`} alt="로고 이미지" />
        </button>
        {userEmail == rootEmail ? (
            <>
              <div className="header_Manager">관리자 메뉴</div>
            </>
          ):(
          <>
            <div className="header_Menu">카테고리1</div>
            <div className="header_Menu">카테고리2</div>
            <div className="header_Menu">카테고리3</div>
            <div className="header_Menu">카테고리4</div>
            <div className="header_Menu">카테고리5</div>
            <div className="header_Menu">공동구매</div>
          </>)}
        {userName !== "" ? (
           userEmail === rootEmail ? (
            <div>
              <div className="header_Manager_Text">관리자님 환영합니다.</div>
              <div 
                className="header_User header_User_Menu"
                onClick={() => (navigate("/manager"))}>
                관리자 페이지
              </div>
              <div 
                className="header_User header_User_Menu"
                onClick={logout}>
                로그아웃
              </div>
            </div>
          ) : (
            <>
              <div>
                <div
                  className="header_User_Text">
                  {userName}님 환영합니다.
                </div>
                <div 
                  className="header_User header_User_Menu"
                  onClick={logout}>
                  로그아웃
                </div>
              </div>
              <div>              
                <div 
                  className="header_User header_User_Menu"
                  onClick={() => (navigate("/seller/apply"))}>
                  판매자 페이지
                </div>
                <div 
                  className="header_User header_User_Menu"
                  onClick={() => (navigate("/user/cart"))}>
                  카트
                </div>
              </div>
            </>)
          ) : (
              <>
                <div
                  className="header_User header_User_First"
                  onClick={() => (navigate("/login"))}>
                  로그인
                </div>
                <div 
                  className="header_User" 
                onClick={() => (navigate("/sign"))}>
                  회원가입
                </div>
              </>
        )}
      </div>
      <Routes>
        <Route path="/manager" element={<ManagerComponent setUserName={setUserName} />} />
        <Route path="/" element={<UserMainComponent />} />
        <Route path="/login" element={<UserAuthComponent setUserName={setUserName} />} />
        <Route path="/sign" element={<UserSignComponent />} />
        <Route path="/seller/apply" element={<StoreComponent setUserName={setUserName} />} />
        <Route path="/seller/storemodi" element={<StoreModiComponent setUserName={setUserName} />} />
        <Route path="/item/add_item" element={<StoreItemRegisterComponent setUserName={setUserName} />} />
        <Route path="/item/add_group_item" element={<StoreGroupItemRegisterComponent setUserName={setUserName} />} />
        <Route path="/user/cart" element={<UserCartComponent setUserName={setUserName} />} />
      </Routes>
      
        <div className="layout_Footer_Buttons">
          <div className="footer_Btn footer_Btn_First">회사소개</div>
          <div className="footer_Btn">개인정보처리방침</div>
          <div className="footer_Btn">이용약관</div>
          <div className="footer_Btn">전자거래분쟁조정위원회</div>
          <div className="footer_Btn">불편사항신고센터</div>
        </div>
        
      <div className="layout_Footer_Info">
        <table className="footer_Info">
          <tr><td>(주)방토샵</td><td>구매안전서비스(채무자2지급보증)</td></tr>
          <tr><td>대표 : 이방토<br/>주소 : 서울특별시 강남구 언주로 508 14층<br/>사업자등록번호:000-00-00000 | 통신판매업신고:0000-서울강남구-0000<br/>상담센터:0000-0000 | 팩스:02-0000-0000<br/>호스팅서비스:(주)방토샵</td><td>당사는 하나은행과 재무지급보증 계약을 체결하여 5만원 이상 현금 등으로 <br/>결제한 금액에 대해 안전거래를 보장하고 있습니다.<br/><br/>COPYRIGHT BANGTOSHOP CO.,LTD.ALL RIGHT RESERVED.</td></tr>
        </table>
      </div>
    </div>
  );
}

export default function AppWithRouter() {
  return (
    <BrowserRouter>
      <App />
    </BrowserRouter>
  );
}
