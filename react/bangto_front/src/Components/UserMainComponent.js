import './MainComponent.css';

const UserMainComponent = () => 
{
    return (
                <div
                  className="box_container"
                  id="usermenu_first"
                  onClick={() => (window.location.href = '/login')}
                >로그인버튼
                </div>
    );
}

export default UserMainComponent;