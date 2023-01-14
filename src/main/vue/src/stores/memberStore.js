import { defineStore } from 'pinia';

export const useMemberStore = defineStore('member', {
    state: () => ({
        memberInfo : {}
    }),

    actions: {
        setInfo(memberInfo){
            this.memberInfo = memberInfo;
        },
        
    },

    // 새로고침해도 로그인 정보가 남아있을 수 있도록 sessionStorage에 저장
    // 기본 js function 말고, pinia-plugin-persist 라이브러리 사용
    // 사용하기 위해서는 App.vue에서 import 및 pinia에 설정해주어야 함
    persist:{
        enabled: true,
    }
})