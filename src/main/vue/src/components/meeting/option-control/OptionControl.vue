<script setup>
import { useMeetingListStore } from "@/stores/meetingListStore";
import Category from "./Category.vue";
import SoftToggle from "@/components/button/SoftToggle.vue";
import Region from "@/components/meeting/option-control/Region.vue";

const meetingListStore = useMeetingListStore();

function handleToggle(e) {
  // 버블링 방지. 없으면 함수 두번 호출됨
  if (e.target.tagName !== "INPUT" && e.target.type !== "checkbox") {
    return;
  }
  meetingListStore.changeToggle();
}
</script>

<template>
  <div class="option-control-wrap">
    <section class="option-control">
      <Category @selectCategory="categoryId" />
      <div class="options-detail">
        <Region />
        <div class="option-control__activate">
          <span>모집 중만 보기</span>
          <SoftToggle
            @click="handleToggle($event)"
            :isOn="meetingListStore.isToggleOn"
          />
        </div>
      </div>
    </section>
  </div>
</template>

<style scoped>
@media (min-width: 768px) {
  .option-control-wrap {
    max-width: 1200px;
  }
  .option-control {
    /* max-width: 1200px; */
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: flex-end;
  }

  .option-control {
    justify-content: center;
    flex-direction: column;
  }
}

.option-control__activate {
  display: flex;
  align-items: center;
  margin-left: auto;
  margin-top: 18px;
  margin-bottom: 18px;
}

.option-control__activate > span {
  margin-right: 1rem;
  font-weight: var(--bold);
  color: var(--main-color);
}

@media (min-width: 768px) {
  .option-control__activate > span {
    font-size: 18px;
    color: var(--grey4);
  }
  .option-control__activate {
    margin-top: 24px;
    margin-bottom: 24px;
  }
}

.options-detail {
  width: 100%;
  display: flex;
  justify-content: end;
  align-items: center;
  column-gap: 10px;
}

@media (min-width: 1200px) {
  .options-detail {
    justify-content: flex-start;
  }
}
</style>
