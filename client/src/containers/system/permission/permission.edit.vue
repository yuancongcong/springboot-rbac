<template>
    <el-form :model="model" :rules="rules" label-width="80px" ref="form" class="c-form">
        <el-form-item label="资源名称" prop="name">
            <el-input v-model="model.name"></el-input>
        </el-form-item>
        <el-form-item label="访问链接" prop="url">
            <el-input v-model="model.url"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="roleIds">
            <role-checkbox v-model="roleIds"></role-checkbox>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
            <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="model.remark"></el-input>
        </el-form-item>
        <el-form-item label="是否禁用" prop="disabled">
            <el-radio-group v-model="model.disabled">
                <el-radio :label="true">是</el-radio>
                <el-radio :label="false">否</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit" :loading="loading">保存</el-button>
            <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import { save,getById,getRoles } from '@/api/system/permission.service'
import roleCheckbox from '@/components/select/select.role-checkbox'
export default {
    components:{roleCheckbox},
    data() {
        return {
            loading: false,
            model:{disabled:false},
            rules: {
                name: [
                    { required: true, message: '请填写姓名', trigger: 'blur' },
                ],
                url: [
                    { required: true, message: '请填写访问链接', trigger: 'blur' },
                ],
            },
            roles:[],
            roleIds:[], //默认普通用户
        }
    },
    created(){
        this.initData();
    },
    methods: {
        async initData(){
            const id = this.model.id = this.$route.params.id;
            //异步
            if(id){
                this.model = await getById(id)
                this.roleIds  = this.model.roles.map(v=>v.id)
            }
        },
        onSubmit() {
            this.$refs.form.validate(async (valid) => {
                if (valid) {
                    try {
                        this.loading = true;
                        await save({...this.model,roleIds:this.roleIds,roles:null});
                        this.$notify.success((this.model.id ? '修改':'新增') + '成功');
                        this.$router.back();
                    } catch (e) {
                        this.$notify.error(e.message);
                    } finally {
                        this.loading = false;
                    }
                }
            });
        },
    }
}
</script>
