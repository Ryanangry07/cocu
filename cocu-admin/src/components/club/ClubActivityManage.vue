<template>
    <div>
        <div style="margin-bottom: 5px;">
            <el-input v-model="name" placeholder="Enter activity name to search" suffix-icon="el-icon-search"
                      style="width: 300px" @keyup.enter.native="loadList"></el-input>
            <el-select style="margin-left: 10px" v-model="auditStatus" placeholder="Select audit status">
                <el-option label="Pending" value="0"></el-option>
                <el-option label="Approve" value="1"></el-option>
                <el-option label="Disapprove" value="2"></el-option>
            </el-select>
            <el-button type="primary" style="margin-left: 9px" @click="loadList">Search</el-button>
            <el-button type="success" @click="resetQuery">Reset</el-button>
            <el-button type="success" @click="create">Creat New</el-button>
        </div>
        <el-table :data="tableData" :header-cell-style="{background: '#f2f5fc', color: '#555'}" border>
            <el-table-column prop="id" label="ID" width="50">
            </el-table-column>
            <el-table-column prop="avatar" label="Avatar" width="130">
                <template slot-scope="scope">
                    <img class="me-header-picture el-dropdown-link" width="120px" :src="scope.row.avatar"/>
                </template>
            </el-table-column>

            <el-table-column prop="title" label="Title" width="150">
            </el-table-column>

            <el-table-column prop="club" label="Club" width="150">
                <template slot-scope="scope">
                    {{scope.row.club}}
                </template>
            </el-table-column>
            <el-table-column prop="organizer" label="Organizer" width="150">
                <template slot-scope="scope">
                    {{scope.row.organizerName}}<br>
                    {{scope.row.organizer}}
                </template>
            </el-table-column>

            <el-table-column prop="date" label="Activity Date" width="200">
                <template slot-scope="scope">
                    Start: {{scope.row.startDate}}<br>
                    End: {{scope.row.endDate}}
                </template>
            </el-table-column>

            <el-table-column prop="audit" label="Audit Status" width="100">
                <template slot-scope="scope">
                    <el-tag
                        :type="scope.row.auditStatus === '0' ? 'info' : (scope.row.auditStatus === '1' ? 'primary' : 'danger')"
                        disable-transitions>{{scope.row.auditStatus === '0' ? 'Pending' :
                        (scope.row.auditStatus === '1' ? 'Approved' : 'Disapproved')}}</el-tag>
                </template>
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
            style="margin-left: 25%; margin-top: 20px; margin-bottom: 20px">
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
                <el-form-item label="Activity Title">
                    <span>{{ form.title }}</span>
                </el-form-item>
                <el-form-item label="Activity Summary">
                    <span>{{ form.summary }}</span>
                </el-form-item>
                <el-form-item label="Activity Location">
                    <span>{{ form.location }}</span>
                </el-form-item>
                <el-form-item label="Club">
                    <span>{{ form.club }}</span>
                </el-form-item>
                <el-form-item label="Activity Organizer">
                    <span>{{ form.organizerName }}</span><br />
                    <span>{{ form.organizer }}</span>
                </el-form-item>
                <el-form-item label="Activity Capacity">
                    <span>{{ form.capacity }}</span>
                </el-form-item>
                <el-form-item label="Activity Date">
                    <span>Start: {{ form.startDate }}</span><br/>
                    <span>End: {{ form.endDate }}</span>
                </el-form-item>
                <el-form-item label="Category">
                    <span>{{ form.category }}</span>
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
                <el-form-item label="Recommend" prop="recommend">
                    <el-radio-group v-model="form.recommend">
                        <el-radio :label="true">Yes</el-radio>
                        <el-radio :label="false">No</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">Cancel</el-button>
                <el-button type="primary" @click="auditActivity">Confirm</el-button>
            </span>
        </el-dialog>
