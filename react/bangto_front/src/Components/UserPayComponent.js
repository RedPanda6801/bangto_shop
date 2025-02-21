import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Modal from 'react-modal';
import './UserPayComponent.css';

Modal.setAppElement('#root');

const UserPayComponent = () => 
{
    const navigate = useNavigate();
    const [payModal, setPayModal] = useState(false);

    const handlePayModal = () => 
    {
        setPayModal(true);
    }

    const handleClosePayModal = () => 
    {
        setPayModal(false);
    };

    return <><div className="layout_User_Pay">
            <div className="box_User_Pay"> 
                <table className="box_User_Pay_Items">
                    <tr>
                        <td rowSpan={2}>
                            <input
                                type="image"
                                className="btn_User_Pay_Img"/>
                        </td>
                        <td colSpan={2}>
                            물품 설명
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            옵션
                        </td>
                        <td>
                            개수
                        </td>
                        <td>
                            가격
                        </td>
                    </tr>
                </table>
                <table className="box_User_Pay_Items">
                    <tr>
                        <td rowSpan={2}>
                            <input
                                type="image"
                                className="btn_User_Pay_Img"/>
                        </td>
                        <td colSpan={2}>
                            물품 설명
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            옵션
                        </td>
                        <td>
                            개수
                        </td>
                        <td>
                            가격
                        </td>
                    </tr>
                </table>
                <line className="div_line"></line>
                <table >
                    <tr>
                        <td>
                            <input
                                className="User_Pay_Addr"
                                type="text" 
                                placeholder="주소 입력"/>
                        </td>
                    </tr>
                </table>
                <button
                    className="User_Pay_Addr_Search">
                    검색
                </button>
                <table>
                <tr>
                    <td>
                        <div 
                            className="User_Pay_Addr_Road"
                            contentEditable={false}
                            tabindex="0">
                            도로명주소
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input 
                            className="User_Pay_Addr_Detail"
                            type="text" 
                            placeholder="상세 주소"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input 
                            className="User_Pay_Addr_Zip"
                            type="text"
                            placeholder="우편번호" 
                            readOnly/>
                    </td>
                </tr>
                </table> 
                <line className="div_line"></line>     
                <table
                    className="User_Pay_Cash">
                    <tr>
                        <td>
                            현재 캐시백
                        </td>
                        <td>
                            100,000 원
                        </td>
                    </tr>
                    <tr>
                        <td>
                            사용 캐시백
                        </td>
                        <td>
                            <input
                                className="User_Pay_Use_Cash"
                                type="number"/>원
                        </td>
                    </tr>
                </table>
                <table
                    className="User_Pay_Cash">
                    <tr>
                        <td>
                            총 상품 가격
                        </td>
                        <td>
                            123,000 원
                        </td>
                    </tr>
                    <tr>
                        <td>
                            캐시백 사용 금액
                        </td>
                        <td>
                            1,000 원
                        </td>
                    </tr>
                    <tr>
                        <td>
                            배송비
                        </td>
                        <td>
                            3,000 원
                        </td>
                    </tr>
                    <tr>
                        <td>
                            총 결제 금액
                        </td>
                        <td>
                            125,000 원
                        </td>
                    </tr>
                </table>
                <div>
                    <button 
                        className="btn_User_Pay_Cash"
                        onClick={() => handlePayModal()}>
                        결제하기
                    </button>
                    <button 
                        className="btn_User_Pay_Cash"
                        onClick={() => navigate(-1)}>
                        뒤로가기
                    </button>
                </div>
            </div>
        </div>        
        <Modal
            isOpen={payModal}
            onRequestClose={handleClosePayModal}
            className=""
            overlayClassName="">
            <div>
                결제모달임
            </div>
        </Modal>
    </>
}

export default UserPayComponent;