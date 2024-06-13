<template>
    <el-card class="box-card">
        <template #header>
            <div class="card-header">
                <span>My Profile</span>
            </div>
        </template>
        <div v-if="uStore.userInfo.email">

            <div class="text item">
                <span style=""> Avatar:</span>
                <img class="userImg" :src="uStore.userInfo.avatar" />
                <!-- <image src="http://localhost:8088/user/default.jpg" /> -->
            </div>
            <div class="text item">
                <span>Username:</span>
                <span style="padding: 10px;">{{  uStore.userInfo.username }}</span>
            </div>
            
            <div class="text item">
                <span>Email:</span>
                <span style="padding: 10px;">{{  uStore.userInfo.email }}</span>
            </div>
            <div class="text item">
                <span>Gender:</span>
                <span v-if="uStore.userInfo.gender == '1'" style="padding: 10px">Male</span>
                <span v-if="uStore.userInfo.gender == '0'" style="padding: 10px">Female</span>
            </div>
            <div class="text item">
                <span>Age:</span>
                <span style="padding: 10px;">{{  uStore.userInfo.age }}</span>
            </div>
            <div class="text item">
                <span>Role:</span>
                <span v-if="uStore.userInfo.roleId == '0'" style="padding: 10px">System Admin</span>
                <span v-if="uStore.userInfo.roleId == '1'" style="padding: 10px">Club Admin</span>
                <span v-if="uStore.userInfo.roleId == '2'" style="padding: 10px">Club Member</span>
                <span v-else style="padding: 10px">Student</span>
            </div>
        </div>
        <div v-else>
            <div class="text item">
                <span style=" font-size: 16px;">Please login first!</span>
                <a
                    style=" font-size: 16px; cursor: pointer; color: blue"
                    @click="loginBtn"
                >
                Go to login
                </a>
            </div>
        </div>
        
    </el-card>
</template>

<script setup lang="ts">

import { userStore } from '@/store/user';
import { useRouter } from 'vue-router';
const uStore = userStore()
const router = useRouter()
// login click event
const loginBtn = ()=> {
  router.push({name: 'login'})
}

</script>

<style scoped>

.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}

.userImg{
    height: 38px;
    width: 38px;
    margin-left: 20px; 
    border-radius: 50%;
}

</style>