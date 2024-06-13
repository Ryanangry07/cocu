<template>
    <view @click="toDetails(item)" class="news-item" v-for="(item, index) in activityList" :key="index">
        <view v-if="item['avatar']">
            <el-image class="images" :src="item['avatar']" />
        </view>
        <view class="news-intro">
            <view class="news-title">
                <text>{{ item['title'] }}</text>
            </view>
            <view class="news-info">
                <view class="news-time">
                    <text>{{ item['createDate'] }}</text>
                </view>
                <view class="news-type">
                    <text>{{ item['views'] }} views</text>
                </view>
            </view>
        </view>
    </view>

    <el-pagination style="margin-top: 15px;"
    @size-change="sizeChange"
    @current-change="currentChange"
    v-model:current-page="pageParam.pageNum"
    v-model:page-size="pageParam.pageSize"
    :page-sizes="[3,5,10,20,40,80,100]"
    layout="total, sizes, prev, pager, next, jumper"
    :total="pageParam.total">
    
    </el-pagination>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { activityStore } from '@/store/activity';
import { useRouter } from 'vue-router';
const router = useRouter()
const aStore = activityStore()

const activityList = computed(()=>{
    //aStore.getActivityListPage()
    return aStore.activityList
})

const pageParam = computed(()=>{
    return aStore.pageParam
})

const sizeChange = (size:number)=>{
    aStore.pageParam.pageSize = size;
    aStore.getActivityListPage()
}

const currentChange = (page:number)=>{
    aStore.pageParam.pageNum = page
    aStore.getActivityListPage()
}

onMounted(()=>{
    aStore.getActivityListPage()
})

// go to activity details page
const toDetails = (item:any) =>{
    router.push({path: `/activityDetails/${item.id}`})
}

</script>

<style scoped lang="scss">

.news-item{
    display: flex;
    height: 110px;
    padding: 30px 0px;
    border-bottom: 1px solid #e4e7ed;
    cursor: pointer;

    .images{
        height: 100px;
        width: 200px;
        border-radius: 10px;
    }
    .news-intro{
        margin-left: 15px;
        .news-info {
            display: flex;
            font-size: 28rpx;
            color: #999;
            margin-left: 16rpx;
            .news-time {
                text-align: left;
            }
            .news-type {
                text-align: right;
                flex: 2;
            }
        }
    }
}


</style>