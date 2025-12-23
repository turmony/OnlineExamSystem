import request from './request'

export const login = (data) => {
  return request.post('/auth/login', data)
}

export const logout = () => {
  return request.post('/auth/logout')
}

export const getInfo = () => {
  return request.get('/auth/info')
}


