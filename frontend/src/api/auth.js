import request from '../utils/request'

export const login = (data) => {
  return request.post('/auth/login', data)
}

export const getUserInfo = () => {
  return request.get('/auth/info')
}

export const logout = () => {
  return request.post('/auth/logout')
}


