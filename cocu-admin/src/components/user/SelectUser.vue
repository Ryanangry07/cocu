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
        </div>
        <el-table
                highlight-current-row
                @current-change="selectRow"
                :data="tableData" :header-cell-style="{background: '#f2f5fc', color: '#555'}" border>
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
    </div>
</template>

<script>
export default {
    name: "SelectUser",
    data() {
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
        selectRow(val) {
            //this.currentRow = val;
            this.$emit("doSelectPresident", val);
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