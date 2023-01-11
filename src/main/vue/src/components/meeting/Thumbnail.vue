<script>
export default {
  name: "MeetingThumbnail",
  props: {
    meeting: Object,
  },
};
</script>

<template>
  <div class="meeting-wrap">
    <a :href="meeting.id">
      <div class="meeting__header">
        <div class="meeting__header-detail"></div>
        <h3>{{ meeting.title }}</h3>
      </div>
      <div class="meeting__body">
        <ul class="meeting__tags">
          <li>#{{ meeting.ageRange }}</li>
          <li>#{{ meeting.maxMember }}명</li>
          <li>#{{ meeting.genderCategory }}</li>
        </ul>
        <div class="meeting__body-detail">
          <div class="status status__on" v-if="!meeting.isClosed">모집중</div>
          <div class="status status__off" v-else>모집완료</div>
          <div class="meeting__body-detail-more">
            <div class="meeting__views">{{ meeting.viewCount }}</div>
            <div class="meeting__comments">{{ meeting.commentCount }}</div>
          </div>
        </div>
      </div>
    </a>
  </div>
</template>

<style scoped></style>
