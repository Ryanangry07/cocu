<template>
    <view @click="toDetails(item)" class="news-item" v-for="(item, index) in newList" :key="index">
        <view v-if="item.image">
            <el-image class="images" :src="item.image" />
        </view>
        <view class="news-intro">
            <view class="news-title">
                <text>{{ item.title }}</text>
            </view>
            <view class="news-info">
                <view class="news-time">
                    <text>{{ item.createTime }}</text>
                </view>
                <view class="news-type">
                    <text>{{ item.views }} views</text>
                </view>
            </view>
        </view>
    </view>

    <el-pagination style="margin-top: 15px;"
    @size-change="sizeChange"
    @current-change="currentChange"
    :current-page.sync="param.currentPage"
    :page-sizes="[10,20,40,80,100]"
    :page-size="param.pageSize"
    layout="total, sizes, prev, pager, next, jumper"
    :total="param.total">
    
    </el-pagination>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import img1 from '@/assets/1.png';
import img2 from '@/assets/2.png';
import img3 from '@/assets/3.jpg';
import img4 from '@/assets/4.jpg';

const router = useRouter()

const newList = ref([
    {
        createTime: '2021-05-01',
        image: img1,
        views: '300',
        title: 'This is the title of this activity, you can edit your details now! It will be better if you join us, you definitely will not regret this time! Come and join us now!!'
    },
    {
        createTime: '2021-05-01',
        image: img2,
        views: '2004',
        title: 'This is the title of this activity, you can edit your details now! It will be better if you join us, you definitely will not regret this time! Come and join us now!!'
    },
    {
        createTime: '2021-05-01',
        image: img3,
        views: '1234',
        title: 'This is the title of this activity, you can edit your details now! It will be better if you join us, you definitely will not regret this time! Come and join us now!!'
    },
    {
        createTime: '2021-05-01',
        image: img4,
        views: '499',
        title: 'This is the title of this activity, you can edit your details now! It will be better if you join us, you definitely will not regret this time! Come and join us now!!'
    }
]);

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


// go to notification details page
const toDetails = (item:any) =>{
    router.push({path: `/notificationDetails/${item.id}`})
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