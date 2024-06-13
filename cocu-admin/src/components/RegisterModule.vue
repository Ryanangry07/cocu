<template>
    <div class="loginBody">
        <div class="loginDiv">
            <div class="login-content">
                <h1 class="login-title">User Register</h1>
                <el-form :model="loginForm" label-width="100px"
                         :rules="rules" ref="loginForm">
                    <el-form-item label="Email" prop="email">
                        <el-input style="width: 200px" type="text" v-model="loginForm.email"
                                  autocomplete="off" size="small"></el-input>
                    </el-form-item>
                    <el-form-item label="Password" prop="password">
                        <el-input style="width: 200px" type="password" v-model="loginForm.password"
                                  show-password autocomplete="off" size="small" @keyup.enter.native="confirm"></el-input>
                    </el-form-item>
                    <el-link style="margin-left: 50%; margin-top: -35px" type="primary" @click="goToLogin">Go To Login</el-link>

                    <el-form-item>
<!--                        <el-button type="primary"  >Login</el-button>-->
                        <el-button type="success" @click="confirm" :disabled="confirm_disabled">Register</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "RegisterModule",
    data(){
        return{
            confirm_disabled:false,
            loginForm:{
                email:'',
                password:''
            },
            rules:{
                email: [
                    { required: true, message: 'Please enter your email account', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: 'Please enter your password', trigger: 'blur' }
                ],
            }
        }
    },
    methods:{

        confirm(){
            this.confirm_disabled=true;
            this.$refs.loginForm.validate((valid) => {
                if (valid) { //valid成功为true，失败为false
                    //去后台验证用户名密码
                    this.$axios.post(this.$httpUrl+'/user/register',this.loginForm).then(res=>{
                        console.log(res)
                        if(res.code==200){
                            //存储
                            //sessionStorage.setItem("curUser",JSON.stringify(res.data.user))

                            //console.log(res.data.menuList)
                            // commit("Method name", Data)
                            //this.$store.commit("setMenu",res.data.menuList)
                            //跳转到主页
                            //pop up
                            this.$message({
                                type: 'success',
                                message: 'Register successfully, please login'
                            })
                            this.$router.replace('/');

                        }else{
                            this.confirm_disabled=false;
                            alert('Email already exists！');
                            return false;
                        }
                    });
                } else {
                    this.confirm_disabled=false;
                    console.log('Authenticate failed');
                    return false;
                }
            });

        },
        goToLogin(){
            this.$router.replace('/');
        }
    }
}
</script>

<style scoped>

.loginBody {
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: #B3C0D1;
}
.loginDiv {
    position: absolute;
    top: 50%;
    left: 50%;
    margin-top: -200px;
    margin-left: -250px;
    width: 450px;
    height: 330px;
    background: #fff;
    border-radius: 5%;

}
.login-title {
    margin: 20px 0;
    text-align: center;
}
.login-content {
    width: 400px;
    height: 250px;
    position: absolute;
    top: 25px;
    left: 25px;
}

</style>