import { useEffect, useState } from "react";
import axios from "axios";
import { resPage , resContent } from '../ResponseData';
import { useNavigate } from 'react-router-dom';
import TimeStamp from "../UtilComponent/DateFormat";
import PagenationComponent from "../UtilComponent/PagenationComponent";

const ManagerApplyComponent = () => {
    const [applyPage, setApplyPage] = useState(1);
    const [applyTotalPage, setApplyTotalPage] = useState(1);
    const [applyList, setApplyList] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {handleApplyList()},[applyPage])

    const handleApplyList = () => {
        axios.get(`http://localhost:9000/manager/apply/get-list/${applyPage}`, {
            withCredentials : true,
            headers: { "Authorization": `Bearer ${localStorage.getItem("token")}`,
        }}).then((res)=> {
            setApplyList(resContent(res));
            setApplyPage(applyPage);
            setApplyTotalPage(resPage(res) ? resPage(res).totalPages : 1);
        }).catch((error) => {
            if(error.status === 401){
                alert("토큰 만료 오류 : 다시 로그인하세요.");
                navigate("/");
            }
            console.error("회원 정보 가져오기 오류:", error);
        })
    }


    return <div className="box_Manager_StoreAuth_Detail">
                <div className="box_Manager_StoreAuth_Info">
                    <table className="table_Manager_StoreAuth_Info">
                        <tr>
                            <th>판매자 이름</th>
                            <th>지원 날짜</th>
                            <th>승인 여부</th>
                            <th>승인</th>
                        </tr>
                        {applyList && Array.isArray(applyList) ? 
                            applyList.map(apply => (
                                <tr>
                                    <td>{apply.userName}</td>
                                    <td>{TimeStamp(apply.applyDate)}</td>
                                    <td>{apply.auth}</td>
                                    <td><button>승인</button></td>
                                </tr>
                            )) : <div>매장 없음</div>
                        }
                    </table>
                </div>
                <div>
                <PagenationComponent page={applyPage} setPage={setApplyPage} totalPage={applyTotalPage}/>
            </div>
            </div>
}

export default ManagerApplyComponent;