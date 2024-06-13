<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="Please enter club type name" suffix-icon="el-icon-search"
                      style="width: 300px" @keyup.enter.native="loadList"></el-input>
            <el-button type="primary" style="margin-left: 9px" @click="loadList">Search</el-button>
            <el-button type="success" @click="resetQuery">Reset</el-button>
            <el-button type="success" @click="create">Creat New</el-button>
        </div>
        <el-table :data="tableData" :header-cell-style="{background: '#f2f5fc', color: '#555'}" border>
            <el-table-column prop="id" label="ID" width="50">
            </el-table-column>
            <el-table-column prop="avatar" label="Avatar" width="130">
                <template slot-scope="scope">
                    <img class="me-header-picture el-dropdown-link" :src="scope.row.avatar"/>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="Type Name" width="200">
            </el-table-column>
            <el-table-column prop="description" label="Description">
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
            style="margin-left: 25%; margin-top: 20px; margin-bottom: 20px">
        </el-pagination>
        <el-dialog
            title="Operate Club Type"
            :visible.sync="centerDialogVisible"
            width="50%"
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
                <el-form-item label="Club Type Name" prop="name">
                    <el-col :span="20">
                        <el-input v-model="form.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="Description" prop="description">
                    <el-col :span="20">
                        <el-input type="textarea" rows="5" v-model="form.description"></el-input>
                    </el-col>
                </el-form-item>
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
    name: "ClubTypeManage",
    data() {
        return {
            id: '',
            tableData: [],
            pageSize: 5,
            pageNum: 1,
            total: 0,
            name: '',
            centerDialogVisible: false,
            form: {
                id: '',
                avatar: '',
                name: '',
                description: ''
            },
            rules: {
                name: [
                    {required: true, message: 'Please enter club type name', trigger: 'blur'}
                ],
                description: [
                    {required: true, message: 'Please enter club type description', trigger: 'blur'},
                ]
            }
        }
    },
    methods: {
        loadList(){
            // if search keywords is not '', load first page
            let size = this.name == '' ? this.pageSize : 10
            let num = this.name == '' ? this.pageNum : 1
            this.$axios.post(this.$httpUrl + '/club-type/listPage', {
                pageSize: size,
                pageNum: num,
                data: {
                    // search club type input name
                    name: this.name
                }
            }).then(res => {
                console.log('wu zhaobo res: ' + res.data)
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
        },
        create(){
            this.centerDialogVisible = true;
            //this.resetForm();
            this.$nextTick(() => {
                this.resetForm();
            });
            this.form.id = '';
        },
        save(){
            // check form input by using 'rules[]'
            this.$refs.form.validate((valid) => {
                // if valid, then send request
                if (valid) {
                    if(this.form.id){
                        this.editClubType();
                    }else{
                        this.saveClubType();
                    }
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });

        },
        saveClubType(){
            this.$axios.post(this.$httpUrl + '/club-type/save', this.form).then(res => {
                console.log(res)
                if(res.code == 200){
                    this.centerDialogVisible = false;
                    this.$message({
                        showClose: true,
                        message: 'Congrats, create club type successfully.',
                        type: 'success'
                    });
                    this.loadList()
                }else{
                    this.$message({
                        showClose: true,
                        message: 'Oops, club type create failed.',
                        type: 'error'
                    });
                }
            })
        },
        editClubType(){
            this.$axios.post(this.$httpUrl + '/club-type/update', this.form).then(res => {
                //console.log(res)
                if(res.code == 200){
                    this.centerDialogVisible = false;
                    this.$message({
                        showClose: true,
                        message: 'Congrats, update club type successfully.',
                        type: 'success'
                    });
                    this.loadList()
                }else{
                    this.$message({
                        showClose: true,
                        message: 'Oops, club type update failed.',
                        type: 'error'
                    });
                }
            })
        },
        edit(row){
            // view form
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                // get data from row
                this.form.id = row.id
                this.form.name = row.name
                this.form.avatar = row.avatar
                this.form.description = row.description
            })
            // no need call backend here,
            // the popup window will call the update api
        },
        del(id){
            console.log(id)
            this.$axios.get(this.$httpUrl + '/club-type/delete?id=' + id).then(res => {
                //console.log(res)
                if(res.code == 200){
                    this.$message({
                        showClose: true,
                        message: 'Congrats, delete club type successfully.',
                        type: 'success'
                    });
                    this.loadList()
                }else{
                    this.$message({
                        showClose: true,
                        message: 'Oops, club type delete failed.',
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
/*.header-center {
    display: flex;
    justify-content: center;
    align-items: center;
}
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}
.avatar-uploader .el-upload:hover {
    border-color: #409EFF;
}
.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 90px;
    height: 90px;
    line-height: 90px;
    text-align: center;
}
.avatar {
    width: 90px;
    height: 90px;
    display: block;
    background-color: #5fb878;
}*/
</style>