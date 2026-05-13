<template>
  <view class="login-page">
    <view class="login-nav-bar">
      <view class="nav-bar-left"></view>
      <view class="nav-bar-title">血透移动系统</view>
      <view class="nav-bar-right" @click="goToSettings">
        <text class="settings-icon">⚙</text>
      </view>
    </view>
    <view class="login-header">
      <view class="logo-container">
        <view class="logo-icon">
          <text class="logo-text">血透</text>
        </view>
        <text class="logo-title">血透移动系统</text>
        <text class="logo-subtitle">护士版</text>
      </view>
    </view>
    
    <view class="login-form">
      <view class="form-input-group">
        <view class="input-wrapper">
          <text class="input-icon">👤</text>
          <input 
            class="form-input" 
            v-model="username" 
            placeholder="请输入用户名"
            type="text"
          />
        </view>
      </view>
      
      <view class="form-input-group">
        <view class="input-wrapper">
          <text class="input-icon">🔒</text>
          <input 
            class="form-input" 
            v-model="password" 
            placeholder="请输入密码"
            type="password"
          />
        </view>
      </view>
      
      <view class="login-btn-container">
        <button class="login-btn" @click="handleLogin">登录</button>
      </view>
    </view>
    
    <view class="login-footer">
      <view class="footer-decoration"></view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useAppStore } from '@/stores/app'

const store = useAppStore()
const username = ref('')
const password = ref('')

onMounted(() => {
  store.loadBaseUrl()
  store.loadUser()
  if (store.token) {
    uni.navigateTo({ url: '/pages/search/index' })
  }
})

const handleLogin = () => {
  if (!username.value) {
    uni.showToast({ title: '请输入用户名', icon: 'none' })
    return
  }
  if (!password.value) {
    uni.showToast({ title: '请输入密码', icon: 'none' })
    return
  }
  
  store.login(username.value, password.value)
  uni.navigateTo({ url: '/pages/search/index' })
}

const goToSettings = () => {
  uni.navigateTo({ url: '/pages/settings/index' })
}
</script>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  background: $background-color;
  display: flex;
  flex-direction: column;
}

.login-nav-bar {
  height: 88rpx;
  background: $primary-color;
  display: flex;
  align-items: center;
  padding: 0 $spacing-md;
}

.nav-bar-left {
  width: 80rpx;
}

.nav-bar-title {
  flex: 1;
  text-align: center;
  font-size: $font-size-lg;
  font-weight: 500;
  color: #FFFFFF;
}

.nav-bar-right {
  width: 80rpx;
  display: flex;
  justify-content: flex-end;
}

.settings-icon {
  font-size: $font-size-xl;
  color: #FFFFFF;
}

.login-header {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: $spacing-xl;
}

.logo-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.logo-icon {
  width: 160rpx;
  height: 160rpx;
  border-radius: 50%;
  background: $primary-color;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: $spacing-lg;
}

.logo-text {
  font-size: 48rpx;
  color: #FFFFFF;
  font-weight: bold;
}

.logo-title {
  font-size: $font-size-xl;
  color: $primary-color;
  font-weight: 600;
  margin-bottom: $spacing-sm;
}

.logo-subtitle {
  font-size: $font-size-sm;
  color: $text-hint;
  background: #E91E63;
  color: #FFFFFF;
  padding: $spacing-xs $spacing-sm;
  border-radius: $radius-sm;
}

.login-form {
  padding: 0 $spacing-lg $spacing-xl;
}

.form-input-group {
  margin-bottom: $spacing-md;
}

.input-wrapper {
  display: flex;
  align-items: center;
  background: $card-background;
  border-radius: $radius-md;
  padding: 0 $spacing-md;
  border: 2rpx solid $border-color;
}

.input-icon {
  font-size: $font-size-lg;
  margin-right: $spacing-sm;
}

.form-input {
  flex: 1;
  height: 88rpx;
  font-size: $font-size-base;
  background: transparent;
}

.login-btn-container {
  margin-top: $spacing-xl;
}

.login-btn {
  width: 100%;
  height: 88rpx;
  background: $primary-color;
  color: #FFFFFF;
  border-radius: $radius-md;
  font-size: $font-size-lg;
  font-weight: 500;
  border: none;
  
  &:active {
    background: $primary-dark;
  }
}

.login-footer {
  padding-bottom: $spacing-xl;
}

.footer-decoration {
  height: 48rpx;
  background: $primary-color;
  border-radius: 48rpx 48rpx 0 0;
}

page {
  background: $background-color;
}
</style>