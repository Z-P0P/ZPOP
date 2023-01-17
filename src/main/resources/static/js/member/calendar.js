window.onload = () => {
    const calendar = document.querySelector("#calendar");
    const today = new Date();
    let currentYear = today.getFullYear();
    let currentMonth = today.getMonth() + 1;
    let date = calendar.querySelector("#date");
    let weeks = document.querySelectorAll("#calendar table tr");
    let holiday = getHolidays(currentYear);
    let calendarDate;
    


    init();
    update();
    display();


    const decreaseMonthBtn = calendar.querySelector("#decrease-month");
    const increaseMonthBtn = calendar.querySelector("#increase-month");

    decreaseMonthBtn.onclick = function(){
        currentMonth--;
        if (currentMonth == 0){
            currentMonth = 12;
            currentYear--;
        }
        init();
        update();
        display();
    }

    increaseMonthBtn.onclick = function(){
        currentMonth++;
        if (currentMonth == 13){
            currentMonth = 1;
            currentYear++;
        }
        init();
        update();
        display();
    }

    function init(){
        calendarDate = [];
        for (let i = 0; i < 6; i++){
            let week = [];
            for (let i = 0; i < 7; i++){
                week.push(0);
            }
            calendarDate.push(week);
        }
    }


    function display(){
        date.innerText = `${currentYear} 년 - ${currentMonth} 월`;
        for (let calendarWeek = 0; calendarWeek < 6; calendarWeek++){
            let week = weeks[calendarWeek + 1];
            for (let calendarDay = 0; calendarDay < 7; calendarDay++){
                let day = week.children[calendarDay];
                if (calendarDate[calendarWeek][calendarDay] == 0){
                    day.innerText = "";
                }
                else{
                    day.innerText = calendarDate[calendarWeek][calendarDay];
                }
            }
        }
    }

    function update(){
        
        let startDayofCurrentMonth = new Date(currentYear, currentMonth - 1, 1).getDay(); 
        let currentWeakIdx = 0;
        let currentDay = startDayofCurrentMonth;
        let currentDateNum = 1;
    
        
        let maxDate = getMaxDate(currentYear, currentMonth);
        while(currentWeakIdx <= 5 && currentDateNum <= maxDate){
            calendarDate[currentWeakIdx][currentDay]= currentDateNum;
            currentDateNum ++;
            currentDay++;
            if (currentDay == 7){
                currentWeakIdx ++;
                currentDay = 0;
            }
        }
    }
}


function getMaxDate(year,month){

    let result;

    switch(month){
        case 1:
        result = 31;
        break;
        case 2:
        if (isLeapYear(year)){
            result = 29;
        }
        else{
            result = 28;
        }
        break;
        case 3:
        result = 31;
        break;
        case 4:
        result = 30;
        break;
        case 5:
        result = 31;
        break;
        case 6:
        result = 30;
        break;
        case 7:
        result = 31;
        break;
        case 8:
        result = 31;
        break;
        case 9:
        result = 30;
        break;
        case 10:
        result = 31;
        break;
        case 11:
        result = 30;
        break;
        case 12:
        result = 31;
        break;
    }
    return result;

}

function isLeapYear(year){
    //4로 나눠떨어지면 윤년
    //4로 나눠 떨어지더라도 100으로 나눠떨어지면 평년
    //4로 나눠 떨어지고, 100으로 나눠떨어져도 400으로 나눠떨어지면 윤년

    if (year % 4 != 0){
        return false;
    }

    if (year % 100 != 0){
        return true;
    }

    if (year % 400 != 0){
        return false;
    }

    return true;
}

function getHolidays(year){
    let serviceKey = 'S1yl3hQHoyz5%2FFbS%2BjDDcMj2zI1kkFrC2VMd0TkMct0Mbad0F3BfYH6C7m2e%2FmpQrGm0iZtjPz%2Fvg%2Bl3eMnuKA%3D%3D';
    let solYear = year;
    let url = `http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getHoliDeInfo?
    serviceKey=${serviceKey}&
    solYear=${solYear}&
    numOfRows=30&_type=json`;
    fetch(url, {
    method: 'GET',
  })
  .then(response => {
    return response.json();
  })
  .then(data => {
    
  });
}