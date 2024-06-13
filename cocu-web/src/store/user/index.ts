import { defineStore } from 'pinia'

export const userStore = defineStore('userStore', {
    state:()=> {
        return{
            userInfo: {
                id: '',
                username: '',
                email: '',
                mobile: '',
                gender: '',
                age: '',
                avatar: '',
                roleId: ''
            }
            
        }
    },
    actions: {
        setUserInfo(user:any){
            this.userInfo = user;
        }
    },
    persist: {
        enabled: true,
        strategies: [
            {storage: sessionStorage, paths: ['userInfo']}
        ]
    }
})
