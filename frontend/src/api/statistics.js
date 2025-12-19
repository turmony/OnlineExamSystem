import request from '../utils/request'

export const getDashboardStatistics = () => {
  return request.get('/statistics/dashboard')
}


