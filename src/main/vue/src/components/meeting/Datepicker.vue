<template>
    <div class="datepicker modal">
        <div class="month-year">
            <div class="month-year-selector">
                <div class="add-deco-img-left deco-img-arrow-left text-hidden" @click="decreaseMonth">
                    왼쪽
                </div>
                <label>{{ `${dateTimePicker.currentYear} 년 - ${dateTimePicker.currentMonth} 월`}}</label>
                <div class="add-deco-img-left deco-img-arrow-right text-hidden" @click="increaseMonth">
                    오른쪽
                </div>
            </div>
            <table>
                <tr>
                    <th>일</th>
                    <th>월</th>
                    <th>화</th>
                    <th>수</th>
                    <th>목</th>
                    <th>금</th>
                    <th>토</th>
                </tr>
                <tr v-for="(week, wIndex) in dateTimePicker.dateInMonth" :key="wIndex" @click="selectDay">
                    <td v-for="(day, dIndex) in week" :key="dIndex" :class="{ holiday: day.isHoliday, selected: day.isSelected }">
                        {{ day.value != 0 ? day.value : "" }}
                    </td>
                </tr>
            </table>
            <div :class="{ 'loader': !dateTimePicker.isLoaded }"></div>
        </div>
        <div class="time-selector">
            <div class="time-indicator">
                <label for="">시간</label>
                <input class="input" type="text" name="" id="" placeholder="시간을 선택해주세요" :value="showSelectedTime" readonly>
            </div>
            <div class="hour-minute">
                <ul @click="selectAmPm" class="am-pm">
                    <li :class="{selected:dateTimePicker.amPm[0].isSelected}"> {{ dateTimePicker.amPm[0].value }} </li>
                    <li :class="{selected:dateTimePicker.amPm[1].isSelected}">{{ dateTimePicker.amPm[1].value }}</li>
                </ul>
                <ul @click="selectHour" class="hour">
                    <li v-for="(hour, index) in dateTimePicker.hours" :key="index" :class="{selected:hour.isSelected}"> {{ hour.value }} </li>
                </ul>
                <ul @click="selectMinute" class="minute"> 
                    <li v-for="(minute, index) in dateTimePicker.minutes" :key="index" :class="{selected:minute.isSelected}"> {{ minute.value }} </li>
                </ul>
            </div>
            <div class="message" :class="{'message--error': dateTimePicker.hasError}">
                <span>{{ errorMessage }}</span>
            </div>
        </div>
        <div class="datepicker-buttons">
            <button type="button" class="btn btn-semiround" @click="closeDatepicker">취소</button>
            <button type="button" class="btn btn-action btn-semiround" @click="submitDate">확인</button>
        </div>
    </div>
</template>

<script setup>
import { ref,reactive, computed } from "@vue/runtime-core";
const emit = defineEmits(['closeDatepicker','submitDate']);

const closeDatepicker = () => {
    emit('closeDatepicker');
}

const selectAmPm = (event) => {
    let value = event.target.innerText;
    value = (value=='오전')? 'am' : 'pm';
    dateTimePicker.selectAmPm(value);
    dateTimePicker.hasError = false;
}

const selectHour = (event) => {
    const value = parseInt(event.target.innerText);
    dateTimePicker.selectHour(value);
    dateTimePicker.hasError = false;
}

const selectMinute = (event) =>{
    const value = parseInt(event.target.innerText);
    dateTimePicker.selectMinute(value);
    dateTimePicker.hasError = false;
}

const selectDay = (event) => {
    let value = event.target.innerText;
    
    if (value === "") return;

    value = parseInt(value);

    console.log('일자 선택', value);
    dateTimePicker.selectDay(value);
    dateTimePicker.hasError = false;

}

