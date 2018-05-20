import axios from 'axios';

export const getById = id => axios.post(`/system/menu/get`, {id})

export const getList = params => axios.post('/system/menu/list', params)

export const deleteByIds = params => axios.post('/system/menu/deleteByIds', params)

export const save = params => axios.post('/system/menu/save', params)
