import request from '../utils/request'

export const getQuestionPage = (params) => {
  return request.get('/question/page', { params })
}


