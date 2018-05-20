import { setStore,getStore,removeStore,setToken} from '../util/storage'
import * as types from './mutation-types'

export default {
  //记录用户本地信息
  [types.RECORD_USERINFO](state, userinfo) {
    state.user = userinfo;
    setStore('userinfo', userinfo)
  },
  //登录成功
  [types.LOGIN_SUCCESS](state, token) {
    state.login = true;
    setToken(token)
  },
  //初始化用户信息
  [types.GET_USERINFO](state) {
    console.log('get userinfo')
    const data = getStore('userinfo', 'json')
    if(data){
      state.user = data
    }
  },
  //记录菜单
  [types.RECORD_MENU_LIST](state,menus) {
    state.menus = menus;
    setStore('menus', menus)
    //加载二级菜单
    this.commit(types.SET_CUR_MENUS)
  },
  //获取本地菜单
  [types.GET_MENU_LIST](state) {
    console.log('get menus')
    const data = getStore('menus', 'json')
    if(data){
      state.menus = data
    }
    //加载二级菜单
    this.commit(types.SET_CUR_MENUS)
  },
  [types.USER_LOGOUT](state, info) {
    setToken('');
    state.menus = null;
    state.subMenus = null;
    removeStore('userinfo')
  },
  [types.GET_ORG_LIST](state, data) {
    state.orgList = data
  },
  [types.SET_CUR_MENUS](state, path = getStore('path')) {
    //根据path获取最后一级菜单
    const menu = getParenMenu(state,path)
    //获取顶级菜单
    const topMenu = getTopParenMenu(menu)
    state.subMenus = topMenu ? topMenu.children : []
  }
}

function getTopParenMenu(menu){
  return menu && menu.parent ? getTopParenMenu(menu.parent) :menu;
}

function getParenMenu(state,path){
  const menu = state.menuMap[path];
  return (menu || !path) ? menu : getParenMenu(state,function getParenMenu(path){
    let index = path.lastIndexOf('/');
    return path.substr(0,index);
  }(path));
}
