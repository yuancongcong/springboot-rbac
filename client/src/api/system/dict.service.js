import axios from 'axios';


export const getById = id => axios.post(`/system/dict/get`,{id} )

export const getListPage = params =>  axios.post('/system/dict/listPage', params)

export const getList = params =>  axios.post('/system/dict/list', params)

export const deleteByIds = params =>  axios.post('/system/dict/deleteByIds', params)

export const save = params =>  axios.post('/system/dict/save', params)
