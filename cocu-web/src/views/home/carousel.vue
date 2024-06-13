<template>
    <el-carousel>
        <el-carousel-item @click="toDetails(item)" style="cursor: pointer;" v-for="item in carouselList" :key="item">
            <el-image 
            style="height: 250px; width: 100%; object-fit: cover;"
            :src="item['avatar']" />
        </el-carousel-item>
    </el-carousel>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { getRecommendActivitiesAPI } from '@/api/home';
// import img1 from '@/assets/1.png';
// import img2 from '@/assets/2.png';
// import img3 from '@/assets/3.jpg';
// import img4 from '@/assets/4.jpg';
import { useRouter } from 'vue-router';

const router = useRouter()
const carouselList = ref()


// get recommendation clubs
const getRecommendActivities = async()=> {
    let res = await getRecommendActivitiesAPI()
    console.log(res)
    if(res && res.code == 200){
        carouselList.value = res.data
    }
}

onMounted(()=>{
    getRecommendActivities()
})

// go to activity details page
const toDetails = (item:any) =>{
    router.push({path: `/activityDetails/${item.id}`})
}

</script>



<style scoped>

</style>