<template>
    <el-row>
        <el-col :span="24" class="toolbar">
            <el-button type="primary" icon="plus" @click="handleAdd">新增</el-button>
        </el-col>
        <el-col :span="24">
            <el-form inline :model="search" ref="search">
                <el-form-item label="资源名称" prop="name">
                    <el-input v-model="search.name"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="initData">查询</el-button>
                </el-form-item>
            </el-form>
            <el-table :data="tableData" @selection-change="selsChange" v-loading="loading" stripe border ref="table">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column type="index" width="60"></el-table-column>
                <el-table-column prop="name" label="资源名称" width="150"></el-table-column>
                <el-table-column prop="url" label="访问链接"></el-table-column>
                <el-table-column prop="remark" label="备注"></el-table-column>
                <el-table-column label="操作" width="150">
                    <template slot-scope="scope">
                        <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button type="danger" size="small" @click="handleDel([scope.row.id])">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
        <el-col :span="24" class="toolbar">
            <el-button type="danger" @click="batchRemove" :disabled="sels.length===0">批量删除</el-button>
            <el-pagination @size-change="pageSize=$event;initData()" @current-change="initData" layout="sizes,prev, pager, next" :current-page.sync="page" :page-size="pageSize" :total="total" class="fr"></el-pagination>
        </el-col>
    </el-row>
</template>

<script>
    import { getListPage, deleteByIds } from '@/api/system/permission.service'

    export default {
        data() {
            return {
                loading: false, //列表load
                page: 1,
                pageSize: 10,
                total: 0,
                tableData: [], //表格数据
                sels: [],
                search: {}
            }
        },
        created() {
            this.initData();
        },
        methods: {
            selsChange: function(sels) {
                this.sels = sels;
            },
            //表格数据
            async initData() {
            this.loading = true;
            let { total, resultList } = await getListPage({ page : this.page-1, size:this.pageSize,...this.search });
            this.total = total;
            this.tableData = resultList;
            this.loading = false;
        },
        handleAdd() {
            this.$router.push('permission/add')
        },
        handleEdit(row) {
            this.$router.push('permission/edit/'+ row.id);
        },
        async handleDel(ids) {
            await this.$confirm('确认删除吗？', '提示');
            await deleteByIds({ ids });
            this.$notify.success('删除成功');
            this.initData();
        },
        //删除多个
        async batchRemove() {
            const ids = this.sels.map(v => v.id);
            if (ids.length > 0) {
                this.handleDel(ids);
            } else {
                this.$notify.warning('请选择需要删除的数据');
            }
        }
    }
}
</script>
<style lang="scss" scoped>

</style>
