import './MainComponent.css';
import './UserLoginComponent.css'; 

const UserMainComponent = () => 
{
  return (
    <div className="layout_Content">
        <div className="layout_Top"/>
        <div className="box_login">
            <table className="table_login">
              <tr>
                <td>
                  <input
                    className="input_email"
                    type="email"
                    placeholder="이메일"/>
                </td>
              </tr>
            </table>
            <table className="table_login">
              <tr>
                <td>
                  <input
                    className="input_pw"
                    type="password"
                    placeholder="비밀번호"/>
                </td>
              </tr>
            </table>
            <input 
              className="btn_login"
              type="button" 
              value="로그인"/>
            <a href="https://kauth.kakao.com/oauth/authorize?client_id=0fd256147c0008703a4c880f0fb3b6a4&redirect_uri=http://localhost:3000&response_type=code">
            <img
              className="btn_social"
              src={`${process.env.PUBLIC_URL}/images/02_icon/kakao_login_medium_narrow.png`} 
              alt="카카오 로그인" />
          </a>
          <div
            className="btn_join"
            onClick={() => (window.location.href = '/join')}>
            회원가입
          </div>
        </div>
        <div className="layout_Footer"/>
      </div>
      
    );
}

export default UserMainComponent;