<script>
import { setMapStoreSuffix } from 'pinia';

export default {
  props: ['detail'],
  setup(props){
      const getParticipantNum = ()=> {

        if (props.detail.participants === undefined) return;
        
        const participantNum = props.detail.participants.length;
        return participantNum; 
      };
      return {getParticipantNum};
  }
}

</script>

<template>
    <section class="participant">
        <div class="participant__num">
            <span>참가자</span>
            <span id="participant-count">{{getParticipantNum()}}</span>
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

  @import url(@/assets/css/meeting/component/participant.css);
  
</style>