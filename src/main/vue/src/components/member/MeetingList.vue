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
  emit("rate", props.meeting.id)
}
</script>

<template>


  <div class="meeting">
    <router-link :to="`/meeting/${meeting.id}`">
      <div class="meeting__header">
        <div class="meeting__header-detail">
          <span class="meeting__category">{{ meeting.category}}</span>
          <span class="meeting__region add-deco-img-left deco-img-location">
            {{ meeting.region }}</span
          >
          <span class="meeting__date">{{ meeting.startedAt }}</span>
        </div>
        <h3>{{ meeting.title }}</h3>
      </div>
      <div class="meeting__body">
        <ul class="meeting__tags" v-if="!meeting.canRate">
          <li>#{{ meeting.age }}</li>
          <li>#{{ meeting.maxMember }}명</li>
          <li>#{{ meeting.genderCategory }}</li>
        </ul>
        <div class="meeting__body-detail">
          <button @click.prevent="onClick($event)"  class="status status__rate" v-if="meeting.canRate">평가하기</button>
          <div class="status status__on" v-else-if="!meeting.closed">모집중</div>
          <div class="status status__off" v-else-if="meeting.closed">모집완료</div>
        
          <div class="meeting__body-detail-more"  v-if="!meeting.canRate">
            <div class="meeting__views add-deco-img-left deco-img-eyes">
              {{ meeting.viewCount }}
            </div>
            <div
              class="meeting__comments add-deco-img-left deco-img-speech-bubble"
            >
              {{ meeting.commentCount }}
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