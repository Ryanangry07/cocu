<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="Please enter to search message" suffix-icon="el-icon-search"
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
            <el-table-column prop="user" label="Username" width="200">
            </el-table-column>
            <el-table-column prop="content" label="Message Content">
            </el-table-column>

            <el-table-column prop="operate" label="Operation">
                <template slot-scope="scope">
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
                <el-form-item label="Message content" prop="content">
                    <el-col :span="20">
                        <el-input type="textarea" rows="5" v-model="form.content"></el-input>
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
    name: "MessageBoardManage",
    data() {
        return {
            curUser: JSON.parse(sessionStorage.getItem('curUser')),
            id: '',
            tableData: [],
            pageSize: 5,
            pageNum: 1,
            total: 0,
            name: '',
            user: '',
            userId: '',
            club: '',
            clubId: '',
            content: '',
            centerDialogVisible: false,
            form: {
                id: '',
                content: ''
            },
            rules: {
                content: [
                    {required: true, message: 'Please enter message content', trigger: 'blur'},
                ]
            }
        }
    },
    methods: {
        loadList(){
            // if search keywords is not '', load first page
            let size = this.name == '' ? this.pageSize : 10
            let num = this.name == '' ? this.pageNum : 1
            this.$axios.post(this.$httpUrl + '/club-message-board/listPage', {
                pageSize: size,
                pageNum: num,
                data: {
                    // search club type input name
                    name: this.name
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
                    this.saveClubType();
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });

        },
        saveClubType(){
            this.form.userId = this.curUser.id
            this.$axios.post(this.$httpUrl + '/club-message-board/save', this.form).then(res => {
                console.log(res)
                if(res.code == 200){
                    this.centerDialogVisible = false;
                    this.$message({
                        showClose: true,
                        message: 'Congrats, create message successfully.',
                        type: 'success'
                    });
                    this.loadList()
                }else{
                    this.$message({
                        showClose: true,
                        message: 'Oops, message create failed.',
                        type: 'error'
                    });
                }
            })
        },

        del(id){
            console.log(id)
            this.$axios.get(this.$httpUrl + '/club-message-board/delete?id=' + id).then(res => {
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

</style>