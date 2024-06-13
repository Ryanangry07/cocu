<template>
    <el-form style="width: 70%; margin-left: 10%; margin-top: 30px" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="Email" prop="email" disabled="disabled">
            <el-input v-model="ruleForm.email" disabled></el-input>
        </el-form-item>

        <el-form-item label="Avatar" prop="avatar">
            <el-upload
                    class="avatar-uploader"
                    name="uploadAvatar"
                    action="http://localhost:8082/upload"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload">
                <img v-if="ruleForm.avatar" :src="ruleForm.avatar" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </el-form-item>
        <el-form-item label="Username" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
        </el-form-item>
        <el-form-item label="Mobile" prop="mobile">
            <el-input v-model="ruleForm.mobile"></el-input>
        </el-form-item>
        <el-form-item label="Email" prop="email">
            <el-input v-model="ruleForm.email"></el-input>
        </el-form-item>
        <el-form-item label="Age" prop="age">
            <el-input v-model="ruleForm.age"></el-input>
        </el-form-item>
        <el-form-item label="Role" prop="role">
            <el-input v-model="ruleForm.role" disabled></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">Save</el-button>
        </el-form-item>
    </el-form>
</template>

<script>

export default {
    name: "UserProfile",
    data() {
        return {
            user: JSON.parse(sessionStorage.getItem('curUser')),
            ruleForm: {
                id: '',
                email: '',
                username: '',
                mobile: '',
                avatar: '',
                gender: '',
                age: '',
                roleId: '2'
            },
            rules: {
                email: [
                    { required: true, message: 'Please enter your username', trigger: 'blur' }
                ],
                username: [
                    { required: true, message: 'Please enter your username', trigger: 'blur' }
                ],
                mobile: [
                    { required: true, message: 'Please enter your mobile number', trigger: 'blur' }
                ],
                age: [
                    { required: true, message: 'Please enter your age', trigger: 'blur' }
                ],
                gender: [
                    { required: true, message: 'Please select gender', trigger: 'change' }
                ],
                aboutMe: [
                    { required: true, message: 'Please enter your about me', trigger: 'blur' }
                ]
            }
        };
    },
    methods: {
        loadUser(){
            this.ruleForm.id = this.user.id
            this.ruleForm.email = this.user.email
            this.ruleForm.username = this.user.username
            this.ruleForm.mobile = this.user.mobile
            this.ruleForm.avatar = this.user.avatar
            this.ruleForm.gender = this.user.gender
            this.ruleForm.age = this.user.age
            let rid = this.user.roleId
            this.ruleForm.roleId = rid
            this.ruleForm.role = rid == '0' ? 'System Admin'
                                : (rid == '1' ? 'Club Admin'
                                : (rid == '2' ? 'Club Member'
                                : 'Student'))
        },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    //去后台验证用户名密码
                    this.$axios.post(this.$httpUrl+'/user/update',this.ruleForm).then(res=>{
                        console.log(res)
                        if(res.code==200){
                            //存储
                            sessionStorage.setItem("curUser",JSON.stringify(res.data))
                            //pop up
                            this.$message({
                                type: 'success',
                                message: 'User profile update successfully!'
                            })
                            //router.go(0);
                            //window.location.reload();
                            location.reload();
                            //console.log('refresh')
                        }else{
                            //pop up
                            this.$message({
                                type: 'success',
                                message: 'User profile update failed!'
                            })
                            return false;
                        }
                    });

                } else {
                    return false;
                }
            });
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isPNG = file.type === 'image/png';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG && !isPNG) {
                this.$message.error('You can only upload JPG or PNG image!');
            }
            if (!isLt2M) {
                this.$message.error('The size of image cannot exceed 2MB!');
            }
            //if less than 2MB and correct image format, return true
            return isLt2M && (isJPG || isPNG);
        },
        handleAvatarSuccess(res) {
            // Handle the avatar upload success, update club type's avatar with the new URL
            let that = this
            if(res.code == 200){
                //this.imageUrl = URL.createObjectURL(file.raw);
                that.ruleForm.avatar = res.data.url
            }else{
                this.$message.error(`club type avatar upload failed:${res.message}`)
            }
            console.log(that.ruleForm.avatar)
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
    },
    //created
    beforeMount() {
        this.loadUser();
    }
}
</script>

<style scoped>

</style>