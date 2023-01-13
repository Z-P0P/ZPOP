<script>
import { setMapStoreSuffix } from 'pinia';
import { computed } from 'vue';

export default {
  props: ['detail'],
  setup(props){
    const getParticipantNum = computed(() => {
      
      console.log('호출됨');
      if (props.detail.participants === undefined) return;
      
      const length = props.detail.participants.length;
      return length;
    })
    // 참여자 수를 반환하는 함수를 만들어라
    return {getParticipantNum}
  }
};

</script>

<template>
    <section class="participant">
        <div class="participant__num">
            <span>참가자</span>
            <span id="participant-count">{{getParticipantNum}}</span>
            <span>/</span>
            <span>{{detail.maxMember}}</span>
            <span class="icon pointer icon-arrow-up"></span>
            <span class="icon pointer hidden icon-arrow-down"></span>
        </div>
        <ul class="participant__list">
            <li v-for="participant in detail.participants">
            <div class="participant__info">
                <img src="/images/icon/user-profile.svg">
                <span>{{participant.nickname}}</span>
            </div>
            </li>
        </ul>
    </section>
</template>

<style>

.main {
    margin: 2rem auto 4rem auto;
    
    max-width: 792px;
}

.main > section {
    margin: 1rem 20px;
}

.participant {
  display: flex;
  flex-direction: column;
  font-size: 16px;
}

.participant__num {
  display: flex;
}

.participant__num > span {
  margin-right: .5rem;
  font-weight: bold;
}

.participant__list {
  margin-top: 1rem;
  display: grid;
  grid-auto-rows: 46px;
  row-gap: 8px;
}

.participant__info {
  width: 220px;
  height: 46px;
  border-radius: 1.625rem;
  display: flex; 
  flex-grow: 0;
  cursor: pointer;
  align-items: center;
  padding-left: 6px;
  background-color: #f9f9f9;
}

.participant__info:hover{
  background-color: var(--main-color);
  color: var(--white);
}

.participant__info > img{
  width: 36px;
  height: 36px;
  margin-right: 15px;
}

@media (min-width:576px){
  .participant__list {
    display: grid;
    grid-auto-rows: 52px;
    grid-template-columns: repeat(2, 240px);
    row-gap: 16px;
    column-gap: 10px;
  }

  .participant {
    font-size: 18px;
    font-weight: 500;
  }
  
  .participant__info {
    width: 240px;
    height: 52px;
  }
  
  .participant__info > img{
    width: 42px;
    height: 42px;
  }

  .participant__num {
    font-size: 18px;
  }

  .main{
        margin: 5rem auto;
  }

  .main > section {
        margin: 2rem 20px;
  }
}

@media (min-width:792px){
  .participant__list {
    grid-template-columns: repeat(3, 240px);
  }
}

</style>