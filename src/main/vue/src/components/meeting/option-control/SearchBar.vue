<script setup>
import { defineEmits } from "vue";

const emit = defineEmits(["searchFromSearchBar"]);

/**
 * 사용자가 검색 input에 Enter 이벤트 발생시 실행 되는 함수
 * 부모 컴포넌트(@/views/meeting/List.vue)에게
 * searchFromSearchBar라는 이름으로 알리며, e.target.value 값을 넘긴다.
 */
function onEnterAndEmit(e) {
  emit("searchFromSearchBar", e.target.value);
}
</script>

<template>
  <div class="search-bar">
    <span class="icon icon-search">돋보기</span>
    <input
      @keyup.enter="onEnterAndEmit($event)"
      class="search-content"
      placeholder="원하는 집합을 찾아보세요!"
    />
  </div>
</template>

<style scoped>
.search-bar {
  display: none;
}

.search-bar .icon {
  margin-right: 0.625rem;
}

.search-bar input {
  width: 90%;
  border: none;
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  color: #333;
  font-size: 1.125rem;
}

.search-bar input::placeholder {
  color: #8b8b8b;
}

/* input창이 focus 되면 생기는 아웃라인 제거 */
.search-bar input:focus {
  outline: none;
}
@media (min-width: 768px) {
  .search-bar {
    width: 100%;
    min-width: 18.75rem;
    max-width: 39.375rem;
    height: 3.75rem;
    padding: 0.5rem 0.5rem 0.5rem 1.688rem;
    display: flex;
    align-items: center;
    border-radius: 62rem;
    border: 0.125rem solid #ebebeb;
    margin-left: auto;
    margin-right: auto;
    margin-top: 32px;
  }
}
</style>
