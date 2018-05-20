<template>
    <div>
        <div class="dict_tree" v-if="showNode">
            <el-tree :load="loadNode" node-key="id" :expand-on-click-node="false" lazy :props="props" @node-click="handleNodeClick" ref="tree">
            </el-tree>
        </div>
        <el-row class="content dict_content">
            <el-col :span="24" class="toolbar">
                <el-button type="primary" icon="plus" @click="handleAdd({})">新增</el-button>
            </el-col>
            <el-form inline :model="search" ref="search" @submit.native="()=> false">
                <el-form-item label="关键字查询" prop="name">
                    <el-input v-model="search.name"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="initData">查询</el-button>
                </el-form-item>
            </el-form>
            <el-col :span="24">
                <el-table :data="tableData" @selection-change="selsChange" v-loading="loading" stripe border ref="table">
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column type="index" width="60"></el-table-column>
                    <el-table-column prop="name" label="名称" width="200"></el-table-column>
                    <el-table-column prop="code" label="代码"></el-table-column>
                    <el-table-column prop="sort" label="排序" width="125">
                        <template slot-scope="scope">
                            <el-input v-model="scope.row.sort">
                                <el-button slot="append" size="small" icon="el-icon-fa-save" @click="saveSort(scope.row)"></el-button>
                            </el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="250">
                        <template slot-scope="scope">
                            <el-button size="small" @click="handleAdd(scope.row)">添加下级</el-button>
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

        <el-dialog
            :visible.sync="dialogVisible" width="500px">
            <el-form :model="model" :rules="rules" label-width="80px" ref="form">
                <el-form-item label="名称" prop="name">
                    <el-input v-model="model.name"></el-input>
                </el-form-item>
                <el-form-item label="代码" prop="code">
                    <el-input v-model="model.code"></el-input>
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                    <el-input v-model="model.sort"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit" :loading="saveLoading">保存</el-button>
                    <el-button @click="dialogVisible = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
import { getListPage,getList, deleteByIds, save,getById } from '@/api/system/dict.service'
let id = 1000;
export default {
    data() {
        return {
            loading: false, //列表load
            page: 1,
            pageSize: 10,
            total: 0,
            tableData: [], //表格数据
            sels: [],
            search: {},
            /**tree */
            props: {
                children: 'children',
                label: 'name',
                isLeaf:"isLeaf"
            },
            //add or edit
            dialogVisible : false,
            model:{},
            rules: {
                name: [
                    { required: true, message: '请填写字典名称', trigger: 'blur' },
                ],
            },
            saveLoading: false,
            showNode:true,
        }
    },
    created() {
        this.initData();
    },
    methods: {
        async loadNode(node,resolve){
            const treeData = await getList({pid:node.data ? node.data.id : null});
            resolve(treeData);
        },
        handleNodeClick(b){
            this.search.flag = b.flag;
            this.initData();
        },
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
        handleAdd(row) {
            let pid = row&&row.id ? row.id : this.$refs.tree.getCurrentKey();
            this.model = {pid};
            this.dialogVisible = true;
        },
        handleEdit(row) {
            this.model = {...row};
            this.dialogVisible = true;
        },
        onSubmit() {
            this.$refs.form.validate(async (valid) => {
                if (valid) {
                    try {
                        const isAdd = !!this.model.id;
                        this.saveLoading = true;
                        this.model = await save(this.model);
                        this.$notify.success(isAdd ? '修改':'新增' + '成功');
                        this.dialogVisible = false;
                        this.initData();
                        this.updateNode({...this.model});
                    } catch (e) {
                        this.$notify.error(e.message);
                    } finally {
                        this.saveLoading = false;
                    }
                }
            });
        },
        async saveSort(row){
            if(!row.sort) return;
            await save(row);
            this.$notify.success('保存成功！');
            this.initData();
            this.refreshNode();
        },
        remove(node, data) {
            const parent = node.parent;
            const children = parent.data.children || parent.data;
            const index = children.findIndex(d => d.id === data.id);
            children.splice(index, 1);
        },
        updateNode(data){
            let node = this.$refs.tree.getNode(data.id);
            if(node){ //修改
                node.data = data;
            }else{ //新增
                this.refreshNode();
            }
        },
        refreshNode(){
            this.showNode = false;
            setTimeout(()=>{
                this.showNode =  true;
            })
        },
        deleteNode(nodeKey){
            this.$refs.tree.remove(nodeKey);
        },
        async handleDel(ids) {
            await this.$confirm('确认删除吗？', '提示');
            await deleteByIds({ ids});
            this.$notify.success('删除成功');
            this.initData();

            ids.forEach(element => {
                this.deleteNode(element)
            });
        },
        //删除多个
        async batchRemove() {
            const ids = this.sels.map(v => v.id);
            if (ids.length > 0) {
                this.handleDel(ids);
            } else {
                this.$notify.warning('请选择需要删除的数据');
            }
        },
    }
}
</script>

<style lang="scss" scoped>
.dict_tree{
    width: 230px;
    position: absolute;
    top: 15px;
    left: 0;
    z-index: 999;
    border-right: solid 1px #e6e6e6;
    min-height: 100%;
    .is-current>.el-tree-node__content{
        background-color: #f5f7fa;;
    }
}
.dict_content{
    padding: 0 15px;
    padding-left: 230px;
}
</style>
