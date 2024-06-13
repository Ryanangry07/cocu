<template>
    <div class="login-container">
        <el-form
            ref="forgetPwdFormRef"
            style="max-width: 600px"
            :model="forgetPwdModel"
            status-icon
            :rules="rules"
            class="login-form"
            size="large"
        >
        
            <el-form-item>
                <div class="login-title">COCU Forget Password</div>
            </el-form-item>
            <el-form-item prop="email">
                <el-input v-model="forgetPwdModel.email" placeholder="Please enter your email" type="email" autocomplete="off" />
            </el-form-item>
            <el-form-item prop="newPwd">
                <el-input v-model="forgetPwdModel.newPwd" placeholder="Please enter your new password" type="password" autocomplete="off" />
            </el-form-item>
            <el-form-item prop="emailCode">
                <el-row style="width: 100%">
                    <el-col :span="16" :offset="0">
                        <el-input 
                        v-model="forgetPwdModel.emailCode" 
                        placeholder="Email verification code" 
                        autocomplete="off" 
                        />
                    </el-col>
                    <el-col :span="8" :offset="0">
                        <el-button
                            class="btn"
                            size="large"
                            style="margin-left: 10px; width: 96%;"
                            @click="sendCode"
                            :disabled="sendCodeDisabled"
                            plain
                            type="danger"
                        >
                            {{ sendCodeDisabled ? `${countdown}s` : 'Send Code' }}
                        </el-button>
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item prop="imageCode">
                <el-row style="width: 100%">
                    <el-col :span="16" :offset="0">
                        <el-input v-model="forgetPwdModel.imageCode" placeholder="Verification code"></el-input>
                    </el-col>
                    <el-col :span="8" :offset="0">
                        <img @click="getImage" :src="image" class="image"/>
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item>
                <el-row style="width: 100%;">
                    <el-col :span="12" :offset="0" style="padding-right: 10px; padding-left: 0px">
                        <el-button class="btn" size="large" @click="forgetPwdBtn" type="primary">Change Password</el-button>
                    </el-col>
                    <el-col :span="12" :offset="0" style="padding-right: 0px; padding-left: 10px">
                        <el-button class="btn" size="large" @click="" plain type="danger">Reset</el-button>
                    </el-col>
                </el-row>
            </el-form-item>
            <div style="display: flex; font-size: 15px; color: #b1b3b8">
                <span>No Account? &nbsp;</span>
                <span style="color: #409eff; cursor: pointer;" @click="goRegister">Register here!</span>
            </div>
        </el-form>
        
    </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { getImageAPI, forgetPwdAPI,  sendTemplateMailAPI, checkEmailCodeAPI } from '@/api/auth';
import { ElMessage, FormInstance } from 'element-plus';

const forgetPwdFormRef = ref<FormInstance>()
const router = useRouter()
const forgetPwdModel = reactive({
    email: '',
    newPwd: '',
    imageCode: '',
    emailCode: ''
})
// form validation rule
const rules = reactive({
    email: [{required: true, message: 'Please enter email', trigger: 'blur'}],
    newPwd: [{required: true, message: 'Please enter password', trigger: 'blur'}],
    imageCode: [{required: true, message: 'Please enter image verification code', trigger: 'blur'}],
    emailCode: [{required: true, message: 'Please enter email verification code', trigger: 'blur'}]
});

const sendCodeDisabled = ref(false);
const countdown = ref(60);

const goRegister = ()=>{
    router.push({name:"register"})
}

// get verification image
const image = ref()
const getImage = async()=>{
    let res = await getImageAPI()
    if(res && res.code == 200){
        image.value = res.data
    }
}

// send code function with countdown
const sendCode = async () => {
    if (!forgetPwdModel.email) {
        ElMessage.error('Please enter your email');
        return;
    }

    // button count down
    sendCodeDisabled.value = true;
    countdown.value = 60;
    const timer = setInterval(() => {
        if (countdown.value > 0) {
            countdown.value--;
        } else {
            clearInterval(timer);
            sendCodeDisabled.value = false;
        }
    }, 1000);

    let res = await sendTemplateMailAPI(forgetPwdModel);
    if (res && res.code == 200) {
        ElMessage.success('Verification code sent successfully');
    } else {
        ElMessage.error('Failed to send verification code');
    }
};

// check email code function
// const checkEmailCode = async () => {
//     if (forgetPwdModel.emailCode) {
//         let res = await checkEmailCodeAPI(forgetPwdModel);
//         if (res && res.code != 200) {
//             ElMessage.error('Invalid email verification code');
//         }
//         if (res && res.code == 200) {
//             ElMessage.error('Bingo! Email verification code is correct!');
//         }
//     }
// };

// submit login
const forgetPwdBtn = ()=>{
    forgetPwdFormRef.value?.validate(async(valid)=>{
        if(valid){
            let res = await checkEmailCodeAPI(forgetPwdModel);
            if (res && res.code == 200) {
                let resetRes = await forgetPwdAPI(forgetPwdModel);
                if (resetRes && resetRes.code == 200) {
                    ElMessage.success('Change password succeed, please go to login!');
                    router.push({ path: '/login' });
                }
            } else {
                ElMessage.error('Invalid email verification code');
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
        height: 400px;
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
    }
}
</style>