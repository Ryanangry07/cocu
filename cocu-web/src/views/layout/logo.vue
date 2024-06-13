<template>
    <div class="logo">
        <img :src="logo"  alt="cocu">
        <div class="title">COCU</div>
    </div>
</template>

<script setup lang="ts">

import logo from '@/assets/logo2.png'
</script>

<style scoped lang="scss">
.logo{
    display: flex;
    align-items: center;
    padding-left: 40px;
    img{
        width: 40px;
        height: 40px;
    }
    .title{
        color: #FFF;
        font-size: 24px;
        font-weight: 600;
        font-family: FZYaoti;
        padding-left: 10px;
    }
}
</style>