<!--        create new -->
        <el-dialog
            title="Operate Activity Information"
            :visible.sync="centerDialogVisible2"
            width="50%"
            center>
            <el-form ref="form" :rules="createNewRules" :model="createForm" label-width="120px" style="padding-right: 20px">
                <el-form-item label="Avatar" prop="avatar">
                    <el-upload
                        class="avatar-uploader"
                        action="http://localhost:8082/upload"
                        name="uploadAvatar"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                        :before-upload="beforeAvatarUpload">
                        <img v-if="createForm.avatar" :src="createForm.avatar" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item label="Title" prop="title">
                    <el-col :span="20">
                        <el-input v-model="createForm.title"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="Summary" prop="summary">
                    <el-col :span="20">
                        <el-input v-model="createForm.summary"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="Location" prop="location">
                    <el-col :span="20">
                        <el-input v-model="createForm.location"></el-input>
                    </el-col>
                </el-form-item>
                <el-form-item label="Start Date" prop="startDate">
                    <el-col :span="20">
                        <el-date-picker v-model="createForm.startDate" type="datetime" placeholder="Select date and time"></el-date-picker>
                    </el-col>
                </el-form-item>
                <el-form-item label="End Date" prop="endDate">
                    <el-col :span="20">
                        <el-date-picker v-model="createForm.endDate" type="datetime" placeholder="End date and time"></el-date-picker>
                    </el-col>
                </el-form-item>
                <el-form-item label="Capacity" prop="capacity">
                    <el-col :span="20">
                        <el-input-number v-model="createForm.capacity" :min="0" placeholder="Enter capacity"></el-input-number>
                    </el-col>
                </el-form-item>
                <!--                <el-form-item label="President" prop="president">
                                    <el-col :span="20">
                                        <el-input v-model="form.president"></el-input>
                                    </el-col>
                                </el-form-item>-->
                <el-form-item label="Organizer" prop="organizerId">
                    <el-col :span="20">
                        <el-input v-model="createForm.organizer" @click.native="selectPresident" readonly></el-input>
                    </el-col>
                </el-form-item>
                <el-dialog
                    width="70%"
                    title="Select Activity Organizer"
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

                <el-form-item label="Club" prop="clubId">
                    <el-col :span="20">
                        <el-select v-model="createForm.clubId" placeholder="Please select">
                            <el-option
                                v-for="item in clubData"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="Category" prop="categoryId">
                    <el-col :span="20">
                        <el-select v-model="createForm.categoryId" placeholder="Please select">
                            <el-option
                                v-for="item in activityCategoryData"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="Tag" prop="tagIds">
                    <el-col :span="20">
                        <el-select v-model="createForm.tagIds" multiple  placeholder="Please select">
                            <el-option
                                v-for="item in activityTagData"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>
                <el-form-item label="Recommend" prop="recommend">
                    <el-radio-group v-model="createForm.recommend">
                        <el-radio :label="true">Yes</el-radio>
                        <el-radio :label="false">No</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible2 = false">Cancel</el-button>
                <el-button type="primary" @click="save">Confirm</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>

import SelectUser from "@/components/user/SelectUser.vue";
import moment from 'moment';

