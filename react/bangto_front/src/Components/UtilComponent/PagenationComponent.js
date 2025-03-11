import { useState } from 'react';
import { Pagination } from 'react-bootstrap';

const PagenationComponent = (props) => {
    const [selectedPage, setSelectedPage] = useState(props.page); // 현재 선택된 페이지

    const handlePageClick = (pageNum) => {
        setSelectedPage(pageNum+1); // 페이지 클릭 시 선택된 페이지 상태 업데이트
        // 여기서 선택된 페이지 번호를 사용할 수 있음 (예: API 호출 등)
        props.setPage(pageNum+1);
    };
    return (
        <div>
            <Pagination style={{justifyContent : 'center'}}>
                <Pagination.First />
                <Pagination.Prev />
                {
                    [...Array(props.totalPage)].map((_, i) => {
                        const pageNumber = i+1;
                        if((pageNumber >= props.page -1 && pageNumber <= props.page +1) ||
                            (pageNumber == 1 || pageNumber == props.totalPage)) 
                            return <Pagination.Item 
                                        key={pageNumber}
                                        onClick={() => handlePageClick(i)}
                                    >{pageNumber}</Pagination.Item>
                        else if(pageNumber == props.page -2 || pageNumber == props.page +2){
                            return <Pagination.Ellipsis></Pagination.Ellipsis>
                        }
                    })
                }
                <Pagination.Next />
                <Pagination.Last />
            </Pagination>
        </div>
    );
}

export default PagenationComponent;