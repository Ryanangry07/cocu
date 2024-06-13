<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="Please enter to search" suffix-icon="el-icon-search"
                      style="width: 300px" @keyup.enter.native="loadList"></el-input>
            <el-select v-model="gender" filterable placeholder="Select Gender" style="margin-left: 9px; width: 150px">
                <el-option
                    v-for="item in genders"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
            </el-select>
            <el-button type="primary" style="margin-left: 9px" @click="loadList">Search</el-button>
            <el-button type="success" @click="resetQuery">Reset</el-button>
            <el-button type="success" @click="create">Creat New</el-button>
        </div>
        <el-table :data="tableData" :header-cell-style="{background: '#f2f5fc', color: '#555'}" border>
            <el-table-column prop="id" label="ID" width="50">
            </el-table-column>
            <el-table-column prop="avatar" label="Avatar" width="125">
                <template slot-scope="scope">
                    <img class="me-header-picture el-dropdown-link" :src="scope.row.avatar"/>
                </template>
            </el-table-column>
            <el-table-column prop="email" label="Email" width="200">
            </el-table-column>
            <el-table-column prop="username" label="Username" width="150">
            </el-table-column>
            <el-table-column prop="age" label="Age" width="70">
            </el-table-column>
            <el-table-column prop="gender" label="Gender" width="100">
                <template slot-scope="scope">
                    <el-tag
                        :type="scope.row.gender === 1 ? 'primary' : 'success'"
                        disable-transitions>{{scope.row.gender === 1 ? 'Male' : 'Female'}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="roleId" label="Role" width="130">
                <template slot-scope="scope">
                    <el-tag
                        :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'primary' : (scope.row.roleId === 2 ? 'success' : 'info'))"
                        disable-transitions>{{scope.row.roleId === 0 ? 'System Admin' :
                        (scope.row.roleId === 1 ? 'Club Admin' :
                            (scope.row.roleId === 2 ? 'Club Member' : 'Visitor'))}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="mobile" label="Mobile" width="180">
            </el-table-column>
<!--            <el-table-column prop="isValid" label="Valid" width="100">
            </el-table-column>-->
            <el-table-column prop="operate" label="Operation">
                <template slot-scope="scope">
                    <el-button type="success" @click="edit(scope.row)">Edit</el-button>
                    <el-popconfirm @confirm="del(scope.row.id)" title="Are you sure to delete this?"
                                   style="margin-left: 7px;">
                        <el-button slot="reference" type="danger">Delete</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 20, 30]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            style="margin-left: 25%; margin-top: 10px">
        </el-pagination>
        <el-dialog
            title="Operate User Information"
            :visible.sync="centerDialogVisible"
            width="30%"
            center>
            <el-form ref="form" :rules="rules" :model="form" label-width="120px" style="padding-right: 20px">

                <el-form-item label="Avatar" prop="avatar">
                    <el-upload
                        class="avatar-uploader"
                        action="http://localhost:8082/upload"
                        name="uploadAvatar"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload">
                        <img v-if="form.avatar" :src="form.avatar" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item label="Email" prop="email">
                    <el-col :span="20">
                        <el-input v-model="form.email"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="Username" prop="username">
                    <el-col :span="20">
                        <el-input v-model="form.username"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="Password" prop="password">
                    <el-col :span="20">
                        <el-input v-model="form.password"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="Age" prop="age">
                    <el-col :span="20">
                        <el-input v-model="form.age"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="Gender" prop="gender">
                    <el-radio-group v-model="form.gender">
                        <el-radio label="1">Male</el-radio>
                        <el-radio label="0">Female</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="Mobile" prop="mobile">
                    <el-col :span="20">
                        <el-input v-model="form.mobile"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="Role" prop="roleId">
                    <el-select v-model="form.roleId" placeholder="please select the role">
                        <el-option label="System Admin" value="0"></el-option>
                        <el-option label="Club Admin" value="1"></el-option>
                        <el-option label="Club Member" value="2"></el-option>
                        <el-option label="Visitor" value="3"></el-option>
                    </el-select>
                </el-form-item>
