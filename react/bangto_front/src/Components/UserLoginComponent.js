import React, { useState } from 'react';
import axios from 'axios';
import './LayoutComponent.css';
import './UserLoginComponent.css'; 

const UserMainComponent = () => 
{  
  const [userEmail, setUserEmail] = useState("");
  const [pw, setPw] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleLogin = async () => 
  {
    try 
    {
      const response = await axios.post("http://localhost:9000/login", 
        { userEmail, pw}, {withCredentials : true});
      if (response.status == 200) 
      {
        setErrorMessage("로그인성공");
        window.location.href = "/";
      } 
      else 
      {
        setErrorMessage("로그인 실패");
      }
    } 
    catch (error) 
    {
      console.error("로그인 오류:", error);
      setErrorMessage("서버와 연결할 수 없습니다.");
    }
  };
  
  return (
    <div className="layout_Login">
        <div className="box_Login">
            <table className="table_Login">
              <tr>
                <td>
                  <input
                    className="input_Email"
                    type="email"
                    placeholder="이메일"
                    value={userEmail}
                    onChange={(e) => setUserEmail(e.target.value)}/>
                </td>
              </tr>
            </table>
            <table className="table_Login">
              <tr>
                <td>
                  <input
                    className="input_Pw"
                    type="password"
                    placeholder="비밀번호"
                    value={pw}
                    onChange={(e) => setPw(e.target.value)}/>
                </td>
              </tr>
            </table>
            {errorMessage && <p className="error_Message">{errorMessage}</p>}
            <input 
              className="btn_Login"
              type="button" 
              value="로그인"
              onClick={handleLogin}/>
            <a href="https://kauth.kakao.com/oauth/authorize?client_id=0fd256147c0008703a4c880f0fb3b6a4&redirect_uri=http://localhost:3000&response_type=code">
            <img
              className="btn_Social"
              src={`${process.env.PUBLIC_URL}/images/02_icon/kakao_login_medium_narrow.png`} 
              alt="카카오 로그인" />
          </a>
          <div
            className="btn_Join"
            onClick={() => (window.location.href = '/join')}>
            회원가입
          </div>
        </div>
      </div>      
    );
}

export default UserMainComponent;