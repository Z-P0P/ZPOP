import { reactive } from 'vue';
import { defineStore } from 'pinia';

export const useMemberStore = defineStore('member', {
    state: () => {
        let info = reactive({
            id : 0,
            nickname : null,
            profileImagePath : null,
            fame : 0,
        })
        const storedMemberInfo = JSON.parse(window.sessionStorage.getItem('ZPOP_MEMBER_INFO'));

        if (storedMemberInfo != null){
            info = storedMemberInfo;
        }
        else{
            fetch('/api/login/me')
                .then(res=>res.json())
                .then(data=>{
                    if (data.code === "AUTHENTICATED"){
                        info.nickname = data.nickname;
                        info.profileImagePath = data.profileImagePath;
                        info.fame = data.fame;
                    }
                    window.sessionStorage.setItem('ZPOP_MEMBER_INFO', 
                    JSON.stringify(info));
                })
        }

        return{
            memberInfo : info,
        }
    },

    actions: {
        setInfo(memberInfo){
            this.memberInfo = memberInfo;
        },
        clearInfo(){
            window.sessionStorage.removeItem('ZPOP_MEMBER_INFO');
            this.memberInfo.nickname = null;
            this.memberInfo.profileImagePath = null;
        }
    },
})