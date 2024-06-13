<template>
    <div class="register-container">
        <el-form
            ref="registerFormRef"
            style="max-width: 600px"
            :model="registerModel"
            status-icon
            :rules="rules"
            class="register-form"
            size="large"
        >
        
            <el-form-item>
                <div class="register-title">COCU Register</div>
            </el-form-item>
            <el-form-item prop="username">
                <el-input v-model="registerModel.username" placeholder="Please enter your username" />
            </el-form-item>
            <el-form-item prop="mobile">
                <el-input v-model="registerModel.mobile" placeholder="Please enter your mobile number"  />
            </el-form-item>
            <el-form-item prop="email">
                <el-input v-model="registerModel.email" placeholder="Please enter your email"  />
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" v-model="registerModel.password" placeholder="Please enter your password"  />
            </el-form-item>
            <el-row style="width: 100%">
                <el-col :span="12" :offset="0" >
                    <el-form-item prop="age">
                        <el-input v-model="registerModel.age" placeholder="Enter your age"></el-input>
                    </el-form-item>
                    
                </el-col>
                <el-col :span="12" :offset="0">
                    <el-form-item prop="gender" style="padding-left: 15%">
                        <el-radio-group v-model="registerModel.gender">
                            <el-radio :label="1">Male</el-radio>
                            <el-radio :label="0">Female</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item prop="emailCode">
                <el-row style="width: 100%">
                    <el-col :span="16" :offset="0">
                        <el-input v-model="registerModel.emailCode" placeholder="Enter your email verification code"></el-input>
                    </el-col>
                    <el-col :span="8" :offset="0">
                        <!-- <img class="image" src="#" /> -->
                        <div>
                            <span style="color: #409eff; cursor: pointer; padding-left: 25%" >Send again</span>
                        </div>
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item>
                <el-row style="width: 100%;">
                    <el-col :span="12" :offset="0" style="padding-right: 10px; padding-left: 0px">
                        <el-button class="btn" size="large" @click="registerBtn" type="primary">Register</el-button>
                    </el-col>
                    <el-col :span="12" :offset="0" style="padding-right: 0px; padding-left: 10px">
                        <el-button class="btn" size="large" @click="" plain type="danger">Reset</el-button>
                    </el-col>
                </el-row>
            </el-form-item>
            <div style="display: flex; font-size: 15px; color: #b1b3b8">
                <span>Already have an Account? &nbsp;</span>
                <span style="color: #409eff; cursor: pointer;" @click="goLogin">Login here!</span>
            </div>
        </el-form>
        
    </div>
</template>

<script setup lang="ts">
import { ElMessage, FormInstance } from 'element-plus';
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { registerAPI } from '@/api/auth';
const router = useRouter();
const registerFormRef = ref<FormInstance>();
const registerModel = reactive({
    username: '',
    mobile: '',
    email: '',
    password: '',
    age: '',
    gender: '',
    emailCode: ''
});
// form validation rule
const rules = reactive({
    username: [{required: true, message: 'Please enter username', trigger: 'blur'}],
    mobile: [{required: true, message: 'Please enter mobile number', trigger: 'blur'}],
    email: [{required: true, message: 'Please enter email', trigger: 'blur'}],
    password: [{required: true, message: 'Please enter password', trigger: 'blur'}],
    age: [{required: true, message: 'Please enter age', trigger: 'blur'}],
    gender: [{required: true, message: 'Please enter gender', trigger: 'blur'}],
    emailCode: [{required: true, message: 'Please enter email verification code', trigger: 'blur'}]
});

const goLogin = ()=>{
    router.push({name:"login"})
}

// submit registration
const registerBtn = ()=>{
    registerFormRef.value?.validate(async(valid)=>{
        if(valid){
            let res = await registerAPI(registerModel)
            if(res && res.code == 200){
                // register successfully
                // go to login
                ElMessage.success(res.msg);
                router.push({name: 'login'});
            }
        }
    })
}
</script>

<style scoped lang="scss">
.register-container {
    background-color: #FFF;
    height: 100%;
    background-image: url('@/assets/bg5.jpg');
    background-size: cover;
    display: flex;
    justify-content: center;
    align-items: center;

    .register-form {
        border-radius: 10px;
        background-color: #fff;
        height: 530px;
        width: 420px;
        padding: 20px 35px;

        .register-title {
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