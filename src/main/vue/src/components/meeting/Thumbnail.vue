<script>
export default {
  name: "MeetingThumbnail",
  props: {
    meeting: Object,
  },
};
</script>

<template>
  <div class="meeting">
    <router-link :to="`/meeting/${meeting.id}`">
      <div class="meeting__header">
        <div class="meeting__header-detail">
          <span class="meeting__category">{{ meeting.category }}</span>
          <span class="meeting__region add-deco-img-left deco-img-location">
            {{ meeting.region }}</span
          >
          <span class="meeting__date">{{ meeting.startedAt }}</span>
        </div>
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
h3 ,span, li{
  font-weight: 500;
}
.meeting__body-detail-more {
  font-weight: 500;
}
.meeting > a {
  display: flex;
  flex-direction: column;
  width: 350px;
  min-height: 188px;
  border-radius: 15px;
  background-color: var(--white);
  border: 2px solid var(--light-grey1);
  padding: 20px;
  justify-content: space-between;
}

@media (min-width: 768px) {
  .meeting > a {
    width: 383px;
    height: 264px;
    border-width: 3px;
    padding: 32px;
  }
}

.meeting__header {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  font-size: 14px;
}

@media (min-width: 768px) {
  .meeting__header {
    font-size: 1rem;
  }
}

.meeting__header-detail {
  display: flex;
  gap: 8px;
  font-size: 14px;
}

@media (min-width: 768px) {
  .meeting__header-detail {
    display: flex;
    gap: auto;
    font-size: 1rem;
  }
}
.meeting__category {
  border-right: 2px solid var(--main-color);
  padding-right: 0.5rem;
}

.meeting__region,
.meeting__date {
  color: var(--grey4);
}

.meeting__region::before {
  width: 12px;
  height: 12px;
  flex: 0 0 12px;
  margin-right: 4px;
}

.meeting__title {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

@media (min-width: 768px) {
  .meeting__title {
    font-size: 20px;
  }
}

.meeting__tags {
  display: flex;
  color: var(--grey1);
  font-size: 14px;
  margin-bottom: 14px;
}

@media (min-width: 768px) {
  .meeting__tags {
    font-size: 1rem;
  }
}

.meeting__tags > li {
  margin-right: 4px;
}

.meeting__body-detail {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 1rem;
}

.status {
  font-size: 14px;
  font-weight: bold;
  padding: 4px 0.5rem;
  border-radius: 5px;
}

@media (min-width: 768px) {
  .status {
    font-size: 1rem;
  }
}

.status__on {
  color: var(--main-color);
  background-color: var(--white);
  border: 1px solid var(--main-color);
}

.status__off {
  color: var(--white);
  background-color: var(--main-color);
}

.meeting__body-detail-more {
  display: flex;
  gap: 8px;
  font-size: 14px;
}

@media (min-width: 768px) {
  .meeting__body-detail-more {
    display: flex;
    gap: auto;
    font-size: 1rem;
  }
}

.meeting__views::before,
.meeting__comments::before {
  width: 24px;
  height: 24px;
  flex: 0 0 24px;
  margin-right: 4px;
}
</style>
