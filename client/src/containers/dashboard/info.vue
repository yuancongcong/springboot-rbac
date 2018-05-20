<template>
    <el-form ref="form" :model="user" :rules="rules" class="c-form" label-width="150px">
        <el-form-item label="头像">
            <el-button type="text">
                <img src="../../assets/user.png" />
            </el-button>
        </el-form-item>
        <el-form-item label="用户名">
            <el-input v-model="model.userName" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="realName">
            <el-input v-model="model.realName"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
            <el-input v-model="model.email"></el-input>
        </el-form-item>
        <el-form-item label="电话">
            <el-input v-model="model.tel"></el-input>
        </el-form-item>
        <el-form-item label="LOGO链接">
            <el-input v-model="model.flag"></el-input>
        </el-form-item>
         <el-form-item label="所在城市">
            <select-city v-model="model.city"></select-city>
        </el-form-item>
        <el-form-item label="备注">
            <el-input v-model="model.remark" type="textarea"></el-input>
        </el-form-item>
        <el-form-item label="最后一次登录时间">
            <el-input v-model="model.lastLoginTime" disabled></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="onSubmit" :loading="loading">提交</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import { mapState,mapActions } from 'vuex'
import selectCity from '@/components/select/select.city'

export default {
    components:{selectCity},
    data() {
        return {
            loading:false,
            model:{},
            rules: {
                realName: [
                    { required: true, message: '请填写姓名', trigger: 'blur' },
                ],
            },
        }
    },
    computed: {
        ...mapState([
            'user'
        ])
    },
    mounted(){
      this.model = { ...this.user};
    },
    methods: {
      ...mapActions(['saveCurrentUser']),
      onSubmit() {
          this.$refs.form.validate(async (valid) => {
              if (valid) {
                  try {
                      this.loading = true
                      this.user = await this.saveCurrentUser(this.model)
                      this.$notify.success(`保存成功`)
                  } catch (e) {
                      this.$notify.error(e.message)
                  } finally {
                      this.loading = false;
                  }
              }
          });
      }
    }
}
</script>
<style lang="scss" scoped>
img {
    width: 100px;
    height: 100px;
}
</style>
