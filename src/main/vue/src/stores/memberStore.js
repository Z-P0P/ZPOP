import { defineStore } from "pinia";
import api from "@/api";
import adminApi from "@/api/admin";

export const useMemberStore = defineStore("member", {
  state: () => ({
    id: 0,
    nickname: "",
    profileImagePath: "",
    fame: 0,
  }),
  actions: {
    setInfo(memberInfo) {
      this.id = memberInfo.id;
      this.nickname = memberInfo.nickname;
      this.profileImagePath = memberInfo.profileImagePath;
      this.fame = memberInfo.fame;

      window.sessionStorage.setItem(
        "ZPOP_MEMBER_INFO",
        JSON.stringify({
          id: this.id,
          nickname: this.nickname,
          profileImagePath: this.profileImagePath,
          fame: this.fame,
        })
      );
    },
    clearInfo() {
      window.sessionStorage.removeItem("ZPOP_MEMBER_INFO");
      this.id = 0;
      this.nickname = null;
      this.profileImagePath = null;
      this.fame = 0;
    },
    async isAuthenticated() {
      try {
        const storedMemberInfo = JSON.parse(
          window.sessionStorage.getItem("ZPOP_MEMBER_INFO")
        );

        // sessionStorage에 사용자 정보가 있음.
        if (storedMemberInfo !== null) {
          this.setInfo(storedMemberInfo);
          return true;
        }

        // sessionStorage에 사용자 정보가 없음
        // 백엔드에 쿠키를 들고 요청. 에러날 경우 로그인해야 함
        const res = await api.auth.me();
        const data = await res.json();
        if (data.code === "AUTHENTICATED") {
          this.setInfo(data);
          return true;
        }

        return false;
      } catch (e) {
        //TODO: 예외
        console.log("erreros ! ", e);
        return false;
      }
    },
    async isAdmin() {
      try {
        const storedAdminInfo = JSON.parse(
          window.sessionStorage.getItem("ZPOP_ADMIN_INFO")
        );

        // sessionStorage에 사용자 정보가 있음.
        if (storedAdminInfo !== null) {
          this.setInfo(storedAdminInfo);
          return true;
        }

        const res = await adminApi.auth.me();
        const data = await res.json();

        if (data.code === "AUTHENTICATED") {
          this.setInfo(data);
          return true;
        }

        return false;
      } catch (e) {
        //TODO: 예외
        console.log("erreros ! ", e);
        return false;
      }
    },
    setAdmin(adminInfo) {
      this.id = adminInfo.id;
      this.nickname = adminInfo.nickname;

      window.sessionStorage.setItem(
        "ZPOP_ADMIN_INFO",
        JSON.stringify({
          id: this.id,
          nickname: this.nickname,
        })
      );
    },
  },
});
