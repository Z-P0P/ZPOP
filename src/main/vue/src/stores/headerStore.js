import { defineStore } from 'pinia';

export const useHeaderStore = defineStore('header', {
    state: () => ({
        isProfileOpened:false,
        isLoginOpened:false,
        isNotificationOpened: false,
    }),

    actions: {
        changeProfileState(){
            this.isProfileOpened = !this.isProfileOpened;
        },
        changeLoginState(){
            console.log(this.isLoginOpened);
            this.isLoginOpened = !this.isLoginOpened;
            console.log(this.isLoginOpened);
        },
        changeNotificationState(){
            this.isNotificationOpened = !this.isNotificationOpened
        }
    }
})