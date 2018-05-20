<template>
    <el-row>
        <el-col :span="24" class="toolbar">
            <el-button type="primary" icon="plus" @click="handleAdd()">新增</el-button>
        </el-col>
        <el-col :span="24">
            <el-table :data="tableData" @selection-change="selsChange" v-loading="loading" stripe border ref="table">
                <el-table-column type="index" width="60"></el-table-column>
                <el-table-column prop="name" label="名称" width="200">
                  <template slot-scope="scope">
                      <span v-for="id in scope.row.pids" :key="id">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                      <i :class="'el-icon-'+scope.row.icon"></i>&nbsp;
                      {{scope.row.name}}
                  </template>
                </el-table-column>
                <el-table-column prop="url" label="链接"></el-table-column>
                <el-table-column prop="sort" label="排序" width="80"></el-table-column>
                <el-table-column prop="disabled" label="禁用" width="100" align="center">
                  <template slot-scope="scope">
                      {{scope.row.disabled?'是':'否'}}
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="260">
                    <template slot-scope="scope">
                        <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button size="small" @click="handleAdd(scope.row)">添加子菜单</el-button>
                        <el-button type="danger" size="small" @click="handleDel(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
    </el-row>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import { deleteByIds } from '@/api/system/menu.service'

export default {
    data() {
        return {
          loading:false
        }
    },
    computed: {
        ...mapState({
            tableData: state => state.menuList
        })
    },
    methods: {
        ...mapActions([
            'getMenuList'
        ]),
        selsChange: function(sels) {
            this.sels = sels;
        },
        initData() {
            this.loading = true;
            this.getMenuList();
            this.loading = false;
        },
        handleAdd(row) {
            this.$router.push(`menu/add/${row ? [...row.pids,row.id].join(',') : ''}`)
        },
        handleEdit(row) {
            this.$router.push(`menu/edit/` + row.id)
        },
        async handleDel(row) {
            try{
                await this.$confirm('确认删除吗？', '提示');
                if (row.children && row.children.length > 0) {
                    await this.$confirm('该菜单有下级菜单吗，确认删除吗？', '提示',{type:'warning'});
                }
                await deleteByIds({ ids: [row.id] });
                this.$notify.success('删除成功');
                this.initData();
            }catch(e){
                this.$message.error(e.message)
            }
        },
    }
}
</script>
<style lang="scss" scoped>

</style>
