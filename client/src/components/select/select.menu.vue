<template>
    <el-cascader :options="menus" :props="defaultProps"  v-model="currentValue" @change="handelChange"
     change-on-select filterable show-all-levels clearable :placeholder="placeholder">
    </el-cascader>
</template>

<script>
import { mapState } from 'vuex'

export default {
    props: ['value', 'placeholder'],
    data() {
        return {
            currentValue: [],
            defaultProps: {
                children: 'children',
                label: 'name',
                value: 'id',
            },
        }
    },
    created(){
        this.setCurrentValue();
    },
    computed: {
        ...mapState([
            'menus'
        ])
    },
    watch: {
        value(v) {
            this.setCurrentValue();
        }
    },
    methods: {
        handelChange(value) {
            let val = this.currentValue.join(',');
            this.$emit('input',val);
            this.$emit('change',val);
        },
        setCurrentValue(){
            this.currentValue = this.value ? this.value.split(',').map(v=>parseInt(v)) : [];
        }
    }
}
</script>