<template>
    <el-select v-model="currentValue" placeholder="请选择" @change="handelChange">
        <el-option
            v-for="item in options"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
  </el-select>
</template>

<script>
import { getList } from '@/api/system/dict.service'
export default {
    props:{
        type:String,
        value : String
    },
    data(){
        return {
            options:[],
            currentValue: this.value,
        }
    },
    watch: {
        value(v) {
            this.currentValue = v;
        }
    },
    async created(){
        this.options = await getList({code:this.type});
    },
    methods:{
        handelChange(v){
            this.$emit('input', v);
        }
    }
}
</script>

<style>

</style>
