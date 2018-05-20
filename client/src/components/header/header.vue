<template>
  <div class="main-header">
        <router-link :to="user.flag || '/'" class="logo">
            <span class="header-title">灵犀科技</span>
            <span class="header-version">1.0.0</span>
        </router-link>
        <nav class="navbar" role="navigation">
            <a href="#" class="sidebar-toggle" @click.prevent="collapse">
                <i class="el-icon-fa-align-justify"></i>
            </a>
            <top-menu class="nav-top-menu" :menus="menus" @select="selectMenu"></top-menu>
            <ul class="navbar-custom-menu">
                <li class="userinfo">
                    <router-link to="/" class="menu">{{user.realName}}&nbsp;
                    <img src="../../assets/user.png"/></router-link>
                </li>
                <li class="logout">
                    <a class="menu" @click="logout">注销</a>
                </li>
            </ul>
        </nav>
    </div>
</template>

<script>
import topMenu from "./topmenu"
import { mapState, mapMutations,mapActions } from 'vuex'
export default {
    components:{topMenu},
    computed: {
        ...mapState([
            'user','system','menus'
        ])
    },
    methods:{
        ...mapMutations([
            'USER_LOGOUT'
        ]),
         //退出登录
        logout: function() {
            this.$confirm('确认退出吗?', '提示').then(() => {
                this.USER_LOGOUT()
                this.$router.push('/login')
            })
        },
        collapse(){
            this.$emit('collapse');
        },
        selectMenu(menu){
            this.$router.push(menu.url)
        }
    }
}
</script>

<style lang="scss">
@import "../../style/base/variables";
.main-header {
    position: relative;
    max-height: 100px;
    z-index: 999;
    .logo {
        background-color: $--color-primary;
        color: #fff;
        border-bottom: 0 solid transparent;
        -webkit-transition: width .3s ease-in-out;
        transition: width .3s ease-in-out;
        display: block;
        float: left;
        height: 50px;
        font-size: 20px;
        line-height: 50px;
        text-align: center;
        width: 230px;
        font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
        padding: 0 15px;
        font-weight: 300;
        overflow: hidden;
        span.header-version {
            font-size: small;
        }
    }
    .navbar{
        background-color: $--color-primary-light-1;
        transition: margin-left .3s ease-in-out;
        margin-bottom: 0;
        margin-left: 230px;
        border: none;
        min-height: 50px;
        border-radius: 0;
        display: flex;
        .nav-top-menu{
            flex:1;
        }
        .sidebar-toggle{
            color:#fff;
            padding: 15px 15px;
            font-size: small;
        }

        .navbar-custom-menu{
            width: 220px;
            text-align: right;
            >li{
                display: inline-block;
                line-height: 50px;
            }
            .userinfo{
                line-height: 50px;
                padding: 0 15px;
                img {
                    vertical-align: middle;
                    width: 25px;
                    height: 25px;
                    border-radius: 20px;
                }
            }
            .el-dropdown-menu{
                background-color:#fff;
            }
            .el-dropdown,.menu{
                display: block;
                color:#fff;
                padding: 0px 15px;
                a{
                    color:#fff;
                }
                &:hover{
                    background: $--color-primary-light-2;
                }
            }
            .logout{
                background: $--color-primary-light-2;
            }
        }
    }
}

.nav>li>a:hover, .nav>li>a:active, .nav>li>a:focus {
    color: #444;
    background: #f7f7f7;
}
.nav>li>a:focus, .nav>li>a:hover {
    text-decoration: none;
    background-color: #eee;
}

</style>

