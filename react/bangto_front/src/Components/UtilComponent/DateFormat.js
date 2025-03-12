const TimeStamp = (data) => {
    const [date, time] = data.split("T");
    const [year, month, day] = date.split("-");
    const [hour, min, sec] = time.split(":");

    return `${year}년 ${month}월 ${day}일 (${hour}:${min}:${sec})`;
}

const StartTimeFormat = (data) => {
    return data + "T00:00:00";
}
const EndTimeFormat = (data) => {
    return data + "T23:59:59";
}

export {TimeStamp, StartTimeFormat, EndTimeFormat};