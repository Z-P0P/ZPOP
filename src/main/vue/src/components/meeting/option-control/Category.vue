<script setup>
import { storeToRefs } from "pinia";
import { useMeetingListStore } from "@/stores/meetingListStore";

// meetingListStore의 category를 selected라는 이름으로 수정하여 사용
const meetingListStore = useMeetingListStore();
const { category: selected } = storeToRefs(meetingListStore);

const categories = [
  "전체",
  "밥/카페",
  "운동",
  "스터디",
  "방탈출",
  "보드게임",
  "문화",
  "펫",
  "기타",
];

function changeSelected(idx) {
  selected.value = idx;
  meetingListStore.selectCategory(idx);
}
</script>

<template>
  <div class="categories">
    <nav class>
      <ul class="categories__items">
        <li
          v-for="(category, idx) in categories"
          :class="{ selected: selected === idx }"
          @click="changeSelected(idx)"
        >
          {{ category }}
        </li>
      </ul>
    </nav>
  </div>
</template>

<style scoped>
.categories {
  display: none;
  width: 100%;
  border-bottom: 3px solid var(--light-grey1);
}

@media (min-width: 1200px) {
  .categories {
    display: block;
  }
}

.categories > nav {
  padding: 0;
  padding: 24px 0;
  margin: 0 auto;
}

.categories__items {
  display: flex;
  width: 100%;
  justify-content: space-between;
}

.categories__items > li {
  color: var(--main-color);
  font-weight: bold;
  cursor: pointer;
  border-radius: 24px;
  height: 48px;
  font-size: 22px;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  padding: 0 30px;
}

.selected {
  background-color: var(--main-color);
  color: var(--white) !important;
}
</style>
