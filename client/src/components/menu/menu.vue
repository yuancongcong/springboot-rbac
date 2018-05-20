<template>
    <el-menu v-if="menus" class="menu" :default-active="$route.fullPath" router :collapse="collapse">
        <template v-for="menu in menus">
            <submenu :menu="menu.children" v-if="menu.children && menu.children.length>0" :key="menu.id"></submenu>
            <el-menu-item :index="menu.url" :route="{path:menu.url}" v-else :key="menu.id">
                <i :class="'el-icon-'+menu.icon"></i>
                <span slot="title">{{menu.name}}</span>
            </el-menu-item>
        </template>
    </el-menu>
</template>

<script>
import submenu from './submenu'
export default {
    components: {
        submenu
    },
    props: ['menus', 'collapse']
}
</script>
