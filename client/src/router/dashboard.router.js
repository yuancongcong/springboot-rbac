export default [
  //我的面板
  { path: 'dashboard',redirect:'/dashboard/info'},
  { path: 'dashboard/changePassword',component: r => require(['@/containers/dashboard/changePassword'],r)},
  { path: 'dashboard/info',component: r => require(['@/containers/dashboard/info'],r)},
  /*insert router*/
]
