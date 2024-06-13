<template>
    <swiper
      :modules="modules"
      :initial-slide="1"
      :slides-per-view="3"
      :centeredSlides="true"
      :space-between="50"
      :autoplay="{ disableOnInteraction: false }"
      loop
      class="mySwiper"
      @swiper="setSwiperRef"
    >
      <swiper-slide 
      @click="toDetails(item)" 
      style="cursor: pointer;"
      class="teacher_li" 
      v-for="(item, index) in list" :key="index">
        <el-image
          style="height: 250px; width: 100%; object-fit: cover"
          :src="item['avatar']" />
      </swiper-slide>
    </swiper>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { getRecommendClubsAPI } from '@/api/home';
// import img1 from '@/assets/1.png';
// import img2 from '@/assets/2.png';
// import img3 from '@/assets/3.jpg';
// import img4 from '@/assets/4.jpg';

// Import Swiper Vue.js components
import { Swiper, SwiperSlide } from 'swiper/vue';

// Import Swiper styles
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import 'swiper/css/scrollbar';

// import Swiper core and required modules
import { Navigation, Virtual, Pagination, Autoplay } from 'swiper/modules';
import { useRouter } from 'vue-router';

const router = useRouter()
// get the modules we want to use
const modules = [Navigation, Virtual, Pagination, Autoplay];
let swiperRef = null;
const setSwiperRef = (swiper: any) => {
  swiperRef = swiper;
};
// const list = ref([
//   {url: img1},
//   {url: img2},
//   {url: img3},
//   {url: img4},
// ]);

const list = ref();

// get recommendation clubs
const getRecommendClubs = async()=> {
  let res = await getRecommendClubsAPI()
  console.log(res)
  if(res && res.code == 200){
    list.value = res.data
  }
}

onMounted(()=>{
  getRecommendClubs()
})

// go to club details page
const toDetails = (item:any) =>{
    router.push({path: `/clubDetails/${item.id}`})
}

</script>

<style scoped>
</style>