import { defineStore } from "pinia";

/**
 * 로그인 모달 상태관리 
 */
export const useLoginModalStore = defineStore("loginModal", {
  state: () => ({
    isOn: false,
  }),
  actions: {
    handleModal() {
      this.isOn = !this.isOn;
    },
    close() {
      this.isOn = false;
    },
    show() {
      this.isOn = true;
    }
  },
});
