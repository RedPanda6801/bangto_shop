import React, { useState, useEffect } from 'react';
import Modal from 'react-modal';
import './UserSignComponent.css';

Modal.setAppElement('#root');

const UserSignComponent = () => {
  const [email, setEmail] = useState("");
  const [nickname, setNickname] = useState("");
  const [isPasswordMatch, setIsPasswordMatch] = useState(true);
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [password, setPassword] = useState('');
  const [keyword, setKeyword] = useState('');
  const [addressList, setAddressList] = useState([]);
  const [selectedAddress, setSelectedAddress] = useState(null);
  const [isAddressSearchOpen, setIsAddressSearchOpen] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPage, setTotalPageNum] = useState(1);
  const [visiblePages, setPageNums] = useState([]);

  // 필수 입력 항목 확인
  const isFormValid = email && password && passwordConfirm && nickname && isPasswordMatch;

  // 비밀번호 확인
  useEffect(() => { setIsPasswordMatch(password === passwordConfirm); }, [password, passwordConfirm]);

  // 페이지 번호 설정
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

  // 주소
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

  // 선택한 주소 저장
  const handleAddressSelect = (address) => 
  {
    console.log("선택한 주소:", address);
    setSelectedAddress(address);
    setIsAddressSearchOpen(false);
  };

  // 주소창 페이지 바꾸기
  const handlePageChange = (page) => 
  {
    setCurrentPage(page);
    getAddr(page);
    updatePageNums(page, totalPage);
  };

  return (
    <div className="layout_Sign">
      <div className="box_Sign">
        <table>
          <tr>
            <td>
              <input 
                className="sign_Email" 
                type="email" 
                placeholder="* 이메일"
                value={email}
                onChange={(e) => setEmail(e.target.value)}/>
            </td>
          </tr>
        </table>
        <table>
          <tr>
            <td>
              <input 
                className="sign_Pw" 
                type="password" 
                placeholder="* 비밀번호" 
                value={password} 
                onChange={(e) => setPassword(e.target.value)}/>
            </td>
          </tr>
        </table>
        <table>
          <tr>
            <td>
              <input 
                className="sign_Pw_Ck" 
                type="password" 
                placeholder="* 비밀번호확인" 
                value={passwordConfirm} 
                onChange={(e) => setPasswordConfirm(e.target.value)}/>
            </td>
          </tr>
        </table>
        {!isPasswordMatch && <p className="pW_Ck_Error_Message">비밀번호가 일치하지 않음</p>}
        <table>
          <tr>
            <td>
              <input 
                className="sign_Name" 
                type="text" 
                placeholder="* 별명"
                value={nickname}
                onChange={(e) => setNickname(e.target.value)}/>
            </td>
          </tr>
        </table>
        <table>
          <tr>
            <td>
              <input 
                className="sign_Tel" 
                type="tel" 
                placeholder="전화번호"/>
            </td>
          </tr>
        </table>
        <table>
          <tr>
            <td>
              <input 
                className="sign_Addr"
                type="text" 
                value={keyword} 
                onChange={(e) => setKeyword(e.target.value)} 
                placeholder="주소 입력"
                onKeyDown={(e) => { if (e.key === 'Enter') { getAddr(1); } }} />
            </td>
          </tr>
        </table>
        <button 
          className="sign_Addr_Search"
          onClick={() => { setCurrentPage(1); getAddr(1)}}>
          검색
        </button>
        <table>
          <tr>
            <td>
              <div 
                className="sign_Addr_Road"
                contentEditable={false}
                tabindex="0"
                style={{ color: selectedAddress ? "black" : "gray"}}>
                {selectedAddress ? selectedAddress.roadAddr : "도로명 주소"}
              </div>
            </td>
          </tr>
          <tr>
            <td>
              <input 
                className="sign_Addr_Detail"
                type="text" 
                placeholder="상세 주소"/>
            </td>
          </tr>
          <tr>
            <td>
              <input 
                className="sign_Addr_Zip"
                type="text" 
                value={selectedAddress ? selectedAddress.zipNo : ""} 
                placeholder="우편번호" 
                readOnly/>
            </td>
          </tr>
        </table> 
        {!isFormValid && <p className="info_Ck_Error_Message">* 필수 입력 항목을 작성해주세요</p>}
        <button 
          className="sign_Submit"
          disabled={!isFormValid}
          onClick={() => (window.location.href = '/')}>
          회원가입
        </button>
        <Modal 
          isOpen={isAddressSearchOpen} 
          onRequestClose={() => setIsAddressSearchOpen(false)}
          contentLabel="주소 선택 창"
          className="modal_Addr"
          overlayClassName="modal_Overlay_Addr"
          ariaHideApp={false}>
          <h3>주소 선택</h3>
          <button className="modal_Addr_Close" onClick={() => setIsAddressSearchOpen(false)}>X</button>
          <table>
            <tr>
              <th>도로명 주소</th>
              <th>우편번호</th>
            </tr>
            {addressList.map((address, index) => (
              <tr key={index} onClick={() => handleAddressSelect(address)} style={{ cursor: 'pointer' }}>
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
                className={currentPage === page ? 'active' : ''}>
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
      </div>
    </div>
  );
};

export default UserSignComponent;
