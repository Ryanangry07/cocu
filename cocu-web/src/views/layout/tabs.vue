<template>
    <div class="tabs">
        <div 
        @click="tabClick(item)"
        class="item" :class="{'active-item':item.value == activeName}" v-for="(item) in tabs">
            {{ item.title }}
        </div>
    </div>
</template>

<script setup lang="ts">
import {computed, ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { tabStore } from '@/store/tab';

const store = tabStore()
const router = useRouter()
const route = useRoute()
const has = ref(['home','club','activity','notification','profile','news'])

const activeName = computed(()=>{
    let res = has.value.some(item => item === route.name);
    if(res){
        return route.name;
    }else{
        return store.tab
    }
})
const tabs = ref([
    {
        title: 'Home',
        value: 'home'
    },
    {
        title: 'Club',
        value: 'club'
    },
    {
        title: 'Activity',
        value: 'activity'
    },
    {
        title: 'News',
        value: 'news'
    },
    {
        title: 'Notification',
        value: 'notification'
    },
    {
        title: 'Profile',
        value: 'profile'
    }
]);

// Function to handle tab click
const tabClick = (item: any) => {
    // activeName.value = item.value;
    // Save the current tab
    store.setTab(item.value);
    console.log(item.value)
    if(item.value === 'profile'){
        router.push({ path: store.profileTab });
    }else{
        router.push({ name: item.value });
    }
};

</script>

<style scoped lang=scss>
.tabs{
    display: flex;
    flex-grow: 1;
    font-weight: 600;
    margin-left: 50px;
    .item{
        padding: 0px 20px;
        text-align: center;
        color: #FFF;
        cursor: pointer;
        height: 60px;
        line-height: 60px;
    }
    .active-item{
        padding: 0px 30px;
        text-align: center;
        color: #F3AF28;
        cursor: pointer;
        height: 60px;
        line-height: 60px;
        border-bottom: 3px solid #F3AF28;
    }
}

</style>