<template>
    <el-container class="container">
      <el-header class="header">
        <logo></logo>
        <tabs></tabs>
        <dropdown></dropdown>
      </el-header>
      <el-main class="main">
        <router-view/>
      </el-main>
      <el-footer clss="footer">Footer</el-footer>
    </el-container>
</template>

<script setup lang="ts">
import logo from './logo.vue'
import tabs from './tabs.vue'
import dropdown from './dropdown.vue'

</script>

<style scoped lang="scss">
.container {
    height: 100%;
    .header {
        background-color: #1077cc;
        display: flex;
        align-items: center;
        padding: 0px;
    }
    .main{
        padding: 0px 150px;
    }
}
</style>