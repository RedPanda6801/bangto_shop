import './MainComponent.css';
import './UserLoginComponent.css'; 

const UserMainComponent = () => 
{
  return (
    <div className="layout_Content">
        <div className="layout_Top"/>
        <div className="box_login">
            <table>
              <tr>
                <td>
                  <input
                    type="email"
                    placeholder="이메일"/>
                </td>
              </tr>
            </table>
            <table>
              <tr>
                <td>
                  <input
                    type="password"
                    placeholder="비밀번호"/>
                </td>
              </tr>
            </table>
            <input 
              type="button" 
              value="로그인"/>
            <input 
              type="button" 
              value="소셜로그인"/>
          <div
            onClick={() => (window.location.href = '/join')}>
            회원가입
          </div>
        </div>
        <div className="layout_Footer"/>
      </div>
      
    );
}

export default UserMainComponent;