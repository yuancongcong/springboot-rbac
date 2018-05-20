<template>
    <el-form ref="form" :model="form" :rules="rules" class="c-form" label-width="150px">
        <el-form-item label="原始密码" prop="oldPassword">
            <el-input v-model="form.oldPassword" type="password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="password">
            <el-input v-model="form.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="rePassword">
            <el-input v-model="form.rePassword" type="password"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit" :loading="loading">提交</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import { changePassword } from '@/api/system/user.service'

export default {
    data() {
        return {
            loading:false,
            form: {
                oldPassword: '',
                password: '',
                rePassword: '',
            },
            rules: {
                oldPassword: [
                    { required: true, message: '请填写旧密码', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: '请填写新密码', trigger: 'blur' },
                    { validator:(rule, value, callback) =>{
                        if (value.length < 6) {
                            callback(new Error('请输入6位数以上的密码'));
                        }else{
                            callback();
                        }
                    }, trigger: 'blur' },
                ],
                rePassword: [
                    { required: true, message: '请再次填写新密码', trigger: 'blur' },
                    { validator:(rule, value, callback) =>{
                        if (value !== this.form.password) {
                            callback(new Error('两次输入密码不一致!'));
                        }else{
                            callback();
                        }
                    }, trigger: 'blur' },
                ],
            },
        }
    },
    methods: {
         onSubmit() {
            this.$refs.form.validate(async (valid) => {
                if (valid) {
                    try {
                        this.loading = true;
                        let {password,oldPassword} = this.form;
                        this.user = await changePassword({password,oldPassword});
                        this.$notify.success(`密码修改成功`);
                    } catch (e) {
                        this.$notify.error(e.message);
                    } finally {
                        this.loading = false;
                    }
                }
            });
        }
    }
}
</script>
