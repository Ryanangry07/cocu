<template>
  <div style="display: flex; line-height: 60px">
      <div style="font-size: 20px; line-height: 60px; cursor: pointer; text-align: center; width: 40px; margin-left: -10px"
           @click="collapse">
          <i :class="icon"></i>
      </div>
      <div style="flex: 1; text-align: center; font-size: 34px">
          <span>Welcome to COCU management system</span>
      </div>
      <el-dropdown>
          <div style="cursor: pointer;font-size: 17px">
              <img class="me-header-picture el-dropdown-link" :src="user.avatar"/>
              <span style="margin-left: 8px">{{user.username}}</span><i class="el-icon-arrow-down" style="margin-left: 10px"></i>
          </div>
          <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="toCOCU">Back to COCU</el-dropdown-item>
              <el-dropdown-item @click.native="toProfile">Profile</el-dropdown-item>
              <el-dropdown-item @click.native="logout">Logout</el-dropdown-item>
          </el-dropdown-menu>
      </el-dropdown>
  </div>
</template>

<script>
export default {
    name: "IndexHeader",
    data(){
        return{
            user: JSON.parse(sessionStorage.getItem('curUser'))
        }
    },
    methods: {
        toCOCU(){
            window.location.href = 'http://localhost:8088/';
        },
        toProfile(){
            console.log('to profile...')
            this.$router.push('/profile')
        },
        logout(){
            console.log('logout...')
            this.$confirm('Are you sure you log out', 'warning',{
                confirmButtonText: 'Confirm',
                type: 'warning',
                center: true,
                //showCancelButton: false,    //不显示取消按钮
                //showClose: false,           //显示右上角的x
                //closeOnClickModal: false    //点击空白处关闭
            }).then(() => {
                //pop up
                this.$message({
                    type: 'success',
                    message: 'Logout successfully'
                })
                this.$router.push('/')
                sessionStorage.clear()
                localStorage.clear()
            }).catch(() => {
                //pop up
                this.$message({
                    type: 'info',
                    message: 'Logout canceled'
                })
            })
        },
        collapse(){
            // 提交到父组件 IndexContainer, 执行@doCollapse事件
            // 整个流程: header 点击图标 -> emit到父组件 -> 父组件传递值到 aside组件 -> aside组件 值改变
            this.$emit('doCollapse')
        },
    },
    props: {
        icon:String
    },
    created(){
        this.$router.push('/home');
    }
}
</script>

<style scoped>
.me-header-picture {
    width: 36px;
    height: 36px;
    border: 1px solid #ddd;
    border-radius: 50%;
    vertical-align: middle;
    background-color: #5fb878;
}

</style>