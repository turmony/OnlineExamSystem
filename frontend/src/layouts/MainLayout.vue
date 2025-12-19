<template>
  <a-layout class="main-layout">
    <a-layout-sider collapsible>
      <div class="logo">Online Exam</div>
      <a-menu
        :selected-keys="[selectedKey]"
        @menu-item-click="onMenuClick"
      >
        <a-menu-item key="dashboard">仪表盘</a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-header class="header">
        <div class="header-right">
          <span class="username">{{ username || '未登录' }}</span>
        </div>
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const selectedKey = computed(() => route.name || 'dashboard')

const username = computed(() => userStore.userInfo?.realName || userStore.userInfo?.username)

const onMenuClick = (key) => {
  if (key === 'dashboard') {
    router.push('/dashboard')
  }
}
</script>

<style scoped>
.main-layout {
  min-height: 100vh;
}

.logo {
  height: 48px;
  margin: 16px;
  color: #fff;
  font-weight: 600;
  display: flex;
  align-items: center;
}

.header {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0 24px;
  background: #fff;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.content {
  margin: 16px;
  padding: 16px;
  background: #fff;
}
</style>


