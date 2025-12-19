import request from '../utils/request'

export const getPaperPage = (params) => {
  return request.get('/paper/page', { params })
}


