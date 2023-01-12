import { defineStore } from "pinia";

/**
 * 모임 리스트 조회시 상태 관리
 */
export const useMeetingListStore = defineStore("meetingList", {
  state: () => ({
    isToggleOn: false,
    category: 0,
    regions: [],
  }),

  actions: {
    selectCategory(categoryId) {
      this.category = categoryId;
    },
    selectRegion(regioinId) {},
    revmoeRegion(regioinId) {},
    changeToggle() {
      this.isToggleOn = !this.isToggleOn;
    },
  },
});
