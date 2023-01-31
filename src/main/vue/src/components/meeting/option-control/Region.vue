<template>
  <select-box
    class="select-box--round region-category"
    @selectBoxClick="selectBoxClickedHandler"
    @optionClick="optionClickedHandler"
    :option="option"
  ></select-box>
  <div
    class="meeting-region"
    v-for="(item, index) in selectedRegions"
    :key="index"
    :data-region-id="item.id"
  >
    <span>{{ item.name }}</span>
    <img @click.stop="cancelRegionHandler($event,index)" class="meeting-active-option" src="../../../../public/images/icon/x-white.svg" alt="delete-region">
  </div>
</template>

<script setup>
import SelectBox from "../SelectBox.vue";
import { reactive } from "vue";
import { useMeetingListStore } from "@/stores/meetingListStore";

const store = useMeetingListStore();
const option = reactive({
  id: "region",
  isOpened: false,
  placeholder: "지역",
  items: [
    { id: 1, name: "서울" },
    { id: 2, name: "인천/경기" },
    { id: 3, name: "경남/부산/울산" },
    { id: 4, name: "충청/대전/세종" },
    { id: 5, name: "전라/광주" },
    { id: 6, name: "강원" },
    { id: 7, name: "경북/대구" },
    { id: 8, name: "제주" },
    { id: 9, name: "온라인" },
  ],
});

const selectedRegions = reactive([]);

const selectBoxClickedHandler = () => {
  option.isOpened = !option.isOpened;
};

const optionClickedHandler = (optionId, name, dataId) => {
  option.isOpened = !option.isOpened;
  const index = getIndexOfSelectedRegion(dataId);

  // 기존에 이미 선택한 지역이라면 제거
  if (index != -1) {
    selectedRegions.splice(index, 1);
  }

  // 지역을 이미 3개 선택한 상태라면, 가장 마지막 것을 제거하고, 선택된 것을 마지막에 추가
  else if (selectedRegions.length >= 3) {
    selectedRegions.pop();
    selectedRegions.push({ id: dataId, name: name });
  } else {
    selectedRegions.push({ id: dataId, name: name });
  }

  updateRegions();
};

const getIndexOfSelectedRegion = (dataId) => {
  console.log(selectedRegions);
  for (let i = 0; i < selectedRegions.length; i++) {
    console.log(selectedRegions[i].id, dataId);
    if (selectedRegions[i].id === dataId) {
      return i;
    }
  }
  return -1;
};

const cancelRegionHandler = (event, index) => {
  selectedRegions.splice(index, 1);
  updateRegions();
};

const updateRegions = () => {
  const params = [];
  selectedRegions.forEach((region) => params.push(region.id));
  store.setRegions(params);
};
</script>

<style scoped>
.region-category {
  width: 220px;
  display: none;
}

@media (min-width: 1200px) {
  .region-category {
    display: flex;
  }
}

.region-category {
  margin-top: 0;
}

.meeting-region {
  min-width: 128px;
  height: 52px;
  background-color: var(--main-color); /* true blue*/
  border-radius: 50px;
  color: white;
  font-weight: bold;
  font-size: 18px;
  display: inline-flex;
  padding: 0 24px;
  justify-content: space-between;
  align-items: center;
  display: none;
}

@media (min-width: 1200px) {
  .meeting-region {
    display: flex;
  }
}

.meeting-region > img {
  width: 24px;
  height: 24px;
  margin-left: 16px;
  cursor: pointer;
}
</style>
