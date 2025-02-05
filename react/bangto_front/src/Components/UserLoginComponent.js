import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './LayoutComponent.css';
import './UserLoginComponent.css'; 
import { useNavigate } from 'react-router-dom';

const UserLoginComponent = (props) => 
{  
  const [userEmail, setUserEmail] = useState("");
  const [pw, setPw] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const code = new URLSearchParams(window.location.search).get("code");
  const navigate = useNavigate();

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
  
  const kakaoLogin = async () => {
    const getToken = await axios.post(
      "https://kauth.kakao.com/oauth/token",
      {
        grant_type: "authorization_code",
        client_id: process.env.REACT_APP_API_KEY,
        redirect_uri: process.env.REACT_APP_LOGIN_REDIRECT_URI,
        code: code
      },
      {
        headers: {
          "Content-type": "application/x-www-form-urlencoded;charset=utf-8"
        }
      }
    );
    const accessToken = getToken.data.access_token;
    const getInfo = await axios.get(
      "https://kapi.kakao.com/v2/user/me",
      {
        headers: {
          "Authorization": `Bearer ${accessToken}`,
          "Content-Type": "application/x-www-form-urlencoded;charset=utf-8"
        }
      }
    )
    const info = getInfo;
    await props.setUserName(info.data.kakao_account.profile.nickname);
    await localStorage.setItem("kakaoAccessToken", accessToken);
    navigate("/");
  }

  useEffect(() => {
    if(code !== null) {
      kakaoLogin();
    }
  }, [code]);

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
            <a href={`https://kauth.kakao.com/oauth/authorize?client_id=${process.env.REACT_APP_API_KEY}&redirect_uri=${process.env.REACT_APP_LOGIN_REDIRECT_URI}&response_type=code`}>
            <img
              className="btn_Social"
              src={`/images/02_icon/kakao_login_medium_narrow.png`} 
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

export default UserLoginComponent;