<!--                <el-form-item label="Valid" prop="isValid">
                    <el-radio-group v-model="form.isValid">
                        <el-radio label="Y" value="Y">Yes</el-radio>
                        <el-radio label="N" value="N">No</el-radio>
                    </el-radio-group>
                </el-form-item>-->
                <!-- true/false -->
                <!--
                <el-form-item label="Instant delivery" prop="delivery">
                    <el-switch v-model="form.isValid"></el-switch>
                </el-form-item>-->
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">Cancel</el-button>
                <el-button type="primary" @click="save">Confirm</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "UserManage",
    data() {
        // rules for age field
        let checkAge = (rule, value, callback) => {
            if(value > 150){
                callback(new Error('Age too big'));
            }else{
                callback();
            }
        };
        // rules for account no field
        let checkDuplicate = (rule, value, callback) => {
            if(this.form.id){
                return callback();
            }
            this.$axios.get(this.$httpUrl + '/user/findByEmail?email=' + this.form.email).then(res => {
                if(res.code != 200){    // user not exists
                    callback();
                }else{
                    callback(new Error('Account already exists'));
                }
            })
        };
        return {
            id: '',
            tableData: [],
            pageSize: 5,
            pageNum: 1,
            total: 0,
            name: '',
            gender: '',
            genders: [
                {value: '1', label: 'Male'},
                {value: '0', label: 'Female'}
            ],
            isValid: 'Yes',
            centerDialogVisible: false,
            form: {
                id: '',
                email: '',
                username: '',
                mobile: '',
                avatar: '',
                gender: '',
                password: '',
                age: '',
                roleId: '2',
                //isValid: 'Y'
            },
            rules: {
                email: [
                    {required: true, message: 'Please enter your email', trigger: 'blur'},
                    {validator: checkDuplicate, trigger: 'blur'}
                ],
                username: [
                    {required: true, message: 'Please enter your username', trigger: 'blur'},
                    {min: 3, max: 15, message: 'It should be 3 ~ 15 characters', trigger: 'blur'},
                ],
                password: [
                    {required: true, message: 'Please enter your password', trigger: 'blur'},
                    {min: 3, max: 12, message: 'It should be 3 ~ 8 characters', trigger: 'blur'}
                ],
                age: [
                    {required: true, message: 'Please enter your age', trigger: 'blur'},
                    {min: 1, max: 3, message: 'It should be 1 ~ 3 characters', trigger: 'blur'},
                    {pattern: /^([1-9][0-9]*){1,3}$/, message: 'Invalid age', trigger: 'blur'},
                    {validator: checkAge, trigger: 'blur'}
                ],
                mobile: [
                    {required: true, message: 'Please enter your phone', trigger: 'blur'}
                ]
            }
        }
    },
    methods: {
        loadList(){
            /*request.get('/user/list').then(res => res.data).then(res => {
                console.log(res)*/
            this.$axios.post(this.$httpUrl + '/user/listPage', {
                pageSize: this.pageSize,
                pageNum: this.pageNum,
                data: {
                    // search user input
                    name: this.name,
                    gender: this.gender,
                    //roleId: '2'
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.data;
                this.total = res.total;
            })
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
                that.form.avatar = res.data.url
            }else{
                this.$message.error(`club type avatar upload failed:${res.message}`)
            }
            console.log(that.form.avatar)
        },
        handleSizeChange(val) {
            this.pageNum = 1;
            this.pageSize = val;
            this.loadList();
            console.log(`${val} items per page`);
        },
        handleCurrentChange(val) {
            this.pageNum = val;
            this.loadList();
            console.log(`current page: ${val}`);
        },
        resetQuery(){
            this.name = '';
            this.gender = '';
        },
        create(){
            this.centerDialogVisible = true;
            //this.resetForm();
            this.$nextTick(() => {
                this.resetForm();
                this.form.id = '';
            });
        },
        save(){
            // check form input by using 'rules[]'
            this.$refs.form.validate((valid) => {
                // if valid, then send request
                if (valid) {
                    if(this.form.id){
                        this.editUser();
                    }else{
                        this.saveUser();
                    }
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });

        },
        saveUser(){
            this.$axios.post(this.$httpUrl + '/user/save', this.form).then(res => {
                console.log(res)
                if(res.code == 200){
                    this.centerDialogVisible = false;
                    this.$message({
                        showClose: true,
                        message: 'Congrats, create user successfully.',
                        type: 'success'
                    });
                    this.loadList()
                }else{
                    this.$message({
                        showClose: true,
                        message: 'Oops, user create failed.',
                        type: 'error'
                    });
                }
            })
        },
        editUser(){
            this.$axios.post(this.$httpUrl + '/user/update', this.form).then(res => {
                //console.log(res)
                if(res.code == 200){
                    this.centerDialogVisible = false;
                    this.$message({
                        showClose: true,
                        message: 'Congrats, update user successfully.',
                        type: 'success'
                    });
                    this.loadList()
                }else{
                    this.$message({
                        showClose: true,
                        message: 'Oops, user update failed.',
                        type: 'error'
                    });
                }
            })
        },
        edit(row){
            console.log(row)

            // view form
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                // get data from row
                this.form.id = row.id
                this.form.email = row.email
                this.form.username = row.username
                this.form.avatar = row.avatar
                this.form.password = row.password
                this.form.age = row.age + ''
                this.form.gender = row.gender + '' // == 1 ? 'Male' : 'Female'
                this.form.mobile = row.mobile
                this.form.roleId = row.roleId + '' // == 0 ? 'Super Admin' : (row.roleId == 1 ? 'Admin' : 'User')
                //this.isValid = row.isValid
            })
        },
        del(id){
            console.log(id)
            this.$axios.get(this.$httpUrl + '/user/delete?id=' + id).then(res => {
                //console.log(res)
                if(res.code == 200){
                    this.$message({
                        showClose: true,
                        message: 'Congrats, delete user successfully.',
                        type: 'success'
                    });
                    this.loadList()
                }else{
                    this.$message({
                        showClose: true,
                        message: 'Oops, user delete failed.',
                        type: 'error'
                    });
                }
            })
        },
        resetForm() {
            this.$refs.form.resetFields();
        }
    },
    //created
    beforeMount() {
        this.loadList();
    }
}
</script>

<style scoped>

</style>