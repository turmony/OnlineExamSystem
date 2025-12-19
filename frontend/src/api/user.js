import request from '../utils/request'

export const getUserList = (params) => {
  return request.get('/user/page', { params })
}


