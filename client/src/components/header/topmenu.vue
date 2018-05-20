<template>
    <ul  class="navbar-nav">
        <li v-for="menu in menus" :key="menu.id">
            <a href="javascript:void(0)" @click="$emit('select',menu)">
                <i :class="'el-icon-'+menu.icon"></i>
                <span>{{menu.name}}</span>
            </a></li>
    </ul>
</template>

<script>
export default {
    props: ['defautActive','menus'],
}
</script>

 <style lang="scss" scoped>
 @import "../../style/base/variables";
    .navbar-nav{
        >li{
            display: inline-block;
            line-height: 50px;
            a{
                color:#fff;
                display: block;
                padding: 0 10px;
                .active,&:hover{
                    background:  $--color-primary;
                }
            }
        }
    }
</style>

