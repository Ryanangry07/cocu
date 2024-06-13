<template>
    <el-row :gutter="10" style="margin-top: 10px;">
        <el-col :span="4">
            <el-card class="box-card" :body-style="{ padding: '5px'}">
                <div
                @click="btnClick(item.id)"
                v-for="item in categoryList"
                :class="{'active-item' : item.id == activeName}"
                :key="item.id"
                class="category-item text item">
                    <router-link :to="item.id"> {{ item.name }} </router-link>
                </div>
            </el-card>
        </el-col>
        <el-col :span="20">
            <router-view></router-view>
        </el-col>
    </el-row>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue';
import { tabStore } from '@/store/tab';

const store = tabStore()

const activeName = computed(()=>{
    return store.profileTab
})

const categoryList = ref([
    {
        name: 'My Profile',
        id: '/profile/myProfile'
    },
    {
        name: 'My Club',
        id: '/profile/myClub'
    },
    {
        name: 'My Activity',
        id: '/profile/myActivity'
    }
])

const btnClick = (id:string)=>{
    // activeName.value = id
    store.profileTab = id;
}
</script>

<style scoped lang="scss">
.time{
    font-size: 15px;
    color: #999;
}
.category-item{
    height: 50px;
    line-height: 50px;
    border-bottom: 1px solid #e4e7ed;
    text-align: center;
    a{
        height: 50px;
        line-height: 50px;
        color: #999;
        text-align: center;
        cursor: pointer;
        text-decoration: none;
    }
}
.active-item{
    a{
        color: #f3af28;
    }
}
.bottom{
    margin-top: 5px;
    line-height: 15px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.image{
    width: 100%;
    height: 180px; //r
    margin-bottom: 5px;
    display: block;
}
</style>