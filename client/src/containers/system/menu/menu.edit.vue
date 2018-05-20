<template>
    <el-form :model="model" :rules="rules" label-width="80px" ref="form" class="c-form">
        <el-form-item label="上级菜单" prop="pids">
            <select-menu v-model="model.pids"></select-menu>
        </el-form-item>
        <el-form-item label="菜单名称" prop="name">
            <el-input v-model="model.name"></el-input>
        </el-form-item>
        <el-form-item label="菜单链接" prop="url">
            <el-input v-model="model.url"></el-input>
        </el-form-item>
        <el-form-item label="菜单图标" prop="icon">
            <select-icon v-model="model.icon"></select-icon>
        </el-form-item>
        <el-form-item label="禁用" prop="disabled">
            <el-radio-group v-model="model.disabled">
                <el-radio :label="true">是</el-radio>
                <el-radio :label="false">否</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
            <el-input v-model="model.sort"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit" :loading="loading">保存</el-button>
            <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import { mapActions } from 'vuex'
import { save,getById } from '@/api/system/menu.service'
import selectMenu from '@/components/select/select.menu'
import selectIcon from '@/components/select/select.icon'

export default {
    components: { selectMenu, selectIcon },
    data() {
        return {
            model:{
                disabled:false,
                icon:'menu'
            },
            loading: false,
            rules: {
                name: [
                    { required: true, message: '请填写菜单名称', trigger: 'blur' },
                ],
                url: [
                    { required: true, message: '请填写菜单链接', trigger: 'blur' },
                ]
            }
        }
    },
    created(){
        this.initData();
    },
    methods: {
        ...mapActions([
            'getMenuList'
        ]),
        async initData(){
            const id = this.model.id = this.$route.params.id;
            if(id){
                this.model = await getById(id)
            }else{
                this.model.pids = this.$route.params.pids;
            }
        },
        onSubmit() {
            this.$refs.form.validate(async (valid) => {
                if (valid) {
                    try {
                        this.loading = true;
                        //选择性的修改
                        let pids = this.model.pids;
                        this.model.pid = pids ? pids.substring(pids.lastIndexOf(',')+1) : null;
                        await save(this.model);
                        this.getMenuList();
                        this.$notify.success((this.model.id ? '修改':'新增' )+ '成功');
                        this.$router.back();
                    } catch (e) {
                        this.$notify.error(e.message);
                    }finally {
                        this.loading = false;
                    }
                }
            });

        },
    }
}
</script>
