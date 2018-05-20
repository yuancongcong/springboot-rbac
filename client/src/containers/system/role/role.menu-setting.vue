<template>
    <el-form label-width="80px" ref="form" class="c-form">
        <el-form-item label="菜单列表">
            <select-menu-tree v-model="menusIds"></select-menu-tree>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit" :loading="loading">保存</el-button>
            <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import { save,getMenus } from '@/api/system/role.service'
import selectMenuTree from '@/components/select/select.menu-tree'

export default {
    components:{selectMenuTree},
    data() {
        return {
            id:this.$route.params.id,
            loading: false,
            menusIds:[],
        }
    },
    created(){
        this.initData();
    },
    methods: {
        async initData(){
            let menus = await getMenus({id:this.id})
            this.menusIds = menus.map(v=>v.id);
        },
        async onSubmit() {
            try {
                this.loading = true;
                await save({id:this.id,menusIds:this.menusIds});
                this.$notify.success(`${this.id ? "修改":"新增"}成功`);
                this.visible = false;
                this.$router.back();
            } catch (e) {
                this.$notify.error(e.message);
            } finally {
                this.loading = false;
            }

        },
    }
}
</script>
