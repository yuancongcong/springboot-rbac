<template>
    <el-tree :data="menus" :props="defaultProps" show-checkbox @check-change="handelChange"
    check-strictly node-key="id" :default-expanded-keys="menus.map(v=> v.id)" ref="tree"></el-tree>
</template>

<script>
import { mapState } from 'vuex'

export default {
    props: ['value'],
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
    watch: {
        value(v) {
            this.$refs.tree.setCheckedKeys(v);
        }
    },
    computed:{
        ...mapState([
            'menus'
        ])
    },
    methods: {
        handelChange(value,checked,indeterminate) {
            let data = this.$refs.tree.getCheckedKeys();
            this.currentValue = data;
            this.$emit('input', data);
            this.$emit('change', data);
        },
    }
}
</script>