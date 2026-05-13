<template>
  <view class="settings-page">
    <view class="settings-content">
      <view class="tips-text">温馨提示：设置完ip地址后，请直接按返回键</view>
      
      <view class="ip-input-wrapper">
        <input 
          class="ip-input" 
          v-model="ipAddress" 
          placeholder="请输入IP地址"
          type="text"
        />
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useAppStore } from '@/stores/app'

const store = useAppStore()
const ipAddress = ref('')

onMounted(() => {
  ipAddress.value = store.baseUrl.replace('http://', '')
})

onUnmounted(() => {
  if (ipAddress.value) {
    const url = ipAddress.value.startsWith('http') ? ipAddress.value : 'http://' + ipAddress.value
    store.saveBaseUrl(url)
  }
})
</script>

<style lang="scss" scoped>
.settings-page {
  min-height: 100vh;
  background: $background-color;
  padding-top: 88rpx;
}

.settings-content {
  padding: $spacing-xl $spacing-lg;
}

.tips-text {
  font-size: $font-size-sm;
  color: $text-hint;
  margin-bottom: $spacing-lg;
}

.ip-input-wrapper {
  border-bottom: 2rpx solid $primary-color;
}

.ip-input {
  width: 100%;
  height: 88rpx;
  font-size: $font-size-lg;
  background: transparent;
}

page {
  background: $background-color;
}
</style>