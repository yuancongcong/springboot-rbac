export default [
  { path: 'system',redirect:'system/user' },
  //用户管理
  { path: 'system/user', component: r => require(['@/containers/system/user/user'], r) },
  { path: 'system/user/add', component: r => require(['@/containers/system/user/user.edit'], r) },
  { path: 'system/user/edit/:id', component: r => require(['@/containers/system/user/user.edit'], r)},
  //系统管理
  { path: 'system/menu', component: r => require(['@/containers/system/menu/menu'], r) },
  { path: 'system/menu/add', component: r => require(['@/containers/system/menu/menu.edit'], r) },
  { path: 'system/menu/add/:pids', component: r => require(['@/containers/system/menu/menu.edit'], r) },
  { path: 'system/menu/edit/:id', component: r => require(['@/containers/system/menu/menu.edit'], r)},
  //角色管理
  { path: 'system/role', component: r => require(['@/containers/system/role/role'], r) },
  { path: 'system/role/add', component: r => require(['@/containers/system/role/role.edit'], r) },
  { path: 'system/role/edit/:id', component: r => require(['@/containers/system/role/role.edit'], r)},
  { path: 'system/role/menu-setting/:id', component: r => require(['@/containers/system/role/role.menu-setting'], r)},
  // //字段管理
  { path: 'system/dict', component: r => require(['@/containers/system/dict/dict'], r) },
  //访问管理
  { path: 'system/permission', component: r => require(['@/containers/system/permission/permission'], r) },
  { path: 'system/permission/add', component: r => require(['@/containers/system/permission/permission.edit'], r) },
  { path: 'system/permission/edit/:id', component: r => require(['@/containers/system/permission/permission.edit'], r)},
  /*insert router*/
]
