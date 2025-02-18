import React, { useEffect, useState } from 'react';
import axios from "axios";
import Modal from 'react-modal';
import { useNavigate } from 'react-router-dom';
import './ManagerComponent.css';

Modal.setAppElement('#root');

const ManagerComponent = () => 
{
    const navigate = useNavigate();
    const [userPage, setUserPage] = useState(1);
    const [storePage, setStorePage] = useState(1);
    const [selectedMenu, setSelectedMenu] = useState("고객 관리");
    const [userlist, setUserlist] = useState([]);
    const [modal, setModal] = useState(false);
    const [selectedUserId, setSelectedUserId] = useState("");
    const [name, setName] = useState("");
    const [phone, setPhone] = useState("");
    const [addr, setAddr] = useState("");
    const [totalUser, setTotalUser] = useState(1);
    const [storelist, setStorelist] = useState([]);
    const [totalStore, setTotalStore] = useState(1);
    const [storeModal, setStoreModal] = useState(false);
    const [selectedStoreId, setSelectedStoreId] = useState("");
    const [storeName, setStoreName] = useState("");
    const [busiNum, setBusiNum] = useState("");
    
    useEffect(() => 
    {
        handleUserDetail();
    }, [userPage]);

    useEffect(() => 
    {
        handleStoreDetail();
    }, [storePage]);

    useEffect(() => 
    {
        calcTotalUser();
    }, [userPage]);

    useEffect(() =>
    {
        calcTotalStore();
    },[storePage]);

    const calcTotalUser = async () =>
    {
        let page = 1;
        let totalUser = 0;

        while(true)
        {
            const response = await axios.get(`http://localhost:9000/manager/user/get-list/${page}`, {
                withCredentials : true,
                headers: { "Authorization": `Bearer ${localStorage.getItem("token")}`}   
            });
                
            totalUser += response.data.length;

            if(response.data.length < 10) { break;  }
            else                          { page++; }
        }        
        setTotalUser(totalUser);
    }

    const calcTotalStore = async () =>
    {
        let page = 1;
        let totalStore = 0;

        while(true)
        {
            const response = await axios.get(`http://localhost:9000/manager/store/get-list/${page}`, {
                withCredentials : true,
                headers: { "Authorization": `Bearer ${localStorage.getItem("token")}`}   
            });
                
            totalStore += response.data.length;

            if(response.data.length < 10) { break;  }
            else                          { page++; }
        }        
        setTotalStore(totalStore);
    }

    const userPagination = () => 
    {
        const totalPage = Math.ceil(totalUser / 10);
        const pages = [];
    
        for (let i = 1; i <= totalPage; i++) 
        {
            pages.push
            (
                <button
                    className="pagination_User"
                    onClick={() => setUserPage(i)}>
                    {i}
                </button>
            );
        }
    
        return <div>{pages}</div>;
    }

    const storePagination = () => 
    {
        const totalPage = Math.ceil(totalStore / 10);
        const pages = [];
    
        for (let i = 1; i <= totalPage; i++) 
        {
            pages.push
            (
                <button
                    className="pagination_Store"
                    onClick={() => setStorePage(i)}>
                    {i}
                </button>
            );
        }
    
        return <div>{pages}</div>;
    }

    const handleUserDetail = async() =>
    {
        try
        {
            const response = await axios.get(`http://localhost:9000/manager/user/get-list/${userPage}`, {
                    withCredentials : true,
                    headers: { "Authorization": `Bearer ${localStorage.getItem("token")}`,
                }
            });

            setUserlist(response.data);     
        }
        catch(error)
        {
            console.error("회원 정보 가져오기 오류:", error);
        }    
    }

    const handleUserModi  = (user) =>
    {
        setSelectedUserId(user);
        setModal(true);
    }

    const handleUserDel = async(userId) =>
    {
        try 
        {    
            const response = await axios.post(`http://localhost:9000/user/delete/${userId}`, {}, {
                    withCredentials : true,
                    headers: { "Authorization": `Bearer ${localStorage.getItem("token")}`,
               }
            });

            if (response.status == 200) 
            {
                console.log("회원 추방 성공");
                alert("회원 추방 성공");
                navigate(0);
            } 
            else 
            {
                console.error("회원 추방 실패:");
            }
        } 
        catch (error) 
        {
            console.error("회원 추방 오류:", error);
        }
    }

    const handleUserModiModal = async(selectedUserId) =>
    {
        try 
        {     
            const response = await axios.post(`http://localhost:9000/manager/user/modify/${selectedUserId}`, 
                { name, addr, phone}, {withCredentials : true});
            if (response.status == 200) 
            {
                alert("회원 정보 수정 성공");
            } 
            else 
            {
                console.error("회원 정보 수정 실패:");
            }
            
        }
        catch (error) 
        {
            console.error("회원 정보 수정 오류:", error);
        }
        handleCloseModal();
    }

    const handleCloseModal = () =>
    {
        setModal(false);
        setSelectedUserId("");
    }

    const getUserById = (id, info) => 
    {
        let user = userlist.find(user => user.id === id);

        if (user) 
        {
            switch(info)
            {
                case "email":
                    return user.email;
                    break;
                case "name":
                    return user.name;
                    break;
                case "phone":
                    return user.phone;
                    break;
                case "addr":
                    return user.addr;
                    break;
                default:
                    return "정보가 존재하지 않습니다.";
                    break;
            }
        } 
    };

    const getStoreById = (id, info) => 
    {
        let store = storelist.find(store => store.id === id);

        if (store) 
        {
            switch(info)
            {
                case "storeName":
                    return store.storeName;
                    break;
                case "name":
                    return store.name;
                    break;
                case "busiNum":
                    return store.busiNum;
                    break;
                default:
                    return "정보가 존재하지 않습니다.";
                    break;
            }
        } 
    };

    const handleStoreDetail = async() =>
    {
        const response = await axios.get(`http://localhost:9000/manager/store/get-list/${storePage}`, {
                withCredentials : true,
                headers: { "Authorization": `Bearer ${localStorage.getItem("token")}`,
            }
        });
        setStorelist(response.data);         
    }
    
    const handleStoreModi = (store) => 
    {
        setSelectedStoreId(store);        
        setBusiNum(getStoreById(store, "busiNum"));
        setStoreModal(true);
    }

    const handleStoreUpdate = async (selectedStoreId) => 
    {
        // try {
        //     const response = await axios.post(`http://localhost:9000/manager/store/modify/${selectedStoreId}`, 
        //         { "id": selectedStoreId, "name":storeName, busiNum }, 
        //         { withCredentials: true }
        //     );

        //     if (response.status === 200) 
        //     {
        //         alert("매장 정보 수정 성공");
        //         setStoreModal(false);
        //         handleStoreDetail();
        //     } 
        //     else 
        //     {
        //         console.error("매장 정보 수정 실패");
        //     }
        // } 
        // catch (error) 
        // {
        //     console.error("매장 정보 수정 오류:", error);
        // }
    }

    const handleCloseStoreModal = () => 
    {
        setStoreModal(false);
        setSelectedStoreId("");
    };
    
    const handleStoreDel = async(userId) =>
    {
        try 
        {    
            //이거 해야됨
            const response = await axios.post(`http://localhost:9000/store/delete/${userId}`, {}, {
                    withCredentials : true,
                    headers: { "Authorization": `Bearer ${localStorage.getItem("token")}`}
            });

            if (response.status == 200) 
            {
                console.log("회원 추방 성공");
                alert("회원 추방 성공");
                navigate(0);
            } 
            else 
            {
                console.error("회원 추방 실패:");
            }
        } 
        catch (error) 
        {
            console.error("회원 추방 오류:", error);
        }
    }
    
    const renderContent = (menu) => {
        if("고객 관리" == menu)
        {
            return <div className="box_Manager_User_Detail">
                    <div className="box_Manager_User_Info">
                        <table className="table_Manager_User_Info">
                        <tr>
                            <th>이메일</th>
                            <th>별명</th>
                            <th>전화번호</th>
                            <th>주소</th>
                            <th>수정</th>
                            <th>삭제</th>
                        </tr>
                            {userlist.map((user) => (
                                <tr>
                                    <td>{user.email}</td>
                                    <td>{user.name}</td>
                                    <td>{user.phone}</td>
                                    <td>{user.addr}</td>
                                    <td>
                                        <button
                                            onClick={() => handleUserModi(user.id)}>
                                            수정
                                        </button>
                                    </td>
                                    <td>
                                        <button
                                            onClick={() => handleUserDel(user.id)}>
                                            삭제
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </table>
                    </div>
                    <div>
                        {userPagination()}
                    </div>
                </div>;
        }
        else if("판매자 / 매장 관리" == menu)
        {
            return <div className="box_Manager_Store_Detail">
                    <div className="box_Manager_Store_Info">
                        <table className="table_Manager_Store_Info">
                        <tr>
                            <th>판매자 이름</th>
                            <th>매장 이름</th>
                            <th>사업자 번호</th>
                            <th>수정</th>
                            <th>삭제</th>
                        </tr>
                            {storelist.map((store) => (
                                <tr>
                                    <td>{store.sellerName}</td>
                                    <td>{store.name}</td>
                                    <td>{store.busiNum}</td>
                                    <td>
                                        <button
                                            onClick={() => handleStoreModi(store.id)}>
                                            수정
                                        </button>
                                    </td>
                                    <td>
                                        <button
                                            onClick={() => handleStoreDel(store.id)}>
                                            삭제
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </table>
                    </div>
                    <div>
                        {storePagination()}
                    </div>
                </div>
        }
        else if("판매자 인증 신청 관리" == menu)
        {
            return <div className="box_Manager_StoreAuth_Detail">
                    <div className="box_Manager_StoreAuth_Info">
                        <table className="table_Manager_StoreAuth_Info">
                        <tr>
                            <th>판매자 이름</th>
                            <th>매장 이름</th>
                            <th>사업자 번호</th>
                            <th>승인</th>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>2</td>
                            <td>3</td>
                            <td><button>승인</button></td>
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
                        {/* {AuthPagination()} */}
                    </div>
                </div>
        }
        else if("공동 구매 관리" == menu)
        {
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
                                <td>공동구매 시작시간표시</td>
                                <td>공동구매 끝시간표시</td>
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
                        {/* {AuthPagination()} */}
                    </div>
                </div>
        }
        else
        {
            return <>선택된 메뉴 없음</>;
        }
      };

    return (        
        <>
        <div className="layout_Manager">
            <div className="box_Manager_Menu">
                <button
                    className="btn_ManagerMenu"
                    onClick={() => {handleUserDetail(); setSelectedMenu("고객 관리");}}>
                    고객 관리
                </button>
                <button
                    className="btn_ManagerMenu"
                    onClick={() => {handleStoreDetail(); setSelectedMenu("판매자 / 매장 관리");}}>
                    판매자 / 매장 관리
                </button>
                <button
                    className="btn_ManagerMenu"
                    onClick={() => setSelectedMenu("판매자 인증 신청 관리")}>
                    판매자 인증 신청 관리
                </button>
                <button
                    className="btn_ManagerMenu"
                    onClick={() => setSelectedMenu("공동 구매 관리")}>
                    공동 구매 관리
                </button>
            </div>
            <div className="layout_Manager_Contents">
                {renderContent(selectedMenu)}
            </div>
        </div>
        <Modal 
            isOpen={modal} 
            onRequestClose={handleCloseModal} 
            className="modal_Manager_User_Modi" 
            overlayClassName="modal_Manager_User_Modi_Overlay">
            <div>
                <table className="table_Modal_Manager_User_Modi">
                    <tr>
                        <td>이메일</td>
                        <td>{getUserById(selectedUserId, "email")}</td>
                    </tr>
                    <tr>
                        <td>별명</td>
                        <td>
                            <input 
                                type="text"
                                placeholder={getUserById(selectedUserId, "name")}
                                value={name}
                                onChange={(e) => setName(e.target.value)}/>
                        </td>
                    </tr>
                    <tr>
                        <td>전화번호</td>
                        <td>
                            <input 
                                type="text"
                                placeholder={getUserById(selectedUserId, "phone")}
                                value={phone}
                                onChange={(e) => setPhone(e.target.value)}/></td>
                    </tr>
                    <tr>
                        <td>주소</td>
                        <td>
                            <input 
                                type="text"
                                placeholder={getUserById(selectedUserId, "addr")}
                                value={addr}
                                onChange={(e) => setAddr(e.target.value)}/>
                        </td>
                    </tr>
                </table>
            </div>
            <div>
                <button 
                    className="btn_Modal_Manager_User_Modi"
                    onClick={() => handleUserModiModal(selectedUserId)}
                    >
                    수정
                </button>
                <button 
                    className="btn_Modal_Manager_User_Modi"
                    onClick={handleCloseModal}>
                    취소
                </button>
            </div>
        </Modal>
        <Modal
            isOpen={storeModal}
            onRequestClose={handleCloseStoreModal}
            className="modal_Manager_Store_Modi"
            overlayClassName="modal_Manager_Store_Modi_Overlay">
            <div>
                <table className="table_Modal_Manager_Store_Modi">
                    <tr>
                        <td>매장 이름</td>
                        <td>
                            <input
                                type="text"
                                placeholder={getStoreById(selectedStoreId, "storeName")}
                                value={storeName}
                                onChange={(e) => setStoreName(e.target.value)}/>
                        </td>
                    </tr>
                    <tr>
                        <td>판매자 이름</td>                        
                        <td>{getStoreById(selectedStoreId, "name")}</td> 
                    </tr>
                    <tr>
                        <td>사업자 번호</td>                        
                        <td>{getStoreById(selectedStoreId, "busiNum")}</td> 
                    </tr>
                </table>
            </div>
            <div>
                <button
                    className="btn_Modal_Manager_Store_Modi"
                    onClick={() => handleStoreUpdate(selectedStoreId)}>
                    수정
                </button>
                <button
                    className="btn_Modal_Manager_Store_Modi"
                    onClick={handleCloseStoreModal}>
                    취소
                </button>
            </div>
        </Modal>
    </>
    );
}

export default ManagerComponent;