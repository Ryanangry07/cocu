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
            <el-select style="margin-left: 10px" v-model="auditStatus" placeholder="Select audit status">
                <el-option label="Pending" value="0"></el-option>
                <el-option label="Approve" value="1"></el-option>
                <el-option label="Disapprove" value="2"></el-option>
            </el-select>
            <el-button type="primary" style="margin-left: 9px" @click="loadList">Search</el-button>
            <el-button type="success" @click="resetQuery">Reset</el-button>
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
            <el-table-column prop="username" label="Username" width="110">
            </el-table-column>
            <el-table-column prop="age" label="Age" width="70">
            </el-table-column>
            <el-table-column prop="gender" label="Gender" width="80">
                <template slot-scope="scope">
                    <el-tag
                        :type="scope.row.gender === 1 ? 'primary' : 'success'"
                        disable-transitions>{{scope.row.gender === 1 ? 'Male' : 'Female'}}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="mobile" label="Mobile" width="150">
            </el-table-column>

            <el-table-column prop="name" label="Applied Club" width="150">
            </el-table-column>


            <el-table-column prop="auditStatus" label="Audit Status" width="120">
                <template slot-scope="scope">
                    <el-tag
                        :type="scope.row.auditStatus === '0' ? 'info' : (scope.row.auditStatus === '1' ? 'primary' : 'danger')"
                        disable-transitions>{{scope.row.auditStatus === '0' ? 'Pending' :
                        (scope.row.auditStatus === '1' ? 'Approved' : 'Disapproved')}}</el-tag>
                </template>
            </el-table-column>
            <!--            <el-table-column prop="isValid" label="Valid" width="100">
                        </el-table-column>-->
            <el-table-column prop="operate" label="Operation">
                <template slot-scope="scope">
                    <el-button type="success" @click="audit(scope.row)">Audit</el-button>
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
            title="Audit Activity"
            :visible.sync="centerDialogVisible"
            width="45%"
            center>
            <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="Avatar" prop="avatar">
                    <img v-if="form.avatar" :src="form.avatar" class="avatar">
                </el-form-item>
                <el-form-item label="Email">
                    <span>{{ form.email }}</span>
                </el-form-item>
                <el-form-item label="Username">
                    <span>{{ form.username }}</span>
                </el-form-item>
                <el-form-item label="Age">
                    <span>{{ form.age }}</span>
                </el-form-item>
                <el-form-item label="Gender">
                    <span>{{ form.gender }}</span>
                </el-form-item>
                <el-form-item label="Club Applied">
                    <span>{{ form.name }}</span>
                </el-form-item>
                <el-form-item label="Mobile">
                    <span>{{ form.mobile }}</span>
                </el-form-item>
            </el-form>
            <el-divider></el-divider>
            <el-form label-width="170px" ref="form" :rules="rules" :model="form" style="margin-top: 30px;">
                <el-form-item label="Audit Status" prop="auditStatus">
                    <el-select v-model="form.auditStatus" placeholder="Please audit">
                        <el-option label="Pending" value="0"></el-option>
                        <el-option label="Approve" value="1"></el-option>
                        <el-option label="Disapprove" value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="Audit Content" prop="auditContent">
                    <el-col :span="20">
                        <el-input type="textarea" rows="3" v-model="form.auditContent"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">Cancel</el-button>
                <el-button type="primary" @click="auditActivity">Confirm</el-button>
            </span>
        </el-dialog>
    </div>
</template>


<script>
export default {
    name: "ClubApplicationManage",
    data() {
        return {
            id: '',
            tableData: [],
            pageSize: 5,
            pageNum: 1,
            total: 0,
            //query condition
            auditStatus: '',
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
                auditStatus: '',
                auditContent: '',
            },
            rules: {
                auditStatus: [
                    {required: true, message: 'Please select audit result', trigger: 'change'}
                ],
                auditContent: [
                    {required: true, message: 'Please enter audit content', trigger: 'blur'},
                ]
            }
        }
    },
    methods: {
        loadList(){
            /*request.get('/user/list').then(res => res.data).then(res => {
                console.log(res)*/
            this.$axios.post(this.$httpUrl + '/user-apply-club/listPage', {
                pageSize: this.pageSize,
                pageNum: this.pageNum,
                data: {
                    // search user input
                    name: this.name,
                    gender: this.gender,
                    auditStatus: this.auditStatus
                    //roleId: '2'
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
            this.auditStatus = '';
        },
        auditActivity(){
            // check form input by using 'rules[]'
            this.$refs.form.validate((valid) => {
                // if valid, then send request
                if (valid) {
                    this.$axios.post(this.$httpUrl + '/user-apply-club/audit', {
                        activityId: this.form.id,
                        auditStatus: this.form.auditStatus,
                        auditContent: this.form.auditContent
                    }).then(res => {
                        console.log(res)
                        this.tableData = res.data;
                        this.total = res.total;
                        this.centerDialogVisible = false;
                        this.loadList();
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        audit(row){
            // view form
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                // get data from row
                this.form.id = row.id
                this.form.avatar = row.avatar
                this.form.email = row.email
                this.form.username = row.username
                this.form.age = row.age + ''
                this.form.name = row.name
                this.form.gender = row.gender + '' // == 1 ? 'Male' : 'Female'
                this.form.mobile = row.mobile
                this.form.roleId = row.roleId + '' // == 0 ? 'Super Admin' : (row.roleId == 1 ? 'Admin' : 'User')
                this.form.auditStatus = row.auditStatus
                this.form.auditContent = row.auditContent
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

<style>


.demo-table-expand {
//font-size: 0;
}
.demo-table-expand label {
    width: 200px;
    color: #99a9bf;
}
.demo-table-expand .el-form-item {
    margin-left: 17%;
    margin-right: 0;
    margin-bottom: 0;
    width: 70%;
}
</style>