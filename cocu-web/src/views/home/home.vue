<template>
    <!-- club recommendations -->
    <el-row style="margin-top: 20px;">
        <el-col :span="24">
            <el-card class="box-card" :body-style="{ height: '250px', padding: '10px'}">
                <template #header>
                <div class="card-header">
                    <span class="more-title">Recommend Student Clubs</span>
                    <el-button @click="tabClick('club')" class="button" text>
                        See More
                        <el-icon>
                            <ArrowRight></ArrowRight>
                        </el-icon>
                    </el-button>
                </div>
                </template>
                <div class="text item">
                    <swipper1></swipper1>
                </div>
            </el-card>
        </el-col>
    </el-row>


    <el-row :gutter="20" style="margin-top: 30px;">
        <!-- activity recommendations -->
        <el-col :span="8" :offset="0">
            <el-card class="box-card" :body-style="{ height: '250px', padding: '10px'}">
                <template #header>
                    <div class="card-header">
                        <span class="more-title">Recommend Activities</span>
                        <el-button @click="tabClick('activity')" class="button" text>
                            See More
                            <el-icon>
                                <ArrowRight></ArrowRight>
                            </el-icon>
                        </el-button>
                    </div>
                </template>
                <div class="text item">
                    <carousel></carousel>
                </div>
            </el-card>
        </el-col>
        <!-- Notifications -->
        <el-col :span="16" :offset="0">
            <el-card class="box-card" :body-style="{ height: '250px', padding: '10px'}">
                <template #header>
                    <div class="card-header">
                        <span class="more-title">Notifications</span>
                        <el-button @click="tabClick('notification')" class="button" text>
                            See More
                            <el-icon>
                                <ArrowRight></ArrowRight>
                            </el-icon>
                        </el-button>
                    </div>
                </template>
                <div>
                    <div @click="toNotificationDetails(item)" class="notice" v-for="(item, index) in noticeList">
                        <div class="title">{{ index + 1 + ". " +item.title}}</div>
                        <div class="date">{{item.createTime}}</div>
                    </div>
                </div>
            </el-card>
        
        </el-col>
    </el-row>
    <!-- latest activities -->
    <el-row style="margin-top: 30px;">
        <el-col :span="24" :offset="0">
            <el-card class="box-card" :body-style="{ height: '250px', padding: '10px'}">
                <template #header>
                    <div class="card-header">
                        <span class="more-title">Latest Activities</span>
                        <el-button @click="tabClick('activity')" class="button" text>
                            See More
                            <el-icon>
                                <ArrowRight></ArrowRight>
                            </el-icon>
                        </el-button>
                    </div>
                </template>
                <div>
                    <div @click="toActivityDetails(item)" class="notice" v-for="(item, index) in latestActivitiesList">
                        <div class="title">{{ index + 1 + ". " +item.title + " | " + item.summary + " | "}}</div>
                        <div class="date">{{item.startDate}} - {{item.endDate}}</div>
                    </div>
                </div>
            </el-card>
        
        </el-col>
    </el-row>
    <!-- top activities -->
    <el-row style="margin-top: 30px;">
        <el-col :span="24" :offset="0">
            <el-card class="box-card" :body-style="{ height: '250px', padding: '10px'}">
                <template #header>
                    <div class="card-header">
                        <span class="more-title">Top Activities</span>
                        <el-button @click="tabClick('activity')" class="button" text>
                            See More
                            <el-icon>
                                <ArrowRight></ArrowRight>
                            </el-icon>
                        </el-button>
                    </div>
                </template>
                <div>
                    <div @click="toActivityDetails(item)" class="notice" v-for="(item, index) in topActivitiesList">
                        <div class="title">{{ index + 1 + ". " +item.title + " | " + item.summary + " | "}}</div>
                        <div class="date">{{item.startDate}} - {{item.endDate}}</div>
                    </div>
                </div>
            </el-card>
        
        </el-col>
    </el-row>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import swipper1 from './swipper1.vue';
import carousel from './carousel.vue';
import { getLatestActivitiesAPI, getTopActivitiesAPI } from '@/api/home';
import { useRouter } from 'vue-router';

const router = useRouter()
const latestActivitiesList = ref()
const topActivitiesList = ref()

const noticeList = ref([
    {
        title: 'Diabolo latest notifications',
        createTime: '2024-05-01'
    },
    {
        title: 'Diabolo latest notifications',
        createTime: '2024-05-01'
    },
    {
        title: 'Diabolo latest notifications',
        createTime: '2024-05-01'
    },
    {
        title: 'Diabolo latest notifications',
        createTime: '2024-05-01'
    },
    {
        title: 'Diabolo latest notifications',
        createTime: '2024-05-01'
    },
    {
        title: 'Diabolo latest notifications',
        createTime: '2024-05-01'
    },
    {
        title: 'Diabolo latest notifications',
        createTime: '2024-05-01'
    },
    {
        title: 'Diabolo latest notifications',
        createTime: '2024-05-01'
    }
])

// Function to handle tab click
const tabClick = (activeName: any) => {
    const item = ref('');
    item.value = activeName;
    // Save the current tab
    if(item.value === 'activity'){
        router.push({ path: 'activity' });

    }
    if(item.value === 'club'){
        router.push({ path: 'club' });
    }
    if(item.value == 'notification'){
        router.push({ path: 'notification' });
    }
};

// get latest activities
const getLatestActivities = async()=> {
    let res = await getLatestActivitiesAPI()
    console.log(res)
    if(res && res.code == 200){
        latestActivitiesList.value = res.data
    }
}

// get top activities
const getTopActivities = async()=> {
    let res = await getTopActivitiesAPI()
    console.log(res)
    if(res && res.code == 200){
        topActivitiesList.value = res.data
    }
}

onMounted(()=>{
    getLatestActivities()
    getTopActivities()
})

// go to club details page
// const toClubDetails = (item:any) =>{
//     router.push({path: `/clubDetails/${item.id}`})
// }
// go to activity details page
const toActivityDetails = (item:any) =>{
    router.push({path: `/activityDetails/${item.id}`})
}
// go to notification details page
const toNotificationDetails = (item:any) =>{
    router.push({path: `/notificationDetails/${item.id}`})
}



</script>

<style scoped lang="scss">

.card-header{
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.more-acitivity{
    display: flex;
    align-items: center;
    cursor: pointer;
}
.text{
    font-size: 14px;
}
.more-title{
    color: #F3AF28;
    font-weight: 600;
}
.notice{
    cursor: pointer;
    display: flex;
    height: 30px;
    line-height: 30px;
    .title{
        font-size: 14px;
        font-weight: 600;
        color: #666;
    }
    .date{
        font-size: 14px;
        margin-left: 20px;
    }
}
</style>