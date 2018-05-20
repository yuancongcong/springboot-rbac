import Vue from 'vue'
import Router from 'vue-router'
import VueRouter from 'vue-router'
import SystemRouter from './system.router'
import DashboardRouter from './dashboard.router'

Vue.use(Router)
const routes = [
  { path: '/login',component: r => require(['@/containers/login/login'],r)},
  { path: '/', component:  r => require(['@/containers/index'],r) ,
  meta: { requireAuth: true},
  children: [
    { path: '', redirect:'dashboard'},
      ...DashboardRouter,
      ...SystemRouter,
    ]
  },
  { path: '*',component: r => require(['@/containers/404/404'],r)},
]
const router = new VueRouter({
    mode: 'history',
    routes
})
export default router
