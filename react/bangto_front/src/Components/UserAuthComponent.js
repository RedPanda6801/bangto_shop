import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './UserAuthComponent.css'; 
import { useNavigate } from 'react-router-dom';

const UserAuthComponent = (props) => 
{  
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const code = new URLSearchParams(window.location.search).get("code");
  const navigate = useNavigate();

  const handleAuth = async () => 
  {
    try 
    {
      const response = await axios.post("http://localhost:9000/login", 
        {"email":email, "pw":password}, {withCredentials : true});
      if (response.status == 200) 
      {
        console.log("로그인 성공");
        console.log(response.data.token);
        localStorage.setItem("token",response.data.token);
        props.setToken(response.data.token);
        navigate("/");
      } 
      else 
      {
        console.error("로그인 실패:");
      }
    } 
    catch (error) 
    {
      console.error("로그인 오류:", error);
    }
  };
  
  const kakaoLogin = async () => {
    try {

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
      const email = info.data.kakao_account.email;
      const name = info.data.kakao_account.profile.nickname
      const SnsSignCheck = await axios.get(
        `http://localhost:9000/user/get-sns-signed/${email}`
      )
      const isSnsSigned = SnsSignCheck.data;
      if(!isSnsSigned) {
        const signResponse = await axios.post("http://localhost:9000/sign", 
          {email, name, "snsAuth": true}, {withCredentials : true});
        if (signResponse.status == 200) 
        {
          console.log("회원가입 성공");
        } 
        else 
        {
          console.error("회원가입 실패");
        }
      }
      const loginResponse = await axios.post("http://localhost:9000/login", 
        {"email":email, "snsAuth":true}, {withCredentials : true});
      if (loginResponse.status == 200) 
      {
        console.log("로그인 성공");
        console.log(loginResponse.data.token);
        localStorage.setItem("token",loginResponse.data.token);
        props.setToken(loginResponse.data.token);
        //alert("카카오 로그인 성공");
        navigate("/");
      } 
      else 
      {
        console.error("로그인 실패:");
      }
      //await props.setUserName(info.data.kakao_account.profile.nickname);
      await localStorage.setItem("kakaoAccessToken", accessToken);
      navigate("/");
    }
    catch (error) {
      console.error("로그인 오류:", error);
    }
  }

  useEffect(() => {
    if(code !== null) {
      kakaoLogin();
    }
  }, [code]);

  return (
    <div className="layout_Auth">
      <div className="box_Auth">
        <table>
          <tr>
            <td>
              <input
                className="Auth_Email"
                type="email"
                placeholder="이메일"
                value={email}
                onChange={(e) => setEmail(e.target.value)}/>
            </td>
          </tr>
        </table>
        <table>
          <tr>
            <td>
              <input
                className="Auth_Pw"
                type="password"
                placeholder="비밀번호"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                onKeyDown={(e) => { if (e.key === 'Enter') { handleAuth(); } }}/>
            </td>
          </tr>
        </table>
        {errorMessage && <p className="error_Message">{errorMessage}</p>}
        <input 
          className="btn_Auth"
          type="button" 
          value="로그인"
          onClick={handleAuth}/>
        <a href={`https://kauth.kakao.com/oauth/authorize?client_id=${process.env.REACT_APP_API_KEY}&redirect_uri=${process.env.REACT_APP_LOGIN_REDIRECT_URI}&response_type=code`}>
          <img
            className="btn_Social"
            src={`/images/02_icon/kakao_login_medium_narrow.png`} 
            alt="카카오 로그인" />
        </a>
        <div
          className="btn_Join"
          onClick={() => (navigate("/sign"))}>
          회원가입
        </div>
      </div>
    </div>      
  );
}

export default UserAuthComponent;