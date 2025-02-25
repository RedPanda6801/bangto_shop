import React, { useState } from 'react';
import Modal from 'react-modal';
import './UserComponent.css';

Modal.setAppElement('#root');

const UserComponent = () => 
{
    const [selectedMenu, setSelectedMenu] = useState("결제 내역");
    const [isUserInfoOpen, setIsUserInfoOpen] = useState(false);
    const [email, setEmail] = useState("");
    const [name, setName] = useState("");
    const [detailAddr, setDetailAddr] = useState("");
    const [phone, setPhone] = useState("");
    const [isPasswordMatch, setIsPasswordMatch] = useState(true);
    const [passwordConfirm, setPasswordConfirm] = useState("");
    const [pw, setPw] = useState("");
    const [keyword, setKeyword] = useState("");
    const [selectedAddress, setSelectedAddress] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const [addressList, setAddressList] = useState([]);
    const [isAddressSearchOpen, setIsAddressSearchOpen] = useState(false);
    const [totalPage, setTotalPageNum] = useState(1);
    const [visiblePages, setPageNums] = useState([]);

    const handleUserInfo = () => 
    {
      setIsUserInfoOpen(true);
    };

    const closeModalAndChangeMenu = () => {
        setIsUserInfoOpen(false);
        setSelectedMenu("결제 내역");
    };

    const getAddr = (page = 1) => 
    {
        const apiUrl = `https://business.juso.go.kr/addrlink/addrLinkApiJsonp.do?currentPage=${page}&countPerPage=10&keyword=${encodeURIComponent(keyword)}&confmKey=devU01TX0FVVEgyMDI1MDIwNDEzMzEwMDExNTQ0MzY=&resultType=json&callback=handleApiResponse`;

        window.handleApiResponse = (data) => 
        {
        console.log("API 응답 데이터:", data);
        if (data.results.common.errorCode !== "0") 
        {
            switch(data.results.common.errorCode)
            {
            case "E0005":
                alert("주소가 입력되지 않았습니다.");
                break;
            case "E0006":
                alert("주소를 상세히 입력해주세요");
                break;
            case "E0008":
                alert("주소를 한글자 이상 입력해주세요");
                break;
            case "E0009":
                alert("주소는 숫자만 검색하지못합니다.");
                break;
            case "E0010":
                alert("주소가 너무 깁니다.(한글 40자, 영문, 숫자 80자 이하로 입력)");
                break;
            case "E0011":
                alert("주소에 긴 숫자가 포함되어있습니다.(숫자 10자 이하로 입력)");
                break;
            case "E0012":
                alert("주소는 특수문자 + 숫자만으로는 검색이 불가능합니다.");
                break;
            case "E0013":
                alert("주소는 특수문자(%, =, >, <, [, ])는 검색이 불가능합니다.");
                break;
            case "E0015":
                alert("주소 검색 범위를 초과했습니다.");
                break;
            default:
                alert("주소가 잘못 입력되었습니다.");
                break;
            }
        } 
        else 
        {
            setAddressList(data.results.juso);
            setIsAddressSearchOpen(true);
            setTotalPageNum(Math.ceil(data.results.common.totalCount / 10));
            updatePageNums(page, Math.ceil(data.results.common.totalCount / 10));
        }
    };

    const script = document.createElement('script');
    script.src = apiUrl;
    document.body.appendChild(script);
    };

    
    const updatePageNums = (currentPage, totalPage) =>
    {
        const pages = [];
        const range = 10;
        let start = Math.max(currentPage - 5, 1);
        let end = Math.min(start + range - 1, totalPage);
    
        if (end - start < range) 
        {
            start = Math.max(end - range + 1, 1);
        }
    
        for (let i = start; i <= end; i++)
        {
            pages.push(i);
        }
        setPageNums(pages);
    };

    const handleAddressSelect = (address) => 
    {
        setSelectedAddress(address);
        setIsAddressSearchOpen(false);
    };

    
    const handlePageChange = (page) => 
    {
        setCurrentPage(page);
        getAddr(page);
        updatePageNums(page, totalPage);
    };

    const renderContentForMenu = (menu) => {
        if("내 정보 조회" == menu)
        {
            return <div className="box_User_Info">
                    {/* <Modal 
                        isOpen={isUserInfoOpen} 
                        onRequestClose={closeModalAndChangeMenu}
                        contentLabel="비밀번호 확인 창"
                        className="modal_Addr"
                        overlayClassName="modal_Overlay_Addr"
                        ariaHideApp={false}>
                        내 정보 조회 모달임
                    </Modal> */}
                        <input 
                            className="user_Email" 
                            type="email" 
                            placeholder="* 이메일"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}/>
                        <input 
                            className="user_Name" 
                            type="text" 
                            placeholder="* 별명"
                            value={name}
                            onChange={(e) => setName(e.target.value)}/>
                        <input 
                            className="user_Tel" 
                            type="tel" 
                            placeholder="전화번호"
                            value={phone}
                            onChange={(e) => setPhone(e.target.value)}/>
                        <div>
                            <input
                                className="user_Addr"
                                type="text" 
                                value={keyword} 
                                onChange={(e) => setKeyword(e.target.value)} 
                                placeholder="주소 입력"/>
                            <button
                                className="User_Addr_Search"
                                onClick={() => { setCurrentPage(1); getAddr(1)}}>
                                검색
                            </button>
                        </div>
                        <div 
                            className="user_Addr_Road"
                            contentEditable={false}
                            tabindex="0">
                            {selectedAddress ? selectedAddress.roadAddr : "도로명 주소"}
                        </div>
                        <input 
                            className="user_Addr_Detail"
                            type="text" 
                            value={detailAddr}
                            onChange={(e) => setDetailAddr(e.target.value)}
                            placeholder="상세 주소"/>
                        <input 
                            className="user_Addr_Zip"
                            type="text" 
                            value={selectedAddress ? selectedAddress.zipNo : ""} 
                            placeholder="우편번호" 
                            readOnly/>
                    <Modal 
                        isOpen={isAddressSearchOpen} 
                        onRequestClose={() => setIsAddressSearchOpen(false)}
                        contentLabel="주소 선택 창"
                        className="modal_Addr"
                        overlayClassName="modal_Overlay_Addr"
                        ariaHideApp={false}>
                        <h3>주소 선택</h3>
                        <button 
                            className="modal_Addr_Close" 
                            onClick={() => setIsAddressSearchOpen(false)}>
                            X
                        </button>
                        <table>
                            <tr>
                            <th>도로명 주소</th>
                            <th>우편번호</th>
                            </tr>
                            {addressList.map((address, index) => (
                            <tr 
                                key={index} 
                                onClick={() => handleAddressSelect(address)}>
                                <td>{address.roadAddr}</td>
                                <td>{address.zipNo}</td>
                            </tr>
                            ))}
                        </table>
                        <div className="pagination_Addr">
                            {currentPage > 1 && (
                            <button onClick={() => handlePageChange(currentPage - 1)}>
                                &lt;
                            </button>
                            )}
                            {visiblePages.map((page) => (
                            <button
                                key={page}
                                onClick={() => handlePageChange(page)}
                                className={currentPage == page ? 'active' : ''}>
                                {page}
                            </button>
                            ))}
                            {currentPage < totalPage && (
                            <button onClick={() => handlePageChange(currentPage + 1)}>
                                &gt;
                            </button>
                            )}
                        </div>
                    </Modal>
            </div>;
        }
        else if("결제 내역" == menu)
        {
            return <div className="box_User_Paid">
                    <div className="box_User_Cash">
                        <table className="table_User_Cash">
                            <tr>
                                <td>
                                    캐시
                                </td>
                                <td>
                                    100,000 원
                                </td>
                            </tr>
                        </table>
                        <table className="table_User_Cash">
                            <tr>
                                <td>
                                    캐시백
                                </td>
                                <td>
                                    10,000 원
                                </td>
                            </tr>
                        </table>
                    </div>
                    <table className="table_User_Paid">
                        <tr>
                            <td colSpan={4}>결제일</td>
                            <td>배송정보</td>
                        </tr>
                        <tr>
                            <td rowSpan={2}>
                                <input
                                    type="image"
                                    className="btn_User_Cart_Img"/>
                            </td>
                            <td colSpan={4}>
                                물품 이름
                            </td>
                        </tr>
                        <tr>
                            <td>
                                옵션
                            </td>
                            <td>
                                개수
                            </td>
                            <td>
                                결제금액
                            </td>
                            <td>
                                <button>후기작성</button>
                            </td>
                        </tr>
                    </table>
                    <table className="table_User_Paid">
                        <tr>
                            <td colSpan={4}>결제일</td>
                            <td>배송정보</td>
                        </tr>
                        <tr>
                            <td rowSpan={2}>
                                <input
                                    type="image"
                                    className="btn_User_Cart_Img"/>
                            </td>
                            <td colSpan={4}>
                                물품 이름
                            </td>
                        </tr>
                        <tr>
                            <td>
                                옵션
                            </td>
                            <td>
                                개수
                            </td>
                            <td>
                                결제금액
                            </td>
                            <td>
                                <button>후기작성</button>
                            </td>
                        </tr>
                    </table>
            </div>;
        }
        else if("내 QNA" == menu)
        {
            return <div className="box_User_QNA">
                    <table className="table_User_QNA">
                        <tr>
                            <td>
                                내용
                            </td>
                            <td>
                                문의 작성일
                            </td>
                        </tr>
                        <tr>
                            <td>
                                답변
                            </td>
                            <td>
                                답변 작성일
                            </td>
                        </tr>
                    </table>
                    <table className="table_User_QNA">
                        <tr>
                            <td>
                                내용
                            </td>
                            <td>
                                문의 작성일
                            </td>
                        </tr>
                        <tr>
                            <td>
                                답변
                            </td>
                            <td>
                                답변 작성일
                            </td>
                        </tr>
                    </table>
            </div>;
        }
        else
        {
          return <div>선택 메뉴 없음</div>
        }
    };

    return (
        <>
          <div className="layout_User">
            <div className="box_User_Menu">
                <button
                    className="btn_User_Menu"
                    onClick={() => {handleUserInfo(); setSelectedMenu("내 정보 조회");}}>
                    내 정보 조회
                </button>
                <button
                    className="btn_User_Menu"
                    onClick={() => setSelectedMenu("결제 내역")}>
                    결제 내역
                </button>
                <button
                    className="btn_User_Menu"
                    onClick={() => setSelectedMenu("내 QNA")}>
                    내 QNA
                </button>
            </div>
            <div className="layout_User_Contents">
              {renderContentForMenu(selectedMenu)}
            </div>
          </div>
        </>
    );
}

export default UserComponent;