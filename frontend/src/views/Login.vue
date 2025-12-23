<template>
  <div style="max-width:360px;margin:100px auto;">
    <h2>登录</h2>
    <form @submit.prevent="onSubmit">
      <div>
        <input v-model="username" placeholder="用户名" />
      </div>
      <div style="margin-top:8px;">
        <input v-model="password" type="password" placeholder="密码" />
      </div>
      <div style="margin-top:12px;">
        <button type="submit">登录</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { login } from '@/api/auth'

const username = ref('')
const password = ref('')

const onSubmit = async () => {
  try {
    const res = await login({ username: username.value, password: password.value })
    // 简化：把 token 存 localStorage
    if (res && res.data && res.data.token) {
      localStorage.setItem('token', res.data.token)
      alert('登录成功')
    } else {
      alert('登录失败')
    }
  } catch (e) {
    alert('登录异常')
  }
}
</script>

<style scoped>
input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}
button {
  width: 100%;
  padding: 8px;
}
</style>


