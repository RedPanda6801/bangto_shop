import { BrowserRouter, Routes, Route, Link, useNavigate } from 'react-router-dom';
import UserMainComponent from './Components/UserMainComponent';
import UserLoginComponent from './Components/UserLoginComponent';
import './Components/MainComponent.css';

function App() {
  const navigate = useNavigate();

  return (  
    <div className="layout_Main">
      <div className="layout_Header">
        <button className="header_Logo" onClick={() => navigate('/')}>
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
                onClick={() => navigate('/join')}>
                회원가입
              </div>
          {/* )} */}
      </div>
      <Routes>
        <Route path="/" element={<UserMainComponent />} />
        <Route path="/login" element={<UserLoginComponent />} />
      </Routes>

      <div className="layout_Footer"/>
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
