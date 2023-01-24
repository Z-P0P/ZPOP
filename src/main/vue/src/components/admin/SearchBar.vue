<template>
    <div class="admin-search-bar-div">
        <div class="admin-search-bar-box">
            <form @submit.prevent="submitHandler" :action="route.path">
                <input class="admin-search-bar" type="text" placeholder="키워드를 입력하세요" name="keyword">
            </form>
            <button v-if="searchOption" class="admin-search-button" @click.stop="clickHandler"></button>
            <div class="filter-box" v-show="isOptionOpened">
                <form @submit.prevent.stop="detailSubmitHandler" :action="route.path">
                    <div>
                        <span>검색어</span>
                        <input class="keyword-input" type="text" name="keyword">
                    </div>
                    <div>
                        <span>검색 옵션</span>
                        <select name="option">
                            <option value="title" selected>제목</option>
                            <option value="nickname">작성자 닉네임</option>
                            <option value="id">ID</option>
                        </select>
                    </div>
                    <div>
                        <span>기간</span>
                        <input class="date-input" type="date" name="minDate">
                        <span>로 부터</span>
                        <select class="period-select" name="period">
                            <option selected value="1">1일</option>
                            <option value="3">3일</option>
                            <option value="7">1주</option>
                            <option value="30">1개월</option>
                            <option value="90">3개월</option>
                            <option value="180">6개월</option>
                            <option value="360">1년</option>
                        </select>
                    </div>
                    <div>
                        <span>페이지당 결과</span>
                        <select class="result-amount" name="num">
                            <option value="10" selected>10개</option>
                            <option value="20">20개</option>
                            <option value="50">50개</option>
                            <option value="100">100개</option>
                        </select>
                    </div>
                    <div class="search-button"><input type="submit" value="검색" class="filter-button"></div>
                </form>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import  router from '../../router';
const isOptionOpened = ref(false);
const props = defineProps(['searchOption']);
const route = useRoute();
const clickHandler = () => {
    isOptionOpened.value = !isOptionOpened.value;
}

const currentParams = ref({});

const submitHandler = (e) => {
    const data = new FormData(e.target);
    const submittedParams = [...data.entries()];

    const keywordParam = submittedParams[0];
    currentParams.value.keyword = keywordParam[1];

    const queryString = currentParams.toString();

    const url = `${route.path}?${queryString}`;
    // route.query;
    router.push({
        path: route.path,
        query : currentParams.value,
    });
}

const detailSubmitHandler = (e) => {
    const data = new FormData(e.target);
    const submittedParams = [...data.entries()];
    const query = {};
    submittedParams.forEach(param =>{
        let key = param[0];
        let value = param[1];
        query[key] = value;
    })
    const queryString = query.toString();
    currentParams.value = query;
    const url = `${route.path}?${queryString}`;
    // route.query;
    router.push({
        path: route.path,
        query : query,
    });
}
</script>

<style>
/* 검색창 css */

.admin-search-bar-div{
    width : 25rem;
    height : 5rem;
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.admin-search-bar{
    width: 25rem;
    height: 3rem;
    font-size: 1rem;
    color:  var(--dark-grey2); 
    outline-color:var(--light-grey2);
    border: 0;
    border-radius: 0.313rem;
    background-color: var(--light-grey2);
    background-image: url(/images/icon/search.svg);
    background-repeat: no-repeat;
    background-position: 4% 50%;
    text-align: center;
    padding:0;
    margin:0;
}

.admin-search-bar-box{
    width: 100%;
    position: relative;
}

.admin-search-button{
    position: absolute;
    top: 0.750rem;
    right: 0.750rem;
    background-color: var(--light-grey2);
    outline-color:var(--light-grey2);
    border:none;
    cursor: pointer;
    background-image: url(/images/icon/slide-bar.svg);
    height:1.500rem;
    width:1.500rem;
    background-repeat: no-repeat;


}

.admin-search-bar::placeholder{
    padding-left: 0.313rem;
    font-size: 1rem;
    color: var(--grey2);
    text-align : center;
}

.admin-search-bar-button{
    width: 3rem;
    height: 3rem;
    background-color: var(--light-grey2);
    border:0;
    cursor: pointer;
}

/* 모달창 css */

.filter-box{
    position:absolute;
    width: 44.813rem;
    height: 20rem;
    border: 0.063rem solid var(--black);
    display:flex;
    justify-content: center;
    align-items: center;
    background-color: var(--white);
    border: 0.063rem solid var(--grey1);
    box-shadow: 0 0.313rem 0.313rem rgba(0, 0, 0, 0.15);
    z-index: 3;
}

.filter-box div {
    width: 38.813rem;
    height: 1.563rem;
    margin: 1.250rem auto;
}

.filter-box span{
    display: inline-block;
    width: 5.8rem;
}

.filter-box input,select {
    text-align: center;
}

.filter-box input{
    border : 0.063rem solid var(--admin-grey);
    border-right:0; 
    border-top:0; 
    border-left:0; 
}

.keyword-input{
    width: 32.375rem;
    outline-color: var(--dark-grey2);
}

.category-select,.result-amount {
    width: 32.375rem;
}

.date-input{
    width:12.500rem;
}

.date-input + span {
    margin-left:1.250rem;
    width: 4.375rem;
}

.period-select{
    width:12.5rem;
    margin-left:1.250rem;
}

select{
    border: 0.063rem solid var(--admin-grey);
    border-right:0; 
    border-top:0; 
    border-left:0; 
}

.filter-button{
    width:5.750rem;
    height:2.375rem;
    border:none;
    border-radius: 0.313rem;
    color:var(--white);
    background-color: var(--admin-loyal-blue);
    cursor:pointer;
}

.search-button{
    display: flex;
    justify-content: flex-end;
}


</style>