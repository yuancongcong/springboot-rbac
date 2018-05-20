<template>
    <el-cascader :options="options" :props="props" clearable v-model="currentValue" @change="handelChange" :change-on-select="changeOnSelect" :filterable="filterable" :placeholder="placeholder">
    </el-cascader>
</template>

<script>
import { getList } from '@/api/system/dict.service'
export default {
    props: {
        value: String,
        changeOnSelect: Boolean,
        filterable: Boolean,
        placeholder:String,
    },
    data() {
        return {
            currentValue: [],
            options:[],
            props: {
                children: 'children',
                label: 'name',
                value: 'code',
            },
        }
    },
    watch: {
        value(v) {
            this.setCurrentValue()
        }
    },
    async created(){
        this.setCurrentValue(this.value);
        this.options = await getList({code:'city_list',isRepeat:true});
    },
    methods: {
        handelChange(value) {
            let val = value.join(',');
            this.$emit('change', val);
            this.$emit('input', val);
        },
        setCurrentValue(){
            this.currentValue = this.value ? this.value.split(',') : [];
        }
    }
}
</script>
