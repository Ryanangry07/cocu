import { defineStore } from 'pinia'
import { getActivityListPageAPI } from '@/api/activity'

// first param is the unique id of this store
export const activityStore = defineStore('activityStore', {
    state:()=> {
        return{
            activityList:[],
            // page param
            pageParam:{
                pageNum:1,
                pageSize:10,
                total:0
            }
        }
    },
    actions:{
        async getActivityListPage(){
            let res = await getActivityListPageAPI(this.pageParam)
            console.log('pageParam: ' + this.pageParam)
            if(res && res.code == 200){
                this.activityList = res.data
                this.pageParam.total = res.total
            }
        }
    },
    persist: {
        enabled: true,
        strategies: [
            {storage: sessionStorage, paths: ['activityList', 'pageParam']},
        ],
    }
})
