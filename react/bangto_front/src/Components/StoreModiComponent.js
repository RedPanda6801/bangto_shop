import React from 'react';
import { useLocation } from 'react-router-dom';
import './StoreModiComponent.css'; 

const StoreModiComponent = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);

  const sellerName = searchParams.get('sellerName');
  const storeName = searchParams.get('storeName');
  const busiNum = searchParams.get('busiNum');

  return  (
    <>
      <div className="layout_Store_Modi">
        <div className="box_Store_Modi">
          <table>
            <tr>
              <td>
                <input
                  className="Auth_Store_name"
                  type="text"
                  placeholder="* 매장 이름"
                  defaultValue={storeName}/>
              </td>
            </tr>
          </table>
            <table>
              <tr>
                <td>
                <input
                  className="Auth_Busi_Num"
                  type="text"
                  placeholder="* 사업자 번호"
                  defaultValue={busiNum}
                  disabled/>
              </td>
            </tr>
          </table>
          <input 
            className="btn_Store_Auth"
            type="button" 
            value="수정"/>
          { <p className="StoreModi_Ck_Error_Message">* 필수 입력 항목을 작성해주세요</p>} 
        </div>
      </div>
    </>
  );
};

export default StoreModiComponent;
