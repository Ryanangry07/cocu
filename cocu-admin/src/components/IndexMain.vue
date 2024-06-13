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
            <el-table-column prop="no" label="Account" width="180">
            </el-table-column>
            <el-table-column prop="name" label="Name" width="180">
            </el-table-column>
            <el-table-column prop="age" label="Age" width="100">
            </el-table-column>
            <el-table-column prop="gender" label="Gender" width="100">
                <template slot-scope="scope">
                    <el-tag
                        :type="scope.row.sex === 1 ? 'primary' : 'success'"
                        disable-transitions>{{scope.row.sex === 1 ? 'Male' : 'Female'}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="roleId" label="Role" width="130">
                <template slot-scope="scope">
                    <el-tag
                        :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'primary' : 'success')"
                        disable-transitions>{{scope.row.roleId === 0 ? 'Super Admin' :
                        (scope.row.roleId === 1 ? 'Admin' : 'User')}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="Phone" width="180">
            </el-table-column>
            <el-table-column prop="isValid" label="Valid" width="100">
            </el-table-column>
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
            title="Create New User"
            :visible.sync="centerDialogVisible"
            width="30%"
            center>
            <el-form ref="form" :rules="rules" :model="form" label-width="120px" style="padding-right: 20px">
                <el-form-item label="Account" prop="no">
                    <el-col :span="20">
                        <el-input v-model="form.no"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="Name" prop="name">
                    <el-col :span="20">
                        <el-input v-model="form.name"></el-input>
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
                <el-form-item label="Phone" prop="phone">
                    <el-col :span="20">
                        <el-input v-model="form.phone"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="Role" prop="roleId">
                    <el-select v-model="form.roleId" placeholder="please select the role">
                        <el-option label="Super Admin" value="0"></el-option>
                        <el-option label="Admin" value="1"></el-option>
                        <el-option label="User" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="Valid" prop="isValid">
                    <el-radio-group v-model="form.isValid">
                        <el-radio label="Y" value="Y">Yes</el-radio>
                        <el-radio label="N" value="N">No</el-radio>
                    </el-radio-group>
                </el-form-item>
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
//import request from "@/utils/request";
export default {
    name: "IndexMain",
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
            this.$axios.get(this.$httpUrl + '/user/findByNo?no=' + this.form.no).then(res => {
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
                no: '',
                name: '',
                password: '',
                age: '',
                gender: '',
                phone: '',
                roleId: '',
                isValid: 'Y'
            },
            rules: {
                no: [
                    {required: true, message: 'Please enter your account', trigger: 'blur'},
                    {min: 3, max: 8, message: 'It should be 3 ~ 8 characters', trigger: 'blur'},
                    {validator: checkDuplicate, trigger: 'blur'}
                ],
                name: [
                    {required: true, message: 'Please enter your name', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: 'Please enter your password', trigger: 'blur'},
                    {min: 3, max: 8, message: 'It should be 3 ~ 8 characters', trigger: 'blur'}
                ],
                age: [
                    {required: true, message: 'Please enter your age', trigger: 'blur'},
                    {min: 1, max: 3, message: 'It should be 1 ~ 3 characters', trigger: 'blur'},
                    {pattern: /^([1-9][0-9]*){1,3}$/, message: 'Invalid age', trigger: 'blur'},
                    {validator: checkAge, trigger: 'blur'}
                ],
                phone: [
                    {required: true, message: 'Please enter your phone', trigger: 'blur'},
                    {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: 'Invalid phone number format', trigger: 'blur'},
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
                    gender: this.gender
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.data;
                this.total = res.total;
            })
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
            })
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
            console.log('gender: ' + row.sex)
            console.log(row)

            // view form
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                // get data from row
                this.form.id = row.id
                this.form.no = row.no
                this.form.name = row.name
                this.form.password = row.password
                this.form.age = row.age + ''
                this.form.gender = row.sex + '' // == 1 ? 'Male' : 'Female'
                this.form.phone = row.phone
                this.form.roleId = row.roleId + '' // == 0 ? 'Super Admin' : (row.roleId == 1 ? 'Admin' : 'User')
                this.isValid = row.isValid
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