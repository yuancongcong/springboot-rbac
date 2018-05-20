import axios from 'axios'


export const deleteByIds = params =>  axios.post('/system/permission/deleteByIds', params)

export const save = params =>  axios.post('/system/permission/save', params)

export const getById = id => axios.post('/system/permission/get',{id} )

export const getListPage = params =>  axios.post('/system/permission/listPage', params)

export const getAllRoles = params => axios.post(`/system/user/getAllRoles`,params )
