import axios from 'axios'

export const getById = id => axios.post(`/system/role/get`,{id} )

export const getList = params =>  axios.post('/system/role/list', params)

export const getListPage = params =>  axios.post('/system/role/listPage', params)

export const deleteByIds = params =>  axios.post('/system/role/deleteByIds', params)

export const save = params =>  axios.post('/system/role/save', params)

export const getMenus = params => axios.post('/system/role/getMenus', params)

export const getUser = params => axios.post('/system/role/getUser', params)

export const querySearch = params =>  axios.post('/system/role/querySearch', params)

export const getByUserName = params => axios.post(`/system/role/getByUserName`,params )
