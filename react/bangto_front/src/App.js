import { BrowserRouter, Routes, Route } from 'react-router-dom';
import UserMainComponent from './Components/UserMainComponent';
import UserLoginComponent from './Components/UserLoginComponent';
import './Components/LayoutComponent.css';

function App() {
  return (  
    <div className="layout_Main">
      <div className="layout_Header">
        <button className="header_Logo" onClick={() => (window.location.href = '/')}>
          <img src={`${process.env.PUBLIC_URL}/images/01_logo/logo_01.jpg`} alt="로고 이미지" />
        </button>
        <div className="header_Menu">카테고리1</div>
        <div className="header_Menu">카테고리2</div>
        <div className="header_Menu">카테고리3</div>
        <div className="header_Menu">카테고리4</div>
        <div className="header_Menu">카테고리5</div>
        <div className="header_Menu">공동구매</div>
          {/* {userName ? (
            <div>
              <div>{userName}님 환영합니다.</div>
              <button onClick={logout}>로그아웃</button>
            </div>
          ) : ( */}
              <div
                className="header_User header_User_First"
                onClick={() => (window.location.href = '/login')}>
                로그인
              </div>
              <div 
                className="header_User" 
                onClick={() => (window.location.href = '/join')}>
                회원가입
              </div>
          {/* )} */}
      </div>
      <Routes>
        <Route path="/" element={<UserMainComponent />} />
        <Route path="/login" element={<UserLoginComponent />} />
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
