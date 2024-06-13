import { defineStore } from 'pinia'

export const testStore = defineStore('testStore', {
    state:()=> {
        return{
            count:0
        }
    },
    getters:{
        //get count
        getCount(state){
            return state.count
        }
    },
    actions:{
        setCount(count:number){
            this.count = count;
        }
    },
})


// import { defineStore } from 'pinia'

// export const testStore = defineStore('testStore', {
//     state: () => ({count: 0}),
//     getters: {
//         double: (state) => state.count * 2,
//     },
//     actions: {
//         increment(){
//             this.count++
//         },
//     },
// })