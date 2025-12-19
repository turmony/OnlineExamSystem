import { reactive } from 'vue'

const state = reactive({
  token: localStorage.getItem('token') || '',
  userInfo: JSON.parse(localStorage.getItem('userInfo') || 'null'),
})

export const useUserStore = () => {
  const setToken = (token) => {
    state.token = token
    if (token) {
      localStorage.setItem('token', token)
    } else {
      localStorage.removeItem('token')
    }
  }

  const setUserInfo = (userInfo) => {
    state.userInfo = userInfo
    if (userInfo) {
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
    } else {
      localStorage.removeItem('userInfo')
    }
  }

  const logout = () => {
    setToken('')
    setUserInfo(null)
  }

  return {
    ...state,
    setToken,
    setUserInfo,
    logout,
  }
}


