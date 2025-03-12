import { useEffect, useState } from 'react';
import '../StoreComponent.css'; 
import { useNavigate } from 'react-router-dom';
import { resContent } from '../UtilComponent/ResponseData';
import axios from 'axios';

const StoreListComponent = (props) => {
    const navigate = useNavigate();
    const [storeInfo, setStoreInfo] = useState({});
    const [itemPage, setItmePage] = useState(1);
    const [itemlist, setItemlist] = useState([]);
    const [status, setStatus] = useState("조회");
    const SERVER_PORT = process.env.REACT_APP_BACKEND_SERVER_PORT;

    useEffect(()=> {
        handleItemList();
    },[props.storeInfo, status])

    const handleItemDelete = (id) => {
        if(window.confirm("물품을 삭제하시겠습니까?")) {
        //물품 삭제 추가 필요
        alert(id);
        }
    }

    const handleItemList = async () => {
        try{
            setStoreInfo(props.storeInfo);
            if(storeInfo && storeInfo.id){
                const itemResponse = await axios.get(`${SERVER_PORT}/item/get-itemlist/${storeInfo.id}/${itemPage}`,{
                    headers: {
                      "Authorization": `Bearer ${localStorage.getItem("token")}`,
                    }
                  })
                setItemlist(resContent(itemResponse));
            }
        }catch(error){
            console.log(error);
        }
    }

    return (
        <>
            <div className="box_Store_Detail">
                <div className="box_Store_Info">
                <table>
                    <tr>
                        <th>판매자 이름</th>
                        <th>매장 이름</th>
                        <th>사업자 번호</th>
                    </tr>
                    <tr>
                        <td>{props.sellerName}</td>
                        <td>{storeInfo.name}</td>
                        <td>{storeInfo.busiNum}</td>
                    </tr>
                </table>
                <button
                    onClick={() => setStatus("수정") }>
                    매장 정보 수정
                </button>
                </div>
                <button 
                    className="btn_Store_Add"
                    onClick={() => setStatus("등록")}>
                    판매 물품 등록
                </button>
                <div className="box_Item_Info">
                {itemlist && Array.isArray(itemlist)  && itemlist.length !== 0?
                    <table>
                        <tr>
                        <th>상품명</th>
                        <th>상품 설명</th>
                        <th>상품 가격</th>
                        <th>공동구매 등록</th>
                        <th>상품 수정</th>
                        <th>상품 삭제</th>
                        </tr>
                        { itemlist.map((item) => (
                            <tr>
                            <td>{item.title}</td>
                            <td>{item.content}</td>
                            <td>{item.price}</td>
                            <td>
                            <button
                                onClick={() => navigate(`/item/add_group_item?storeName=${storeInfo.name}&itemTitle=${item.title}&itemId=${item.id}`)}>
                                공동구매 등록
                            </button>
                            </td>
                            <td>
                            <button
                                onClick={() => navigate(`/item/modi?storeName=${storeInfo.name}&itemId=${item.id}`)}>
                                수정
                            </button>
                            </td>
                            <td>
                            <button
                                onClick={() => handleItemDelete(item.id)}>
                                삭제
                            </button>
                            </td>
                        </tr>
                        ))}
                    </table>
                : <div style={{textAlign : 'center'}}>매장을 선택해주세요</div>}
                </div>
            </div>
        </>
    );
}

export default StoreListComponent;