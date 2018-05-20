<template>
    <div>
        <ul>
            <li v-for="user in value" :key="user.id">
                {{user.userName}} &nbsp;
                <el-button type="text" size="small" @click="handleRemove(user)">删除</el-button>
            </li>
        </ul>
        <el-autocomplete
            v-model="state"
            :fetch-suggestions="querySearchAsync"
            @select="handleSelect"
            value-key="userName"
            ></el-autocomplete>
            <el-button @click="handleAdd">新增</el-button>
    </div>
    <!-- <el-tree :data="menus" :props="defaultProps" show-checkbox @check-change="handelChange"
    check-strictly node-key="id" :default-expanded-keys="menus.map(v=> v.id)" ref="tree"></el-tree> -->
</template>

<script>
import { mapState } from 'vuex'
import { querySearch,getByUserName } from '@/api/system/role.service'

export default {
    props: ['value'],
    data() {
        return {
            state:'',
            defaultProps: {
                children: 'children',
                label: 'name',
                value: 'id',
            },
        }
    },
    methods: {
        async querySearchAsync(queryString, cb) {
            if(!queryString) return;

            let result = await querySearch({search:queryString});
            if(result && result.length>0){
                cb(result);
            }
        },
        handleSelect(item) {
            this.state = item.userName;
        },
        async handleAdd(){
            let user = await getByUserName({ userName:this.state});
            if(!user) return;
            for(var i in this.value){
                if(user.userName === this.value[i].userName){
                    this.$emit('exists',user);
                    return;
                }
            }
            let newUsers = [...this.value,user];
            this.$emit('input',newUsers)
        },
        handleRemove(user){
            let newUsers = this.value.filter(v=> v !== user)
            this.$emit('input',newUsers)
        },
        handelChange(value,checked,indeterminate) {
            let data = this.$refs.tree.getCheckedKeys();
            this.currentValue = data;
            this.$emit('input', data);
            this.$emit('change', data);
        },
    }
}
</script>
