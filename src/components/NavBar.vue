<template>
  <view class="nav-bar">
    <view class="nav-bar-status-bar" :style="{ height: statusBarHeight + 'px' }"></view>
    <view class="nav-bar-content">
      <view class="nav-bar-left" @click="handleBack" v-if="showBack">
        <text class="nav-bar-back-icon">‹</text>
      </view>
      <view class="nav-bar-left" v-else></view>
      <view class="nav-bar-title">{{ title }}</view>
      <view class="nav-bar-right">
        <slot name="right"></slot>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue'

defineProps<{
  title: string
  showBack?: boolean
}>()

const statusBarHeight = ref(44)

uni.getSystemInfo({
  success: (res) => {
    statusBarHeight.value = res.statusBarHeight || 44
  }
})

const handleBack = () => {
  uni.navigateBack({ delta: 1 })
}
</script>

<style lang="scss" scoped>
.nav-bar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 999;
  background: $primary-color;
}

.nav-bar-status-bar {
  width: 100%;
}

.nav-bar-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 88rpx;
  padding: 0 $spacing-md;
}

.nav-bar-left {
  width: 100rpx;
  display: flex;
  align-items: center;
}

.nav-bar-back-icon {
  font-size: 48rpx;
  color: #FFFFFF;
  font-weight: bold;
}

.nav-bar-title {
  flex: 1;
  text-align: center;
  font-size: $font-size-lg;
  font-weight: 500;
  color: #FFFFFF;
}

.nav-bar-right {
  width: 100rpx;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
</style>