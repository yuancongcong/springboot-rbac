import axios from 'axios'

export const getById = id => axios.post(`/system/user/get`,{id} )

export const login = (username,password) => axios.post('/login', {username,password})

export const getListPage = params =>  axios.post('/system/user/listPage', params)


export const deleteByIds = params =>  axios.post('/system/user/deleteByIds', params)

export const save = params =>  axios.post('/system/user/save', params)

export const getAllRoles = params => axios.post(`/system/user/getAllRoles`,params )

export const getUserInfo = () => axios.post(`/index/user/info`)

export const changePassword = params => axios.post(`/index/user/changePassword`,params)
