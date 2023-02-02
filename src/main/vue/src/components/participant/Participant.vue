<template>
  <div class="participant" @click="onClickAndEmit">
    <img
      class="participant__image"
      :src="
        (userDetail.profileImagePath != '' && userDetail.profileImagePath != null)
          ? `/image/profile/${userDetail.profileImagePath}`
          : '../../../images/icon/user-profile-grey.svg'
      "
    />
    <span>{{ props.userDetail.nickname }}</span>
  </div>
</template>

<script setup>
const props = defineProps({
  userDetail: Object,
});

const emit = defineEmits(["onClickParticipant"]);

// 참여자 id를 담아 부모에게 emit(전달)
function onClickAndEmit(e) {
  emit("onClickParticipant", props.userDetail.participantId);
}
</script>

<style scoped>
.participant {
  width: 220px;
  height: 46px;
  border-radius: 1.625rem;
  display: flex;
  flex-grow: 0;
  cursor: pointer;
  align-items: center;
  padding-left: 6px;
}

@media (min-width: 576px) {
  .participant {
    width: 240px;
    height: 52px;
  }
}

.participant:hover {
  background-color: var(--main-color);
  color: var(--white);
}

.participant__image {
  width: 36px;
  height: 36px;
  margin-right: 15px;
  border-radius: 18px;
}

@media (min-width: 576px) {
  .participant__image {
    width: 42px;
    height: 42px;
    border-radius: 21px;
  }
}
</style>
