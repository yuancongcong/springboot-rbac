const stroage = window.sessionStorage

/**
 * 存储sessionStorage
 */
export const setStore = (name, content) => {
  if (!name) return;
  if (typeof content !== 'string') {
    content = JSON.stringify(content);
  }
  stroage.setItem(name, content);
}

/**
 * 获取sessionStorage
 */
export const getStore = (name,type) => {
  if (!name) return;
  let val = stroage.getItem(name);
  if(type === 'json'){
    return JSON.parse(val)
  }
  return val
}


const TOKEN_STR = 'x-auth-token'
/**
 * 删除sessionStorage
 */
export const removeStore = name => {
  if (!name) return;
  stroage.removeItem(name);
}


export const getToken = ()=> getStore(TOKEN_STR)

export const setToken = (val) => setStore(TOKEN_STR,val)
