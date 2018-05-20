<template>
    <div :class="{'collapsed':collapsed}" >
        <my-header @collapse="collapse"></my-header>
        <my-menu class="main-sidebar" :menus="subMenus" :collapse="collapsed"></my-menu>
        <div class="content-wrapper" :style="{minHeight:curContentHeigt+'px'}">
            <div class="content body">
                <transition name="fade" mode="out-in">
                    <router-view></router-view>
                </transition>
            </div>
        </div>
    </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
import myMenu from '@/components/menu/menu.vue'
import myHeader from '@/components/header/header'

export default {
    components: {myHeader,myMenu },
    data() {
      return {
          collapsed: false,
          get curContentHeigt(){
              return window.innerHeight-50;
          }
      }
    },
    created() {
      this.GET_USERINFO()
      this.GET_MENU_LIST(this.$route.path)
    },
    computed: {
      ...mapState([
          'curMenu', 'sysName', 'subMenus', 'breadcrumb'
      ])
    },
    methods: {
      ...mapMutations([
          'GET_USERINFO','GET_MENU_LIST','USER_LOGOUT'
      ]),
      //退出登录
      logout: function() {
          this.$confirm('确认退出吗?', '提示').then(() => {
              this.USER_LOGOUT()
              this.$router.push('/login')
          })
      },
      //折叠导航栏
      collapse: function() {
          this.collapsed = !this.collapsed
      },
    }
}
</script>
