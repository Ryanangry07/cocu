<template>
  <div v-if="username"
  style="color: #FFF; font-size: 16px;"> @{{ username }}</div>
  <div
  v-else
  style="color: #FFF; font-size: 16px; cursor: pointer"
  @click="loginBtn"
  >
  Login / Register
  </div>

  <el-dropdown style="padding: 0px 20px; cursor: pointer;">
    <span class="el-dropdown-link">
      <img :src="userAvatar" class="userImg" />
      <!-- <img :src="uStore.userInfo.avatar ? uStore.userInfo.avatar : require('@/assets/user/default-user.jpg')" /> -->
      <!-- <img class="userImg" src="@/assets/user/default-user.jpg" /> -->
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item @click="goAdmin">Admin Center</el-dropdown-item>
        <el-dropdown-item @click="logout">Logout</el-dropdown-item>
        <!-- <el-dropdown-item>Action 3</el-dropdown-item>
        <el-dropdown-item disabled>Action 4</el-dropdown-item>
        <el-dropdown-item divided>Action 5</el-dropdown-item> -->
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { useRouter } from 'vue-router';
import { userStore } from '@/store/user';
import useInstance from '@/hooks/useInstance'
import defaultUserImage from '@/assets/user/default-user.jpg';
import { ElMessage } from 'element-plus';

const uStore = userStore()
//get router
const router = useRouter()
const { global } = useInstance()

// user avatar
const userAvatar = computed(() => {
  return uStore.userInfo.avatar || defaultUserImage;
});

// go to Admin Center
// Method to navigate to the Admin Center running on port 8082
const goAdmin = () => {
    const token = localStorage.getItem('jwtToken');
    if (token) {
        window.location.href = `http://localhost:8080?token=${token}`;
    } else {
        ElMessage.error('You must be logged in to access the admin center');
    }
};
// logout
const logout = async()=> {
  let confirm = await global.$myConfirm("Are you sure to logout?");
  if(confirm){
    sessionStorage.clear()
    router.push({name: 'login'})
  }
}
// login click event
const loginBtn = ()=> {
  router.push({name: 'login'})
}

// user info
const username = computed(()=>{
  return uStore.userInfo.username
})

</script>

<style scoped lang="scss">
.userImg{
    height: 38px;
    width: 38px;
    border-radius: 50%;
}
</style>