class DateTimePicker {
    constructor(date) {
        this.today = date;
        this.currentYear = date.getFullYear();
        this.currentMonth = date.getMonth() + 1;
        this.dateInMonth = [];
        this.holidays = [];
        this.isLoaded = false;
        this.hours = [];
        this.minutes = [];
        this.amPm = [];
        this.selectedTime = {
            year: null,
            month: null,
            day: null,
            isAM : null,
            hour: null,
            minute: null,
        }
        this.hasError = false;
    }
    init() {
        this.dateInMonth.length = 0;
        this.minutes.length = 0;
        this.hours.length = 0;

        for (let i = 0; i < 6; i++) {
            let week = [];
            for (let i = 0; i < 7; i++) {
                week.push({
                    value: "",
                    isHoliday : false,
                    isSelected : false,
                });
            }
            this.dateInMonth.push(week);
        }
        
        for (let i = 0; i < 12; i++){
            let hour = (i+1);
            hour = hour < 10 ? `0${hour}` : hour.toString();
            this.hours.push({
                value : hour,
                isSelected : false,
            });

            let minute = i * 5;
            minute = minute < 10 ? `0${minute}` : minute.toString();
            this.minutes.push({
                value : minute,
                isSelected : false,
            })
        }

        this.amPm.push({
            value : '오전',
            isSelected : false,
        })
        this.amPm.push({
            value : '오후',
            isSelected : false,
        })
    }
    update() {
        // 이번달의 시작 요일을 계산함. 0인 경우 일요일, 6인 경우 토요일이다.
        let startDayofCurrentMonth = new Date(this.currentYear, this.currentMonth - 1, 1).getDay();
        let currentWeakIdx = 0;
        let currentDay = startDayofCurrentMonth;
        let currentDateNum = 1;


        let maxDate = this.getMaxDate();
        while (currentWeakIdx <= 5 && currentDateNum <= maxDate) {
            this.dateInMonth[currentWeakIdx][currentDay] = {
                value: currentDateNum,
                isHoliday: false,
                isSelected : false,
            }
            currentDateNum++;
            currentDay++;
            if (currentDay == 7) {
                currentWeakIdx++;
                currentDay = 0;
            }
        }
    }
    decreaseMonth() {
        if (!this.isLoaded) return;

        this.currentMonth--;
        if (this.currentMonth == 0) {
            this.currentMonth = 12;
            this.currentYear--;
            const requestHolidays = this.getHolidays();
            requestHolidays.then(res=>res.json())
            .then(data=>{
                this.holidays = data.response.body.items.item;
                this.isLoaded = true;
                this.init();
                this.update();
                this.updateHolidays();
            })
            .catch(() => {
                console.log('휴일정보를 불러오는데 실패했습니다.');
            });
        }
        else{
            this.init();
            this.update();
            this.updateHolidays();
        }
    }
    increaseMonth() {
        if (!this.isLoaded) return;
        if (this.currentYear == new Date().getFullYear() + 2) return;

        this.currentMonth++;
        if (this.currentMonth == 13) {
            this.currentMonth = 1;
            this.currentYear++;
            const requestHolidays = this.getHolidays();
            requestHolidays.then(res=>res.json())
            .then(data=>{
                this.holidays = data.response.body.items.item;
                this.isLoaded = true;
                this.init();
                this.update();
                this.updateHolidays();
            })
            .catch(() => {
                console.log('휴일정보를 불러오는데 실패했습니다.');
            });
        }
        else{
            this.init();
            this.update();
            this.updateHolidays();
        }
    }
    getMaxDate() {
        let result;

        switch (this.currentMonth) {
            case 1:
                result = 31;
                break;
            case 2:
                if (this.isLeapYear(this.currentYear)) {
                    result = 29;
                }
                else {
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
    getLeapYear() {
        //4로 나눠떨어지면 윤년
        //4로 나눠 떨어지더라도 100으로 나눠떨어지면 평년
        //4로 나눠 떨어지고, 100으로 나눠떨어져도 400으로 나눠떨어지면 윤년

        if (year % 4 != 0) {
            return false;
        }

        if (year % 100 != 0) {
            return true;
        }

        if (year % 400 != 0) {
            return false;
        }

        return true;
    }
    async getHolidays() {
        this.isLoaded = false;
        let serviceKey = 'S1yl3hQHoyz5%2FFbS%2BjDDcMj2zI1kkFrC2VMd0TkMct0Mbad0F3BfYH6C7m2e%2FmpQrGm0iZtjPz%2Fvg%2Bl3eMnuKA%3D%3D';
        let solYear = this.currentYear;
        let url = 'http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?';
        url+=`ServiceKey=${serviceKey}&`;
        url+=`solYear=${solYear}&`;
        url+=`_type=json&`;
        url+=`numOfRows=30`;

        return await fetch(url, {
            method: 'GET',
        })
    }
    updateHolidays(){
        this.holidays.forEach(holiday => {
            const holidayMonth = parseInt(holiday.locdate.toString().slice(4,6))
            const holidayDate = holiday.locdate % 100;
            
            if(holidayMonth !== this.currentMonth) return;

            for(let week of this.dateInMonth){
                for (let day=0; day<week.length; day++){
                    if (week[day].value == holidayDate){
                        week[day].isHoliday = true;
                    }
                }
            }
        })
    }
    isLeapYear(){
    //4로 나눠떨어지면 윤년
    //4로 나눠 떨어지더라도 100으로 나눠떨어지면 평년
    //4로 나눠 떨어지고, 100으로 나눠떨어져도 400으로 나눠떨어지면 윤년

    if (this.currentYear % 4 != 0){
        return false;
    }

    if (this.currentYear % 100 != 0){
        return true;
    }

    if (this.currentYear % 400 != 0){
        return false;
    }

    return true;
    }
    
    selectHour(value){
        this.hours.forEach(hour=>hour.isSelected=false);
        this.selectedTime.hour = value;
        this.hours[value - 1].isSelected = true;
    }

    selectMinute(value){
        this.minutes.forEach(minute=>minute.isSelected=false);
        this.selectedTime.minute = value;
        this.minutes[value/5].isSelected = true;
    }

    selectAmPm(value){
        this.amPm.forEach(item=>item.isSelected=false);
        if(value =='am'){
            this.selectedTime.isAM = true;
            this.amPm[0].isSelected = true;
        }
        else{
            this.selectedTime.isAM = false;
            this.amPm[1].isSelected = true;
        }
    }
    selectDay(inputDay){
        this.dateInMonth.forEach(week=>{
            week.forEach(day=>{
                day.isSelected = false;
            })
        })

        this.selectedTime.year = this.currentYear;
        this.selectedTime.month = this.currentMonth;
        this.selectedTime.day = inputDay;
        this.dateInMonth.forEach(week=>{
            week.forEach(day=>{
                if(day.value == inputDay){
                    day.isSelected = true;
                }
            })
        })
    }
    getFormattedDate(){
        let year = this.selectedTime.year;
        let month = this.selectedTime.month;
        let day = this.selectedTime.day;
        let hour = (this.selectedTime.isAM) ? this.selectedTime.hour : this.selectedTime.hour + 12;
        let minute = this.selectedTime.minute;

        month = (month < 10) ? `0${month}` : `${month}`;
        day = (day < 10) ? `0${day}` : `${day}`;
        hour = (hour < 10) ? `0${hour}` : `${hour}`;
        minute = (minute < 10) ? `0${minute}` : `${minute}`;
        console.log(`${year}-${month}-${day}T${hour}:${minute}`);
        return `${year}-${month}-${day}T${hour}:${minute}`;
    }

    checkInputValid(){
        if(!(this.selectedTime.year != null && this.selectedTime.month != null && this.selectedTime.day != null)){
            this.hasError = true;
            throw new Error('날짜를 선택해주세요.');
        }
        if(!(this.selectedTime.isAM != null&& this.selectedTime.hour != null && this.selectedTime.minute != null)){
            this.hasError = true;
            throw new Error('시간을 선택해주세요');
        }
    }
}

const today = new Date();

const dateTimePicker = reactive(new DateTimePicker(today));
dateTimePicker.init();
const requestHolidays = dateTimePicker.getHolidays();
requestHolidays
.then(res=>res.json())
.then(data=>{
    dateTimePicker.holidays = data.response.body.items.item;
    dateTimePicker.isLoaded = true;
    dateTimePicker.init();
    dateTimePicker.update();
    dateTimePicker.updateHolidays();
})


const decreaseMonth = () => {
    dateTimePicker.decreaseMonth();   
}

const increaseMonth = () => {
    dateTimePicker.increaseMonth();
}

const errorMessage = ref("");

const submitDate = () => {
    try{
        dateTimePicker.checkInputValid();
        const formattedDate = dateTimePicker.getFormattedDate();
        const data = {
            formattedDate : formattedDate,
            dateInfo : dateTimePicker.selectedTime,
        }
        emit('submitDate', data);
        emit('closeDatepicker');
    }
    catch (err){
        errorMessage.value = err.message;
    };
}

const showSelectedTime = computed(()=>{
    let amPm, hour, minute = null;

    amPm = (dateTimePicker.selectedTime.isAM) ? '오전' : '오후';

    if (dateTimePicker.selectedTime.hour){
        let selectedHour = dateTimePicker.selectedTime.hour;
        hour = (selectedHour < 10) ? `0${selectedHour}` : selectedHour;
    }
    else{
        hour = "   ";
    }

    if (dateTimePicker.selectedTime.minute != null){
        let selectedMinute = dateTimePicker.selectedTime.minute;
        minute = (selectedMinute < 10) ? `0${selectedMinute}` : selectedMinute;
    }
    else{
        minute = "   ";
    }

    return `${amPm} ${hour} : ${minute}`;
})

</script>

<style scoped>
.datepicker {
    width: 300px;
    height: fit-content;
    background-color: white;
    padding:10px 30px;
    border: 1px solid #c4c4c4;
    box-shadow: 0px 3px 8px rgba(0, 0, 0, 0.25);
    border-radius: 10px;
    position:absolute;
}

.month-year{
    position:relative;
}

.datepicker .month-year-selector {
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 10px 0;
}

.datepicker label {
    width: 8rem;
    text-align: center;
}

.time-indicator{
    width:100%;
    display: flex;
    justify-content: space-between;
    align-items:center;
    margin: 10px 0;
}

.time-indicator > label{
    font-weight:var(--bold);
}

.time-indicator > input{
    font-size:18px;
    width:150px;
    text-align:center;
}

.hour-minute {
    display: flex;
    transition: max-height 0.2s;
    justify-content: center;
}

/* .hour-minute > ul{
    border: 1px solid #c4c4c4;
    border-radius:5px;
} */


.datepicker .hour-minute>ul {
    /*
    display: flex;
    flex-direction: column;
    justify-content: center;
    */
    height: 200px;
    overflow: scroll;
    overscroll-behavior: contain;
}

.datepicker .hour-minute>ul::-webkit-scrollbar {
    display: none;
}


.datepicker .hour-minute>ul:last-child {
    row-gap: 10px;
}

.datepicker .hour-minute>ul li {
    display: flex;
    width: 3rem;
    padding: 0.5rem 0.5rem;
    margin:0 0.5rem;
    justify-content: center;
    align-items: center;
    cursor: pointer;
}

.hour-minute>ul li:hover{
    background-color: var(--light-grey1);
    border-radius: 5px;
}

.datepicker table td {
    text-align: center;
    width: 2rem;
    height: 2rem;
    cursor:pointer;
}

.datepicker table th {
    font-weight: 500;
    color: var(--grey2);
}

.datepicker table tr>td:nth-child(7) {
    color: var(--true-blue);
}

.datepicker table tr>td:nth-child(1),
.datepicker .holiday {
    color: var(--red-theme);
}

.datepicker-buttons{
    display:flex; 
    justify-content: center; 
    column-gap: 10px; 
    padding: 30px 0;
}

.add-deco-img-left::before{
    width:16px;
    height:16px;
    flex: 0 0 16px;
    cursor: pointer;
}

.loader::before{
    font-size:5px;
}

.selected{
    background-color:var(--main-color) !important;
    border-radius:5px;
    font-weight:var(--bold);
    color:var(--white);
}

.message > span{
    opacity: 0;
    height: 16px;
}

.message--error > span{
    color:var(--error-red);
    opacity:1;
    transition: all 0.2s;
}
</style>