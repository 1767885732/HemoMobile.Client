<template>
  <view class="orders-page">
    <view class="orders-content">
      <view class="section-header">
        <text class="section-title">长期医嘱</text>
      </view>

      <view class="order-list" v-if="longOrders.length > 0">
        <view
          v-for="order in longOrders"
          :key="order.CURE_DRUG_ID"
          class="order-item"
        >
          <view class="order-content">
            <text class="order-medicine">{{ order.DRUG_NAME }} {{ order.DOSE }}{{ order.DOSE_UNIT }}</text>
            <text class="order-usage">{{ order.USAGE }}{{ order.LONG_USAGE ? ' - ' + order.LONG_USAGE : '' }}</text>
            <text class="order-status" :class="getStateClass(order.STATE)">{{ getStateText(order.STATE) }}</text>
          </view>
        </view>
      </view>
      <view class="empty-state" v-else>
        <text class="empty-text">暂无长期医嘱</text>
      </view>

      <view class="section-header">
        <text class="section-title">临时医嘱</text>
      </view>

      <view class="order-list" v-if="tempOrders.length > 0">
        <view
          v-for="order in tempOrders"
          :key="order.CURE_DRUG_ID"
          class="order-item"
        >
          <view class="order-content">
            <text class="order-medicine">{{ order.DRUG_NAME }} {{ order.DOSE }}{{ order.DOSE_UNIT }}</text>
            <text class="order-usage">{{ order.USAGE }}</text>
            <text class="order-status" :class="getStateClass(order.STATE)">{{ getStateText(order.STATE) }}</text>
          </view>
          <view
            class="order-action"
            :class="{ executed: order.STATE === '2' }"
            @click="handleExecute(order)"
          >
            <text>{{ order.STATE === '2' ? '已执行' : '执行' }}</text>
          </view>
        </view>
      </view>
      <view class="empty-state" v-else>
        <text class="empty-text">暂无临时医嘱</text>
      </view>
    </view>

    <view class="loading-mask" v-if="isLoading">
      <view class="loading-content">
        <text class="loading-text">处理中...</text>
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
import { ref, computed, onMounted } from 'vue'
import { useAppStore } from '@/stores/app'
import type { MedCureDrug, MedCureLongDrug } from '@/utils/types'

const store = useAppStore()

const tempOrders = computed(() => store.tempOrders)
const longOrders = computed(() => store.longOrders)
const isLoading = computed(() => store.isLoading)

const getStateClass = (state: string) => {
  if (state === '2') return 'state-executed'
  if (state === '1') return 'state-pending'
  return ''
}

const getStateText = (state: string) => {
  if (state === '2') return '已执行'
  if (state === '1') return '待执行'
  return state
}

const handleExecute = async (order: MedCureDrug) => {
  if (order.STATE === '2') {
    uni.showToast({ title: '该医嘱已执行', icon: 'none' })
    return
  }

  uni.showModal({
    title: '确认执行',
    content: `确定执行 "${order.DRUG_NAME}" 医嘱吗？`,
    success: async (res) => {
      if (res.confirm) {
        try {
          const hemoId = store.currentPatient?.HEMODIALYSIS_ID || ''
          const result = await store.executeOrder('2', hemoId, order.CURE_DRUG_ID, order.CREATE_DATE)
          if (result.success) {
            uni.showToast({ title: '执行成功', icon: 'success' })
          } else {
            uni.showToast({ title: result.message || '执行失败', icon: 'none' })
          }
        } catch (error) {
          uni.showToast({ title: '执行失败', icon: 'none' })
        }
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

onMounted(async () => {
  const hemoId = store.currentPatient?.HEMODIALYSIS_ID || ''
  if (hemoId) {
    try {
      await Promise.all([
        store.fetchLongOrders(hemoId),
        store.fetchTempOrders(hemoId)
      ])
    } catch (error) {
      console.error('Failed to fetch orders:', error)
    }
  }
})
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

.order-usage {
  display: block;
  font-size: $font-size-sm;
  color: $text-secondary;
  margin-bottom: $spacing-xs;
}

.order-status {
  font-size: $font-size-xs;
  padding: 2rpx 12rpx;
  border-radius: $radius-sm;
  display: inline-block;

  &.state-pending {
    background: #FFF3E0;
    color: #FF9800;
  }

  &.state-executed {
    background: #E8F5E9;
    color: #4CAF50;
  }
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

.empty-state {
  padding: $spacing-xl;
  text-align: center;
}

.empty-text {
  font-size: $font-size-sm;
  color: $text-hint;
}

.loading-mask {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}

.loading-content {
  background: $card-background;
  padding: $spacing-lg $spacing-xl;
  border-radius: $radius-md;
}

.loading-text {
  font-size: $font-size-base;
  color: $text-primary;
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
