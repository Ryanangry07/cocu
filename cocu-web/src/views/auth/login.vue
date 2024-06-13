<template>
    <div class="login-container">
        <el-form
            ref="loginFormRef"
            style="max-width: 600px"
            :model="loginModel"
            status-icon
            :rules="rules"
            class="login-form"
            size="large"
        >
        
            <el-form-item>
                <div class="login-title">COCU Login</div>
            </el-form-item>
            <el-form-item prop="email">
                <el-input v-model="loginModel.email" placeholder="Please enter your email" type="email" autocomplete="off" />
            </el-form-item>
            <el-form-item prop="password">
                <el-input v-model="loginModel.password" placeholder="Please enter your password" type="password" autocomplete="off" />
            </el-form-item>
            
            <el-form-item prop="imageCode">
                <el-row style="width: 100%">
                    <el-col :span="16" :offset="0">
                        <el-input v-model="loginModel.imageCode" placeholder="Verification code"></el-input>
                    </el-col>
                    <el-col :span="8" :offset="0">
                        <img @click="getImage" :src="image" class="image"/>
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item>
                <el-row style="width: 100%;">
                    <el-col :span="12" :offset="0" style="padding-right: 10px; padding-left: 0px">
                        <el-button class="btn" size="large" @click="loginBtn" type="primary">Login</el-button>
                    </el-col>
                    <el-col :span="12" :offset="0" style="padding-right: 0px; padding-left: 10px">
                        <el-button class="btn" size="large" @click="" plain type="danger">Reset</el-button>
                    </el-col>
                </el-row>
            </el-form-item>
            <div class="options-container">
                <div>
                    <span>No Account? &nbsp;</span>
                    <span style="color: #409eff; cursor: pointer;" @click="goRegister">Register here!</span>
                </div>
                <div class='forget-password' @click="goForgetPwd">Forget Password?</div>
            </div>
            
        </el-form>
        
    </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { getImageAPI, loginAPI } from '@/api/auth';
import { ElMessage, FormInstance } from 'element-plus';
import { tabStore } from '@/store/tab';
import { userStore } from '@/store/user';

const uStore = userStore()
const tStore = tabStore()
const loginFormRef = ref<FormInstance>()
const router = useRouter()
const loginModel = reactive({
    email: '',
    password: '',
    imageCode: ''
})
// form validation rule
const rules = reactive({
    email: [{required: true, message: 'Please enter email', trigger: 'blur'}],
    password: [{required: true, message: 'Please enter password', trigger: 'blur'}],
    imageCode: [{required: true, message: 'Please enter image verification code', trigger: 'blur'}]
});

const goRegister = ()=>{
    router.push({name:"register"})
}

const goForgetPwd = ()=>{
    router.push({name:"forgetPwd"})
}

// get verification image
const image = ref()
const getImage = async()=>{
    let res = await getImageAPI()
    if(res && res.code == 200){
        image.value = res.data
    }
}

// submit login
const loginBtn = ()=>{
    loginFormRef.value?.validate(async(valid)=>{
        if(valid){
            let res = await loginAPI(loginModel)
            if(res && res.code == 200){
                // login successfully, store user info
                let user = {
                    id: '',
                    username: '',
                    email: '',
                    mobile: '',
                    gender: '',
                    age: '',
                    avatar: '',
                    roleId: ''
                }
                // assign res to user object
                Object.assign(user, res.data.user);

                // store the JWT token
                const token = res.data.token;
                localStorage.setItem('jwtToken', token);

                // persist to userStore
                uStore.setUserInfo(user);
                tStore.setTab('home');
                tStore.setProfileTab('/profile/myProfile')
                ElMessage.success(res.msg);
                router.push({path: '/'})
            } else {
                ElMessage.error(res.msg || 'Login failed');
            }
        }
    })
}

onMounted(()=>{
    getImage()
})
</script>

<style scoped lang="scss">
.login-container {
    background-color: #FFF;
    height: 100%;
    background-image: url('@/assets/bg5.jpg');
    background-size: cover;
    display: flex;
    justify-content: center;
    align-items: center;

    .login-form {
        border-radius: 10px;
        background-color: #fff;
        height: 340px;
        width: 420px;
        padding: 20px 35px;

        .login-title {
            color: #606266;
            width: 100%;
            font-size: 24px;
            font-weight: 800;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .image {
            margin-left: 10px;
            height: 40px;
            width: 96%;
            display: flex;
            justify-content: center;
            align-items: center;
            cursor: pointer;
        }
        .btn {
            width: 100%;
        }
        .options-container {
            display: flex;
            justify-content: space-between;
            font-size: 15px;
            color: #b1b3b8;
            margin-bottom: 10px; /* Optional: Add some space at the bottom */

            .forget-password {
                color: #409eff;
                cursor: pointer;
            }
        }
    }
}
</style>