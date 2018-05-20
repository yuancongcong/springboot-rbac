import axios from 'axios';


export const deleteByIds = params =>  axios.post('/system/log/deleteByIds', params)

export const save = params =>  axios.post('/system/log/save', params)

export const getById = id => axios.post('/system/log/get',{id} )

export const getListPage = params =>  axios.post('/system/log/listPage', params)
