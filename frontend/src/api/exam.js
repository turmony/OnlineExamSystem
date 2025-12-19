import request from '../utils/request'

export const getExamPage = (params) => {
  return request.get('/exam/page', { params })
}


