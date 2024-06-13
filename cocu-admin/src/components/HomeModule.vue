<template>
    <div style="text-align: center;background-color: #f1f1f3;height: 100%;padding: 0px;margin: 0px;">
        <h1 style="font-size: 50px;">{{'Good Day！' + user.username}}</h1>
        <el-descriptions  title="Personal Profile" :column="2" size="40" border>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-s-custom"></i>
                    Email
                </template>
                {{user.email}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    Phone
                </template>
                {{user.mobile}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-location-outline"></i>
                    Gender
                </template>
                <el-tag
                        :type="user.gender == '1' ? 'primary' : 'danger'"
                        disable-transitions><i :class="user.gender==1?'el-icon-male':'el-icon-female'"></i> {{user.gender==1?"Male":"Female"}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-tickets"></i>
                    Role
                </template>
                <el-tag
                        type="success"
                        disable-transitions>{{user.roleId==0?"Super Admin":(user.roleId==1?"Admin":"User")}}</el-tag>

            </el-descriptions-item>
        </el-descriptions>

        <DateUtils></DateUtils>
    </div>
</template>

<script>
    import DateUtils from "../utils/DateUtils.vue";
    export default {
        name: "HomeModule",
        components: {DateUtils},
        data() {

            return {
                user:{}
            }
        },
        computed:{

        },
        methods:{
            init(){
                this.user = JSON.parse(sessionStorage.getItem('curUser'))
            }
        },
        created(){
            this.init()
        }
    }
</script>

<style scoped>
    .el-descriptions{
        width:90%;

        margin: 0 auto;
        text-align: center;
    }
</style>