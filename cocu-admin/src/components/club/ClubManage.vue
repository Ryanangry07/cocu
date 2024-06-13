<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="Enter club name to search" suffix-icon="el-icon-search"
                      style="width: 300px" @keyup.enter.native="loadList"></el-input>
            <el-select v-model="clubType" placeholder="Select club type" style="margin-left: 7px">
                <el-option
                    v-for="item in clubTypeData"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                </el-option>
            </el-select>
            <el-button type="primary" style="margin-left: 9px" @click="loadList">Search</el-button>
            <el-button type="success" @click="resetQuery">Reset</el-button>
            <el-button type="success" @click="create">Creat New</el-button>
        </div>
        <el-table :data="tableData" :header-cell-style="{background: '#f2f5fc', color: '#555'}" border>
            <el-table-column prop="id" label="ID" width="50">
            </el-table-column>
            <el-table-column prop="avatar" label="Avatar" width="150">
                <template slot-scope="scope">
                    <img class="me-header-picture el-dropdown-link" width="120px" :src="scope.row.avatar"/>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="Club Name" width="170">
            </el-table-column>
            <el-table-column prop="description" label="Description" width="300">
            </el-table-column>
<!--            <el-table-column prop="creator" label="Creator" width="130">
            </el-table-column>-->
            <el-table-column prop="president" label="Current President" width="170">
                <template slot-scope="scope">
                    {{scope.row.presidentName}}<br>
                    {{scope.row.president}}
                </template>
            </el-table-column>
            <el-table-column prop="type" label="Club Type" width="100">
            </el-table-column>
            <el-table-column prop="recommend" label="Recommend" width="100">
                <template slot-scope="scope">
                    <el-tag
                        :type="scope.row.recommend ? 'primary' : 'success'"
                        disable-transitions>{{scope.row.recommend ? 'Yes' : 'No'}}</el-tag>
                </template>
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
            title="Operate Club Information"
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
                <el-form-item label="Club Name" prop="name">
                    <el-col :span="20">
                        <el-input v-model="form.name"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="Description" prop="description">
                    <el-col :span="20">
                        <el-input type="textarea" rows="5" v-model="form.description"></el-input>
                    </el-col>
                </el-form-item>
<!--                <el-form-item label="President" prop="president">
                    <el-col :span="20">
                        <el-input v-model="form.president"></el-input>
                    </el-col>
                </el-form-item>-->
                <el-form-item label="President" prop="president">
                    <el-col :span="20">
                        <el-input v-model="form.president" @click.native="selectPresident" readonly></el-input>
                    </el-col>
                </el-form-item>
                <el-dialog
                    width="70%"
                    title="Select Club President"
                    :visible.sync="innerEntryVisible"
                    append-to-body>
                    <SelectUser @doSelectPresident="doSelectPresident"></SelectUser>

                    <span slot="footer" class="dialog-footer">
                    <el-button @click="innerEntryVisible = false">Cancel</el-button>
                        <el-button type="primary" @click="confirmPresident">Confirm</el-button>
                </span>
                </el-dialog>
<!--                <el-form-item label="Type" prop="type">
                    <el-col :span="20">
                        <el-input v-model="form.type"></el-input>
                    </el-col>
                </el-form-item>-->

                <el-form-item label="Type" prop="typeId">
                    <el-col :span="20">
                        <el-select v-model="form.typeId" placeholder="Please select">
                            <el-option
                                v-for="item in clubTypeData"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="Recommend" prop="recommend">
                    <el-radio-group v-model="form.recommend">
                        <el-radio :label="true">Yes</el-radio>
                        <el-radio :label="false">No</el-radio>
                    </el-radio-group>
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
import SelectUser from "@/components/user/SelectUser.vue";
export default {
    name: "ClubManage",
    components: {SelectUser},
    data() {
        return {
            id: '',
            tableData: [],
            clubTypeData: [],
            clubType: '',
            pageSize: 5,
            pageNum: 1,
            total: 0,
            name: '',
            centerDialogVisible: false,
            innerEntryVisible: false,
            form: {
                id: '',
                avatar: '',
                name: '',
                description: '',
                creatorId: '',
                presidentId: '',
                typeId: '',
                recommend: false
            },
            tempUser: {},
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
            this.$axios.post(this.$httpUrl + '/club/listPage', {
                pageSize: size,
                pageNum: num,
                data: {
                    // search club type input name
                    name: this.name,
                    type: this.clubType + ''
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.data;
                this.total = res.total;
            })
        },
        loadClubType(){
            this.$axios.get(this.$httpUrl+ '/club-type/list').then(res =>{
                console.log('ClubManage.vue - loadClubType: ' + res)
                if(res.code == 200){
                    this.clubTypeData = res.data
                }else{
                    alert('error')
                }
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
                this.$message.error(`club avatar upload failed:${res.message}`)
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
            this.clubType = '';
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
            this.$axios.post(this.$httpUrl + '/club/save', this.form).then(res => {
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
            this.$axios.post(this.$httpUrl + '/club/update', this.form).then(res => {
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
            console.log('edit' + row)
            // view form
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                // get data from row
                this.form.id = row.id
                this.form.name = row.name
                this.form.avatar = row.avatar
                this.form.description = row.description
                this.form.creator = row.creator
                this.form.president = row.president
                //this.form.type = row.type
                this.form.typeId = row.typeId
                this.form.recommend = row.recommend
            })
            // no need call backend here,
            // the popup window will call the update api
        },
        del(id){
            console.log(id)
            this.$axios.get(this.$httpUrl + '/club/delete?id=' + id).then(res => {
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
        selectPresident(){
            this.innerEntryVisible = true;
        },
        doSelectPresident(val){
            console.log(val);
            this.tempUser = val;
        },
        confirmPresident(){
            this.innerEntryVisible = false;
            this.form.presidentId = this.tempUser.id;
            this.form.president = this.tempUser.email;
            if(!this.form.creatorId){
                this.form.creatorId = this.form.presidentId
            }
        },
        resetForm() {
            this.$refs.form.resetFields();
        }
    },
    //created
    beforeMount() {
        this.loadClubType();
        this.loadList();
    }
}
</script>

<style scoped>

</style>