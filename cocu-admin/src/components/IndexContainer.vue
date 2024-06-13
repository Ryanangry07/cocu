<template>
    <el-container style="height: 100%; border: 1px solid #eee">
        <el-aside :width="asideWidth" style="min-height: 100vh; margin-left: -1px; background-color: rgb(238, 241, 246)">
            <IndexAside :isCollapse="isCollapse"></IndexAside>
        </el-aside>

        <el-container style="height: 100%">
            <el-header style="text-align: right; font-size: 12px; height: 100%">
                <IndexHeader @doCollapse="doCollapse" :icon="icon"></IndexHeader>
            </el-header>

            <el-main style="height: 100%">
<!--                <IndexMain></IndexMain>-->
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
import IndexAside from "@/components/IndexAside.vue";
import IndexHeader from "@/components/IndexHeader.vue";
//import IndexMain from "@/components/IndexMain.vue";

export default {
    name: "IndexContainer",
    components: {
        //IndexMain,
        IndexHeader,
        IndexAside
    },
    data(){
        return{
            isCollapse: false,
            asideWidth: '250px',
            icon: 'el-icon-s-fold'
        }
    },
    methods:{
        // 收到 header组件 传来的信息，改变 aside组件 的值
        doCollapse(){
            this.isCollapse = !this.isCollapse;
            this.asideWidth = this.isCollapse ? '64px' : '250px';
            this.icon = this.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold';
            console.log('do collapse')
        }
    }
}
</script>

<style scoped>
    .el-header {
        background-color: #B3C0D1;
        color: #333;
        line-height: 60px;
    }

    .el-main {
        padding: 5px;
    }

    .el-aside {
        color: #333;
    }
</style>