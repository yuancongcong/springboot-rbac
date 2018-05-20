<template>
    <el-checkbox-group
        v-model="currentValue">
        <el-checkbox v-for="role in roles" :label="role.id" :key="role.id" @change="handelChange">{{role.name}}</el-checkbox>
    </el-checkbox-group>
</template>

<script>
import { getList } from '@/api/system/role.service'

export default {
    props: ['value', 'placeholder'],
    data() {
        return {
            currentValue: [],
            roles:[],
        }
    },
    created(){
        this.setCurrentValue();
        this.initData();
    },
    watch: {
        value(v) {
            this.setCurrentValue();
        }
    },
    methods: {
        async initData(){
            this.roles = await getList()
        },
        handelChange(value) {
            this.$emit('input', this.currentValue);
            this.$emit('change', this.currentValue);
        },
        setCurrentValue(){
            this.currentValue = this.value ? [...this.value] : [];
        }
    }
}
</script>
