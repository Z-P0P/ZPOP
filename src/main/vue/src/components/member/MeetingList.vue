<script setup>
  import { defineProps ,defineEmits} from 'vue';

const props = defineProps({
  meeting: Object
})
const emit = defineEmits([
  'rate'
]);

function onClick(e) {
  console.log("clicked from child");
  console.log(props.meeting.id);
  emit("rate", props.meeting.meetingId)
}
</script>

<template>


  <div class="meeting">
    <router-link :to="`/meeting/${ props.meeting.meetingId}`">
      <div class="meeting__header">
        <div class="meeting__header-detail">
          <span class="meeting__category">{{ props.meeting.category}}</span>
          <span class="meeting__region add-deco-img-left deco-img-location">
            {{ props.meeting.region }}</span
          >
          <span class="meeting__date">{{ props.meeting.startedAt }}</span>
        </div>
        <h3>{{ props.meeting.title }}</h3>
      </div>
      <div class="meeting__body">
        <ul class="meeting__tags" v-if="!props.meeting.canRate">
          <li>#{{ props.meeting.age }}</li>
          <li>#{{ props.meeting.maxMember }}명</li>
          <li>#{{ props.meeting.genderCategory }}</li>
        </ul>
        <div class="meeting__body-detail">
          <button class="status status__rated" v-if="props.meeting.evaluated">평가완료</button>
          <button @click.prevent="onClick($event)" class="status status__rate" v-else-if="props.meeting.canRate">평가하기</button>
        
          <div class="status status__on" v-else-if="!props.meeting.closed">모집중</div>
          <div class="status status__off" v-else-if="props.meeting.closed">모집완료</div>
        
          <div class="meeting__body-detail-more"  v-if="!props.meeting.canRate">
            <div class="meeting__views add-deco-img-left deco-img-eyes">
              {{ props.meeting.viewCount }}
            </div>
            <div
              class="meeting__comments add-deco-img-left deco-img-speech-bubble"
            >
              {{ props.meeting.commentCount }}
            </div>
          </div>
        </div>
      </div>
    </router-link>
  </div>

</template>

<style scoped>
 @import url(../../assets/css/member/meeting-list.css);
</style>