<template>
    <img class="images" :src="avatar" />
    <div class="container">
        <div>
            <div> {{ title }} </div>
            <div> {{ summary }} </div>
            <div v-html="details"></div>
            <div> {{ createTime }} </div>
        </div>
    </div>
    <div style="display: flex; margin: 40px 0; justify-content: center; align-items: center;" >
        <el-button icon="Edit" type="primary" @click="joinActivity">Apply to Join Activity</el-button>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { userStore } from '@/store/user';
import { getActivityByIdAPI, applyJoinActivityAPI } from '@/api/activity';
import useInstance from '@/hooks/useInstance';
import { ElMessage } from 'element-plus';
const { global } = useInstance()

const route = useRoute()
const router = useRouter()
const store = userStore()

const activityId = ref('')
const title = ref('')
const summary = ref('')
const avatar = ref('')
const details = ref('')
const createTime = ref('')

// get activity details by id
const getById = async()=>{
    let res = await getActivityByIdAPI(route.params.id as string);
    if(res && res.code == 200){
        console.log(res)
        activityId.value = res.data.id;
        title.value = res.data.title;
        summary.value = res.data.summary;
        avatar.value = res.data.avatar;
        details.value = res.data.details;
        createTime.value = res.data.createDate;
    }
}
const joinActivity = async()=>{
    // confirm
    let joinConfirm = await global.$myConfirm('Are you sure to join this Activity?')
    if(joinConfirm){
        // check if login or not
        if(!store.userInfo.id){
            let confirm = await global.$myConfirm('You are not login, Please go to login')
            if(confirm){
                // go login
                router.push({name: 'login'})
            }
        }else{
            let res = await applyJoinActivityAPI({
                activityId: activityId.value,
                userId: store.userInfo.id
            })
            if(res && res.code == 200){
                ElMessage.success(res.msg)
            }
        }
    }
    
    
}

getById()
</script>

<style scoped>
.container {
    display: flex;
    margin-top: 30px;
}

.images {
    width: 30%;
    margin-left: 35%;
    margin-top: 10px;
    border-radius: 10px;
}

</style>