import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import './StoreGroupItemRegisterComponent.css'; 

const StoreGroupItemRegisterComponent = () => {   
  const navigate = useNavigate();
  const [optionInfo, setOptionInfo] = useState("");
  const [addPrice, setAddPrice] = useState(0);
  const [options, setOptions] = useState([]);

  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);  
  const itemId = searchParams.get("itemId");
  const storeName = searchParams.get("storeName");
  const itemTitle = searchParams.get("itemTitle");

  const handelAddGroupItem = async () => 
  {
    
  };

  return  (
    <>
      <div className="layout_Store_Group_Item_Regi">
        <div className="box_Store_Group_Item_Add">
          <input 
            className="Group_Item_Add_Store" 
            type="text" 
            defaultValue={storeName}
            disabled/>
          <div className="Item_Add_Title">공동 구매 정보</div>
          <table className="Group_Item_Add_Table">
            <tr><td>물품 이름</td>
              <td>
                <input 
                  type="text" 
                  defaultValue={itemTitle}
                  disabled/>
              </td>
            </tr>
          </table>
          <table className="Group_Item_Add_Table">
            <tr><td>구매 기간</td>
              <td>
                <input 
                  type="text"
                  disabled/>
              </td>
            </tr>
          </table>
          <table className="Group_Item_Add_Table">
            <tr><td>1인 제한 수량</td>
              <td>
                <input 
                  type="number"
                  className="Item_Limit_Amount_Per"/>
              </td>
            </tr>
          </table>
          <table className="Group_Item_Add_Table">
            <tr><td>최대 수량</td>
              <td>
                <input 
                  type="number"
                  className="Item_Limit_Amountt"/>
              </td>
            </tr>
          </table>
          <div className="box_Btn_Item_Add">
            <input 
              className="btn_Group_Item_Add"
              type="button" 
              value="공동 구매 등록"
              onClick={handelAddGroupItem}/>
            <input 
              className="btn_Group_Item_Add"
              type="button" 
              value="취소"
              onClick={() => navigate(-1)}/>
          </div>
        </div>
      </div>
    </>
  );
};

export default StoreGroupItemRegisterComponent;
