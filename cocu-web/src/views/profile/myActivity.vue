<template>
    <el-row :gutter="10">
        <el-col :span="24">
            <div class="grid-content ep-bg-purple-light" />
            <el-card class="box-card">
                <template #header>
                    <div class="card-header">
                        <span>My Activity</span>
                    </div>
                </template>
                <div v-if="uStore.userInfo.email" class="text item">
                    <el-row>
                        <el-col
                        :xs="12"
                        :sm="8"
                        :md="7"
                        v-for="item in tableData"
                        :key="item['id']"
                        :offset="1">
                            <div style="margin-top: 15px;">
                                <el-card :body-style="{ padding: '5px'}" shadow="hover">
                                    <img class="image" :src="item['avatar']"/>
                                    <div>
                                        <strong style="font-size: 16px">
                                            {{ item['title'] }} {{ item['summary'] }}
                                        </strong>
                                        <div class="bottom clearfix">
                                            <time class="time">
                                                <span>create time:</span> {{ item['createDate'] }}
                                            </time>
                                            <el-button @click="toDetails(item)" class="button">check</el-button>
                                            <!-- <router-link :to="{path:'/club/detail', query:{id:1,name:'vue'}}" target="_blank">see details</router-link> -->
                                        </div>
                                    </div>
                                </el-card>
                            </div>
                        </el-col>
                    </el-row>
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
        </el-col>


        <el-pagination style="margin-top: 15px;"
        @size-change="sizeChange"
        @current-change="currentChange"
        :current-page.sync="param.currentPage"
        :page-sizes="[20,40,80,100]"
        :page-size="param.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="param.total"
        background>
        </el-pagination>

    </el-row>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { userStore } from '@/store/user';
import { useRouter } from 'vue-router';
import { getMyActivityAPI } from '@/api/activity';
const uStore = userStore()
const router = useRouter()

const tableData = ref([])

const param = reactive({
    currentPage:1,
    pageSize:10,
    total:10
})

const sizeChange = (size:number)=>{
    console.log(size)
}

const currentChange = (page:number)=>{
    console.log(page)
}

const getMyActivity = async()=>{
    let res = await getMyActivityAPI(uStore.userInfo.id)
    if(res && res.code == 200){
        tableData.value = res.data
    }
}


// login click event
const loginBtn = ()=> {
  router.push({name: 'login'})
}

// go to activity details page
const toDetails = (item:any) =>{
    router.push({path: `/activityDetails/${item.id}`})
}

onMounted(()=>{
    if(uStore.userInfo.id){
        getMyActivity()
    }
})
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
    cursor: pointer;
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