<template>
  <view class="orders-page">
    <view class="orders-content">
      <view class="section-header">
        <text class="section-title">长期医嘱</text>
      </view>
      
      <view class="order-list">
        <view 
          v-for="order in longOrders" 
          :key="order.id"
          class="order-item"
        >
          <view class="order-content">
            <text class="order-medicine">{{ order.medicineName }} {{ order.dosage }} {{ order.method }}</text>
            <text class="order-operator">{{ order.operator }} {{ order.createTime }}</text>
            <text class="order-remark">备注：</text>
          </view>
        </view>
      </view>
      
      <view class="section-header">
        <text class="section-title">临时医嘱</text>
      </view>
      
      <view class="order-list">
        <view 
          v-for="order in tempOrders" 
          :key="order.id"
          class="order-item"
        >
          <view class="order-content">
            <text class="order-medicine">{{ order.medicineName }} {{ order.dosage }} {{ order.method }}</text>
            <text class="order-operator">{{ order.operator }} {{ order.createTime }}</text>
          </view>
          <view 
            class="order-action"
            :class="{ executed: order.status === 'executed' }"
            @click="handleExecute(order)"
          >
            <text>{{ order.status === 'executed' ? '已执行' : '执行' }}</text>
          </view>
        </view>
      </view>
    </view>
    
    <view class="bottom-tabs">
      <view 
        class="bottom-tab-item" 
        @click="navigateToDetail()"
      >
        <text class="tab-icon">☰</text>
        <text class="tab-text">治疗信息</text>
      </view>
      <view 
        class="bottom-tab-item active"
      >
        <text class="tab-icon">💊</text>
        <text class="tab-text">临时医嘱</text>
      </view>
      <view 
        class="bottom-tab-item"
        @click="navigateToParams()"
      >
        <text class="tab-icon">📊</text>
        <text class="tab-text">透析参数</text>
      </view>
      <view 
        class="bottom-tab-item"
        @click="navigateToSummary()"
      >
        <text class="tab-icon">📝</text>
        <text class="tab-text">透析小结</text>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useAppStore, type MedicalOrder } from '@/stores/app'

const store = useAppStore()
const longOrders = ref(store.longOrders)
const tempOrders = ref(store.tempOrders)

const handleExecute = (order: MedicalOrder) => {
  if (order.status === 'executed') {
    uni.showToast({ title: '该医嘱已执行', icon: 'none' })
    return
  }
  
  uni.showModal({
    title: '确认执行',
    content: `确定执行 "${order.medicineName}" 医嘱吗？`,
    success: (res) => {
      if (res.confirm) {
        store.executeOrder(order.id)
        tempOrders.value = [...store.tempOrders]
        uni.showToast({ title: '执行成功', icon: 'success' })
      }
    }
  })
}

const navigateToDetail = () => {
  uni.navigateBack()
}

const navigateToParams = () => {
  uni.navigateTo({ url: '/pages/dialysis-param/index' })
}

const navigateToSummary = () => {
  uni.navigateTo({ url: '/pages/summary/index' })
}
</script>

<style lang="scss" scoped>
.orders-page {
  min-height: 100vh;
  background: $background-color;
  padding-top: 88rpx;
  padding-bottom: 120rpx;
}

.orders-content {
  padding: $spacing-md;
}

.section-header {
  padding: $spacing-md 0;
}

.section-title {
  font-size: $font-size-lg;
  font-weight: 600;
  color: $text-primary;
}

.order-list {
  margin-bottom: $spacing-lg;
}

.order-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: $card-background;
  padding: $spacing-md;
  margin-bottom: $spacing-sm;
  border-radius: $radius-md;
}

.order-content {
  flex: 1;
}

.order-medicine {
  display: block;
  font-size: $font-size-base;
  color: $text-primary;
  margin-bottom: $spacing-xs;
}

.order-operator {
  display: block;
  font-size: $font-size-sm;
  color: $text-hint;
  margin-bottom: $spacing-xs;
}

.order-remark {
  font-size: $font-size-sm;
  color: $text-hint;
}

.order-action {
  padding: $spacing-sm $spacing-md;
  background: $primary-color;
  color: #FFFFFF;
  border-radius: $radius-md;
  font-size: $font-size-sm;
  
  &.executed {
    background: $text-hint;
  }
}

.bottom-tabs {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  background: $card-background;
  padding-bottom: env(safe-area-inset-bottom);
  box-shadow: 0 -2rpx 10rpx rgba(0, 0, 0, 0.05);
}

.bottom-tab-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100rpx;
  
  &.active {
    .tab-text {
      color: $primary-color;
    }
  }
}

.tab-icon {
  font-size: $font-size-xl;
  margin-bottom: $spacing-xs;
}

.tab-text {
  font-size: $font-size-xs;
  color: $text-hint;
}

page {
  background: $background-color;
}
</style>