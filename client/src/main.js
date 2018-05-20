// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import { getToken , setStore} from './util/storage'
import store from './store'
import './api/config'
import ElementUI from 'element-ui'
import './style/style.scss'

Vue.use(ElementUI)

Vue.config.productionTip = false


//登录拦截
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requireAuth)) {
    if (!getToken()) {
      return next({
        path: '/login',
        query: {
          redirect: to.fullPath
        }
      })
    }
  }

  setStore('path',to.path)//记录当前路由path
  store.commit('SET_CUR_MENUS') //更新二级菜单
  next() // 确保一定要调用 next()
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
