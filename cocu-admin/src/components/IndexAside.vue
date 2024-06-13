<template>

    <el-menu
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
        default-active="/home"
        :collapse="isCollapse"
        :collapse-transition="false"
        style="height: 100%"
        router
    >
        <el-menu-item index="/home">
            <i class="el-icon-s-home"></i>
            <span slot="title">Home</span>
        </el-menu-item>

<!--        <el-menu-item index="/admin">
            <i class="el-icon-s-home"></i>
            <span slot="title">Admin</span>
        </el-menu-item>

        <el-menu-item index="/user">
            <i class="el-icon-s-home"></i>
            <span slot="title">User</span>
        </el-menu-item>-->

        <el-menu-item :index="'/' + menu.menuClick" v-for="(menu, i) in menuList" :key="i">
            <i :class="menu.menuIcon"></i>
            <span slot="title">{{menu.menuName}}</span>
        </el-menu-item>
    </el-menu>


<!--    <el-menu :default-openeds="['1', '3']" style="height: 100vh">
        <el-submenu index="1">
            <template slot="title"><i class="el-icon-message"></i>Navigator One</template>
            <el-menu-item-group>
                <template slot="title">Group 1</template>
                <el-menu-item index="1-1">Option 1</el-menu-item>
                <el-menu-item index="1-2">Option 2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="Group 2">
                <el-menu-item index="1-3">Option 3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
                <template slot="title">Option4</template>
                <el-menu-item index="1-4-1">Option 4-1</el-menu-item>
            </el-submenu>
        </el-submenu>
        <el-submenu index="2">
            <template slot="title"><i class="el-icon-menu"></i>Navigator Two</template>
            <el-menu-item-group>
                <template slot="title">Group 1</template>
                <el-menu-item index="2-1">Option 1</el-menu-item>
                <el-menu-item index="2-2">Option 2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="Group 2">
                <el-menu-item index="2-3">Option 3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="2-4">
                <template slot="title">Option 4</template>
                <el-menu-item index="2-4-1">Option 4-1</el-menu-item>
            </el-submenu>
        </el-submenu>
        <el-submenu index="3">
            <template slot="title"><i class="el-icon-setting"></i>Navigator Three</template>
            <el-menu-item-group>
                <template slot="title">Group 1</template>
                <el-menu-item index="3-1">Option 1</el-menu-item>
                <el-menu-item index="3-2">Option 2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="Group 2">
                <el-menu-item index="3-3">Option 3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="3-4">
                <template slot="title">Option 4</template>
                <el-menu-item index="3-4-1">Option 4-1</el-menu-item>
            </el-submenu>
        </el-submenu>
    </el-menu>-->
</template>

<script>
export default {
    name: "IndexAside",
    data(){
        return{
            //isCollapse: false

            /*menus: [
                {
                    menuClick: 'admin',
                    menuName: 'Admin',
                    menuIcon: 'el-icon-s-custom'
                },
                {
                    menuClick: 'user',
                    menuName: 'User',
                    menuIcon: 'el-icon-user-solid'
                }
            ]*/
        }
    },
    computed: {
        "menuList": {
            get(){
                return this.$store.state.menuList
            }
        }
    },
    props: {
        isCollapse:Boolean
    }
}
</script>

<style scoped>

</style>