<template>
    <div class="login-bg">
        <div class="login-box">
            <div class="logo">灵犀科技管理后台</div>
            <el-form class="login-form" :model="form" :rules="rules"  ref="form" label-width="0">
                <el-form-item prop="username">
                    <el-input v-model="form.username" placeholder="用户名">
                        <template slot="prepend"><i class="el-icon-fa-user"></i></template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="form.password" type="password" placeholder="密码" @keyup.enter.native="onSubmit">
                        <template slot="prepend"><i class="el-icon-fa-lock"></i></template>
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" class="w-100" @click="onSubmit" :loading="loading">登录</el-button>
                </el-form-item>
            </el-form>
        </div>
     </div>
</template>

<script>
import { mapActions } from 'vuex'


export default {
    data() {
        return {
            loading:false,
            form: {
                username: 'admin',
                password: '123456',
            },
            rules: {
                username: [
                    { required: true, message: '请填写用户名', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: '请填写密码', trigger: 'blur' },
                ]
            }
        }
    },
    methods: {
         ...mapActions([
            'login'
        ]),
        onSubmit() {
            this.$refs.form.validate(async (valid) => {
                if (valid) {
                    try {
                        this.loading = true
                        const {username,password} = this.form
                        await this.login(this.form)
                        this.$router.push(this.$route.query.redirect || '/')
                    } catch (e) {
                        this.$message.error(e.message)
                    }finally{
                        this.loading = false
                    }
                }
            });
        }
    }
}
</script>
<style>
.login-bg{
    background: url(../../assets/loginBg.jpg) no-repeat center center;
    background-size: cover;
}
</style>
