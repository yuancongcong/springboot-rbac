<template>
    <el-form :model="model" :rules="rules" label-width="80px" ref="form" class="c-form">
        <el-form-item label="角色名称" prop="name">
            <el-input v-model="model.name"></el-input>
        </el-form-item>
        <el-form-item label="角色代码" prop="code">
            <el-input v-model="model.code"></el-input>
        </el-form-item>
        <el-form-item label="是否禁用" prop="disabled">
            <el-radio-group v-model="model.disabled">
                <el-radio :label="true">是</el-radio>
                <el-radio :label="false">否</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="成员列表">
            <select-user v-model="users" @exists="handleExists"></select-user>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit" :loading="loading">保存</el-button>
            <el-button @click="$router.back()">取消</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import { save,getById,getUser } from '@/api/system/role.service'
import selectUser from '@/components/select/select.user'

export default {
    components:{selectUser},
    data() {
        return {
            loading: false,
            model:{
                disabled:false,
            },
            users:[],
            rules: {
                name: [
                    { required: true, message: '请填写角色名称', trigger: 'blur' },
                ],
                code: [
                    { required: true, message: '请填写角色代码', trigger: 'blur' },
                ],
            }
        }
    },
    created(){
        this.initData();
    },
    methods: {
        async initData(){
            const id = this.model.id = this.$route.params.id;
            if(id){
                getUser({id}).then(v=> this.users = v)
                this.model = await getById(id)
            }
        },
        handleExists(user){
            this.$message.warning(`成员${user.userName}已存在`);
        },
        onSubmit() {
            this.$refs.form.validate(async (valid) => {
                if (valid) {
                    try {
                        this.loading = true;
                        const userIds = this.users.map(v=>v.id)
                        await save({userIds,...this.model});
                        this.$notify.success((this.model.id ? '修改':'新增' )+ '成功');
                        this.visible = false;
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
