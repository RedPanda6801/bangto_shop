import axios from "axios";
import { useState } from "react";

const ManagerGroupComponent = () => {
    const [groupItem, setGroupItem] = useState("");

    const searchGroupDate = async() => {
        try 
        {
            //이거 해야됨     
            const response = await axios.get(`http://localhost:9000/pay/get-info`,{

            }, {
                withCredentials : true,
                headers: { "Authorization": `Bearer ${localStorage.getItem("token")}`}
            });

            if (response.status == 200) 
            {
                setGroupItem(response);
            } 
            else 
            {
                console.error("공동 구매 기간 가져오기 실패:");
            }
            
        }
        catch (error) 
        {
            console.error("공동 구매 기간 가져오기 오류:", error);
        }
    }

    return <div className="box_Manager_Group_Item_Detail">
                    <div className="box_Manager_Group_Item_Set">
                        <input
                            className="manager_Group_Item_Date"
                            type="date"/>
                        <input
                            className="manager_Group_Item_Date"
                            type="date"/>
                        <button>
                            등록
                        </button>
                    </div>
                    <div>
                        <table className="table_Manager_Group_Item_Date">
                            <tr>
                                <td>공동구매 기간 : </td>
                                <td>2025.02.19</td>
                                <td>~</td>
                                <td>2025.02.26</td>
                            </tr>
                        </table>
                    </div>
                    <div className="box_Manager_Group_Item_Info">
                        <table className="table_Manager_Group_Item_Info">
                        <tr>
                            <th>판매자 이름</th>
                            <th>상품명</th>
                            <th>상품 설명</th>
                            <th>상품 가격</th>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>2</td>
                            <td>3</td>
                            <td>4</td>
                        </tr>
                            {/* {storelist.map((store) => (
                                <tr>
                                    <td>{store.sellerName}</td>
                                    <td>{store.name}</td>
                                    <td>{store.busiNum}</td>
                                    <td>
                                        <button>
                                            승인
                                        </button>
                                    </td>
                                </tr>
                            ))} */}
                        </table>
                    </div>
                    <div>
                        {/* {authPagination()} */}
                    </div>
                </div>
}

export default ManagerGroupComponent;