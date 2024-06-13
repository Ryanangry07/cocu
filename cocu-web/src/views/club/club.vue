<template>
    <el-row :gutter="10" style="margin-top: 10px;">
        <el-col :span="4">
            <el-card class="box-card" :body-style="{ padding: '5px'}">
                <div @click="getClubList()" class="category-item text item" style="font-weight: 700;"> All Clubs</div>
                <div 
                :class="{'category-item-active' : item.id == activeType}"
                @click="typeBtn(item.id)" 
                v-for="item in clubTypeList" :key="item.id" 
                class="category-item text item"
                >
                    {{ item.name }}
                </div>
            </el-card>
        </el-col>
        <el-col :span="20">
            <el-card class="box-card">
                <div class="text item">
                    <el-row>
                        <el-col
                        @click="toDetails(item)"
                        :xs="12"
                        :sm="8"
                        :md="7"
                        v-for="item in clubList"
                        :key="item.clubId"
                        :offset="1">
                            <div style="margin-top: 15px;">
                                <el-card :body-style="{ padding: '5px'}" shadow="hover">
                                    <img class="image" :src="item.avatar"/>
                                    <div>
                                        <strong style="font-size: 16px">
                                            {{ item.name }}
                                            <!--  {{ item.classMajor }} | president:{{ item.classNumber }} -->
                                        </strong>
                                        <div class="bottom clearfix">
                                            <time class="time">
                                                <span>create time:</span> {{ item.createDate }}
                                            </time>
                                            <el-button class="button">check</el-button>
                                            <!-- <router-link :to="{path:'/club/detail', query:{id:1,name:'vue'}}" target="_blank">see details</router-link> -->
                                        </div>
                                    </div>
                                </el-card>
                            </div>
                        </el-col>
                    </el-row>
                </div>
                
            </el-card>
        </el-col>
    </el-row>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { getClubListAPI, getClubTypeListAPI, getClubListByTypeAPI } from '@/api/club';
// import img1 from '@/assets/1.png';
// import img2 from '@/assets/2.png';
// import img3 from '@/assets/3.jpg';
// import img4 from '@/assets/4.jpg';
import { useRouter } from 'vue-router';

const router = useRouter()
const clubTypeList = ref()

const clubList = ref()

const activeType = ref('')


const getClubTypeList = async()=>{
    let res = await getClubTypeListAPI()
    if(res && res.code == 200){
        clubTypeList.value = res.data
        getClubList()
    }
}

const getClubListByType = async(typeId:string)=>{
    let res = await getClubListByTypeAPI(typeId)
    if(res && res.code == 200){
        clubList.value = res.data
    }
}

const getClubList = async()=>{
    activeType.value = ''
    let res = await getClubListAPI()
    if(res && res.code == 200){
        clubList.value = res.data
    }
}
const typeBtn = (typeId:string)=>{
    activeType.value = typeId
    getClubListByType(typeId)
}

onMounted(()=>{
    getClubTypeList()
    // getClubListByType()
})

// go to club details page
const toDetails = (item:any) =>{
    router.push({path: `/clubDetails/${item.id}`})
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
    cursor: pointer;
}
.category-item-active{
    height: 50px;
    line-height: 50px;
    border-bottom: 1px solid #e4e7ed;
    color: #f3af28;
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