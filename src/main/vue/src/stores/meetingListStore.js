import { defineStore } from "pinia";

/**
 * 모임 리스트 조회시 상태 관리
 */
export const useMeetingListStore = defineStore("meetingList", {
  state: () => ({
    isToggleOn: true,
    category: 0,
    regions: [],
  }),

  actions: {
    selectCategory(categoryId) {
      this.category = categoryId;
    },
    setRegions(selectedRegions){
      this.regions = selectedRegions;
    },
    changeToggle() {
      this.isToggleOn = !this.isToggleOn;
    },
  },
});
