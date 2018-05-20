import { getList as getMenuList } from '../api/system/menu.service'
import { login, getUserInfo,save as saveCurrentUser } from '../api/system/user.service'
import * as types from './mutation-types'

export default {
  async login({dispatch,commit},{username,password}){
    let token = await login(username,password)
    commit(types.LOGIN_SUCCESS,token)
    dispatch('getUserInfo')
    dispatch('getMenuList')
  },
  async getMenuList({state,commit}) {
    let list = await getMenuList()
    commit(types.RECORD_MENU_LIST, list)
  },
  async getUserInfo({state,commit}) {
    let userinfo = await getUserInfo();
    commit(types.RECORD_USERINFO, userinfo)
  },
  async saveCurrentUser({dispatch},data){
    await saveCurrentUser(data)
    return dispatch('getUserInfo')
  }
}
