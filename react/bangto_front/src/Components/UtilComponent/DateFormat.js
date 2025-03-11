const TimeStamp = (data) => {
    const [date, time] = data.split("T");
    const [year, month, day] = date.split("-");
    const [hour, min, sec] = time.split(":");

    return `${year}년 ${month}월 ${day}일 ${hour}시 ${min}분 ${sec}초`;
}

export default TimeStamp;