export default {
    name: "ClubActivityManage",
    components: {SelectUser},
    data() {
        return {
            id: '',
            tableData: [],
            clubData: [],
            activityCategoryData: [],
            activityTagData: [],
            pageSize: 5,
            pageNum: 1,
            total: 0,
            //name, auditStatus, are the search keywords
            name: '',
            auditStatus: '',
            centerDialogVisible: false,
            centerDialogVisible2: false,
            innerEntryVisible: false,
            form: {
                id: '',
                avatar: '',
                title: '',
                summary: '',
                auditStatus: '',
                auditContent: '',
                location: '',
                capacity: '',
                participants: '',
                startDate: '',
                endDate: '',
                organizerId: '',
                clubId: '',
                categoryId: '',
                recommend: false
                /*contentId: '',
                views: '',
                likes: '',
                stars: '',
                comments: ''*/
            },
            createForm: {
                id: '',
                avatar: '',
                title: '',
                summary: '',
                auditStatus: '',
                auditContent: '',
                location: '',
                capacity: '',
                participants: '',
                startDate: '',
                endDate: '',
                organizerId: '',
                organizer: '',
                clubId: '',
                categoryId: '',
                tagIds: [],
                recommend: false
                /*contentId: '',
                views: '',
                likes: '',
                stars: '',
                comments: ''*/
            },
            tempUser: {},
            rules: {
                auditStatus: [
                    {required: true, message: 'Please select audit result', trigger: 'change'}
                ],
                auditContent: [
                    {required: true, message: 'Please enter audit content', trigger: 'blur'},
                ]
            },
            createNewRules: {
                avatar: [
                    { required: true, message: 'Please upload an avatar', trigger: 'change' }
                ],
                title: [
                    { required: true, message: 'Please enter the activity title', trigger: 'blur' },
                    { min: 3, max: 100, message: 'Title must be between 3 and 100 characters', trigger: 'blur' }
                ],
                summary: [
                    { required: true, message: 'Please enter the activity summary', trigger: 'blur' },
                    { min: 10, max: 200, message: 'Summary must be between 10 and 200 characters', trigger: 'blur' }
                ],
                location: [
                    { required: true, message: 'Please enter the activity location', trigger: 'blur' }
                ],
                startDate: [
                    { required: true, message: 'Please select a start date and time', trigger: 'blur' }
                ],
                endDate: [
                    { required: true, message: 'Please select an end date and time', trigger: 'blur' }
                ],
                capacity: [
                    { required: true, message: 'Please enter capacity', trigger: 'blur' },
                    { type: 'number', min: 0, message: 'Capacity must be a non-negative number', trigger: 'blur' }
                ],
                description: [
                    { required: true, message: 'Please enter the activity description', trigger: 'blur' },
                    { min: 10, max: 500, message: 'Description must be between 10 and 500 characters', trigger: 'blur' }
                ],
                organizer: [
                    { required: true, message: 'Please select an organizer', trigger: 'blur' }
                ],
                clubId: [
                    { required: true, message: 'Please select a club', trigger: 'change' }
                ],
                categoryId: [
                    { required: true, message: 'Please select a category', trigger: 'change' }
                ],
                tagIds: [
                    { required: true, message: 'Please select at least one tag', trigger: 'change' }
                ],
                recommend: [
                    { required: true, message: 'Please select recommendation status', trigger: 'change' }
                ]
            }
        }
    },
    methods: {
        loadList(){
            // if search keywords is not '', load first page
            let size = this.name == '' ? this.pageSize : 10
            let num = this.name == '' ? this.pageNum : 1
            this.$axios.post(this.$httpUrl + '/activity/listPage', {
                pageSize: size,
                pageNum: num,
                data: {
                    // search activity input name
                    name: this.name,
                    auditStatus: this.auditStatus
                }
            }).then(res => {
                console.log(res)
                this.tableData = res.data;
                this.total = res.total;
            })
        },
        loadClub(){
            this.$axios.get(this.$httpUrl+ '/club/list').then(res =>{
                console.log('ActivityManage.vue - loadClub: ' + res)
                if(res.code == 200){
                    this.clubData = res.data
                }else{
                    alert('error')
                }
            })
        },
        loadActivityCategory(){
            this.$axios.get(this.$httpUrl+ '/category/list').then(res =>{
                console.log('ActivityManage.vue - loadActivityCategory: ' + res)
                if(res.code == 200){
                    this.activityCategoryData = res.data
                }else{
                    alert('error')
                }
            })
        },
        loadActivityTag(){
            this.$axios.get(this.$httpUrl+ '/tag/list').then(res =>{
                console.log('ActivityManage.vue - loadActivityTag: ' + res)
                if(res.code == 200){
                    this.activityTagData = res.data
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
                that.createForm.avatar = res.data.url
            }else{
                this.$message.error(`activity avatar upload failed:${res.message}`)
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
            this.auditStatus = '';
        },
        create(){
            this.loadClub();
            this.loadActivityCategory();
            this.loadActivityTag();
            this.centerDialogVisible2 = true;
            // this.resetForm();
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
                        this.saveActivity();
                    }else{
                        this.saveActivity();
                    }
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });

        },
        saveActivity(){
            const formattedData = {
                ...this.createForm,
                startDate: moment(this.createForm.startDate).format('YYYY-MM-DD HH:mm:ss'),
                endDate: moment(this.createForm.endDate).format('YYYY-MM-DD HH:mm:ss')
            };

            this.$axios.post(this.$httpUrl + '/activity/save', formattedData).then(res => {
                console.log(res)
                if(res.code == 200){
                    this.centerDialogVisible2 = false;
                    this.$message({
                        showClose: true,
                        message: 'Congrats, create activity successfully.',
                        type: 'success'
                    });
                    this.loadList()
                }else{
                    this.$message({
                        showClose: true,
                        message: 'Oops, activity create failed.',
                        type: 'error'
                    });
                }
            })
        },
        auditActivity(){
            // check form input by using 'rules[]'
            this.$refs.form.validate((valid) => {
                // if valid, then send request
                if (valid) {
                    this.$axios.post(this.$httpUrl + '/activity/audit', {
                        activityId: this.form.id,
                        auditStatus: this.form.auditStatus,
                        auditContent: this.form.auditContent,
                        recommend: this.form.recommend
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
            console.log('test')
            console.log(row)
            // view form
            this.centerDialogVisible = true;
            this.$nextTick(() => {
                // get data from row
                this.form.id = row.id
                this.form.avatar = row.avatar
                this.form.title = row.title
                this.form.summary = row.summary
                this.form.location = row.location
                this.form.capacity = row.capacity
                this.form.participants = row.participants
                this.form.startDate = row.startDate
                this.form.endDate = row.endDate
                this.form.organizer = row.organizer
                this.form.organizerName = row.organizerName
                this.form.club = row.club
                this.form.auditStatus = row.auditStatus
                this.form.auditContent = row.auditContent
                // this.form.isRecommend = row.isRecommend
                this.form.recommend = row.recommend
            })
        },
        del(id){
            console.log(id)
            this.$axios.get(this.$httpUrl + '/activity/delete?id=' + id).then(res => {
                //console.log(res)
                if(res.code == 200){
                    this.$message({
                        showClose: true,
                        message: 'Congrats, delete activity successfully.',
                        type: 'success'
                    });
                    // if only one record in this page, after delete, move page to previous page
                    if(this.tableData.length == 1) this.pageNum = (this.pageNum - 1 < 1) ? 1 : this.pageNum - 1
                    this.loadList()
                }else{
                    this.$message({
                        showClose: true,
                        message: 'Oops, activity delete failed.',
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
            console.log('tempUser:'  + this.tempUser)
            this.innerEntryVisible = false;
            this.createForm.organizerId = this.tempUser.id;
            this.createForm.organizer = this.tempUser.email;
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