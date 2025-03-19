import { useEffect } from "react";
import Modal from "react-modal";
import axios from "axios";
Modal.setAppElement("#root");

const UserGroupItemPayModalComponent = (props) => {
  const params = new URLSearchParams(window.location.search);
  const amount = params.get("amount");

  useEffect(() => {
    console.log(props.payModal);
  }, [props.payModal]);

  const handlePayGroupItem = async () => {
    try {
      if (props.address !== undefined) {
        const groupItem = JSON.parse(localStorage.getItem("group-item"));
        const response = await axios.post(
          `${process.env.REACT_APP_BACKEND_SERVER_PORT}/group-pay/pay`,
          {
            groupItemPk: groupItem.id,
            optionPk: groupItem.option.id,
            amount,
            address: props.address,
            itemPk: groupItem.item.id,
          },
          {
            withCredentials: true,
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        );
        if (response.status == 200) {
          alert("결제 성공");
          props.setPayModal(false);
        }
      } else {
        alert("주소 입력 오류");
      }
    } catch (err) {
      console.log(err);
      alert("결제 실패");
    }
  };

  return props.payModal !== undefined &&
    props.setPayModal !== undefined &&
    props.payModal &&
    props.wallet ? (
    <Modal
      isOpen={props.payModal}
      onRequestClose={() => props.setPayModal(false)}
      className="modal_User_Pay"
      overlayClassName="modal_User_Pay_Overlay"
    >
      <table className="User_Pay_Cash">
        <tr>
          <td>현재 보유 캐시</td>
          <td>{props.wallet.cash}원</td>
        </tr>
        <tr>
          <td>결제 후 캐시</td>
          <td>{props.wallet.cash - props.totalCost}원</td>
        </tr>
        <tr>
          <td>예상 캐시백 포인트</td>
          {props.wallet.cash - props.totalCost > 0 ? (
            <td>
              50% 판매 시 {(props.wallet.cash - props.totalCost) * 0.1}
              <br />
              80% 판매 시 {(props.wallet.cash - props.totalCost) * 0.15}
              <br />
              100% 판매 시 {(props.wallet.cash - props.totalCost) * 0.2}
              <br />
            </td>
          ) : (
            <td>잔액이 부족합니다.</td>
          )}
        </tr>
      </table>
      <div>
        <button className="btn_User_Pay" onClick={() => handlePayGroupItem()}>
          결제완료
        </button>
        <button
          className="btn_User_Pay"
          onClick={() => props.setPayModal(false)}
        >
          결제취소
        </button>
      </div>
    </Modal>
  ) : null;
};

export default UserGroupItemPayModalComponent;
