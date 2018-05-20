import axios from 'axios'


export const deleteByIds = params =>  axios.post('/system/organization/deleteByIds', params)

export const save = params =>  axios.post('/system/organization/save', params)

export const getById = id => axios.post('/system/organization/get',{id} )

export const getListPage = params =>  axios.post('/system/organization/listPage', params)
