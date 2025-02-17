import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './StoreModiComponent.css'; 

const StoreModiComponent = () => {  
  const navigate = useNavigate();
  const [store, setStore] = useState("");
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);

  const sellerName = searchParams.get('sellerName');
  const busiNum = searchParams.get('busiNum');

  let isStoreValid = store;

  // 입력 항목 유효성 검사
  const handleStoreAuth = async () => 
  {  
    try 
    {      
      const validChar = /^[a-zA-Z가-힣0-9]+$/;

      if( 0 < store.length  && 11 > store.length  && validChar.test(store)  ) isStoreValid = true;

      const response = await axios.post("http://localhost:9000/store/modify", 
        {"name":store, "sellerName":sellerName, "busiNum":busiNum}, {withCredentials : true, headers: { "Authorization": `Bearer ${localStorage.getItem("token")}` }});
      
      if(!isStoreValid)
      {
        alert("매장 이름 입력이 잘못되었습니다. (1 ~ 10자로 입력 가능, 특수문자 입력 불가)");
      }
      else
      {
        if (response.status == 200) 
        {
          alert("판매자 정보 수정이 완료되었습니다."); 
          navigate(-1);
        } 
        else 
        {
          console.error("판매자 정보 수정 실패", response.data);
        }
      }
    }  
    catch (error) {
        console.error("판매자 정보 수정 오류:", error);
        if (error.response) {
          console.error("서버 응답 오류:", error.response.data);
        }
      }
  };
 
  return  (
    <>
      <div className="layout_Store_Modi">
        <div className="box_Store_Modi">
          <table>
            <tr>
              <td>
                <input
                  className="Modi_Seller_Name"
                  type="text"
                  placeholder="* 판매자 이름"
                  defaultValue={sellerName}
                  disabled/>
              </td>
            </tr>
          </table>
          <table>
            <tr>
              <td>
                <input
                  className="Modi_Store_Name"
                  type="text"
                  placeholder="* 매장 이름"
                  value={store}
                  onChange={(e) => setStore(e.target.value)}/>
              </td>
            </tr>
          </table>
            <table>
              <tr>
                <td>
                <input
                  className="Modi_Busi_Num"
                  type="text"
                  placeholder="* 사업자 번호"
                  defaultValue={busiNum}
                  disabled/>
              </td>
            </tr>
          </table>
          <div className="box_btn_Store_Modi">
            <input 
              className="btn_Store_Modi"
              type="button" 
              value="수정"
              disabled={!isStoreValid}
              onClick={handleStoreAuth}/>
            <input 
              className="btn_Store_Modi"
              type="button" 
              value="취소"
              onClick={() => navigate(-1)}/>
          </div>
          {!store && <p className="StoreModi_Ck_Error_Message">* 매장 이름을 입력해주세요</p>} 
        </div>
      </div>
    </>
  );
};

export default StoreModiComponent;
