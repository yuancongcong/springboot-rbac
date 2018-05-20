import axios from 'axios'
import { getToken} from '../util/storage'

axios.defaults.baseURL = process.env.BASE_API_URL || ''

// axios请求拦截器
axios.interceptors.request.use(config =>{
  const token = getToken();
  if (token) {
    config.data = config.data || {};
    config.data['x-auth-token'] = token
  }
  return config;
},err => {
  return Promise.reject(error)
})


axios.interceptors.response.use(response => {
  const data = response.data
  switch (data.status) {
    //返回成功数据
    case 'success':{
      response.data = data.data;
      return data.data;
    }
    default:{
      const err = new Error(data.data)
      err.data = data;
      err.response = response;
      throw err;
    }
  }
},error=>{
  return Promise.reject(error)
})

axios.defaults.transformRequest.unshift((data, headers)=> {
  var key, result = [];
  for (key in data) {
    if (data.hasOwnProperty(key) && data[key] !== undefined && data[key] != null)
    result.push(encodeURIComponent(key) + "=" + encodeURIComponent(data[key]));
  }
  return result.join("&");
})

export default axios
