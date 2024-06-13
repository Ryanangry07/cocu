import { defineStore } from 'pinia'

export const tabStore = defineStore('tabStore', {
    state:()=> {
        return{
            tab: 'home',
            profileTab: '/profile/myProfile'
        }
    },
    actions: {
        setTab(tab:string){
            this.tab = tab;
        },
        setProfileTab(profileTab:string){
            this.profileTab = profileTab
        }
    },
    persist: {
        enabled: true,
        strategies: [
            {storage: sessionStorage, paths: ['tab', 'profileTab']},
        ],
    }
})
