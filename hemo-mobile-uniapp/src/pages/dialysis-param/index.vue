<template>
  <view class="dialysis-param-page">
    <scroll-view class="param-scroll" scroll-y>
      <view class="section-header">
        <text class="section-title">透析参数列表</text>
      </view>

      <view class="param-list" v-if="hemoParameters.length > 0">
        <view
          v-for="param in hemoParameters"
          :key="param.hemodialysis_Parameters_Id"
          class="param-item"
        >
          <view class="param-header">
            <text class="param-time">{{ formatDate(param.create_Date) }}</text>
            <text class="param-delete" @click="handleDelete(param)">删除</text>
          </view>
          <view class="param-grid">
            <view class="param-cell">
              <text class="cell-label">静脉压</text>
              <text class="cell-value">{{ param.venous_Pressure }}{{ param.venous_Pressure_Unit || 'mmHg' }}</text>
            </view>
            <view class="param-cell">
              <text class="cell-label">跨膜压</text>
              <text class="cell-value">{{ param.transmembrane_Pressure }}mmHg</text>
            </view>
            <view class="param-cell">
              <text class="cell-label">血流量</text>
              <text class="cell-value">{{ param.blood_Flow }}ml/min</text>
            </view>
            <view class="param-cell">
              <text class="cell-label">电导度</text>
              <text class="cell-value">{{ param.conductivity }}ms/cm</text>
            </view>
          </view>
        </view>
      </view>
      <view class="empty-state" v-else>
        <text class="empty-text">暂无透析参数记录</text>
      </view>

      <view class="section-header">
        <text class="section-title">新增透析参数</text>
      </view>

      <view class="form-card">
        <view class="form-item">
          <text class="form-label">记录时间</text>
          <picker mode="time" :value="paramForm.create_Date" @change="onTimeChange">
            <view class="picker-value">{{ paramForm.create_Date || '请选择时间' }}</view>
          </picker>
        </view>

        <view class="form-item">
          <text class="form-label">静脉压 (mmHg)</text>
          <input class="form-input" type="digit" v-model="paramForm.venous_Pressure" placeholder="请输入静脉压" />
        </view>

        <view class="form-item">
          <text class="form-label">跨膜压 (mmHg)</text>
          <input class="form-input" type="digit" v-model="paramForm.transmembrane_Pressure" placeholder="请输入跨膜压" />
        </view>

        <view class="form-item">
          <text class="form-label">血流量 (ml/min)</text>
          <input class="form-input" type="digit" v-model="paramForm.blood_Flow" placeholder="请输入血流量" />
        </view>

        <view class="form-item">
          <text class="form-label">电导度 (ms/cm)</text>
          <input class="form-input" type="digit" v-model="paramForm.conductivity" placeholder="请输入电导度" />
        </view>

        <view class="form-item">
          <text class="form-label">透析液流量 (ml/h)</text>
          <input class="form-input" type="digit" v-model="paramForm.dialysate_Rate" placeholder="请输入透析液流量" />
        </view>

        <view class="form-item">
          <text class="form-label">超滤率 (ml/h)</text>
          <input class="form-input" type="digit" v-model="paramForm.urf" placeholder="请输入超滤率" />
        </view>

        <view class="form-item">
          <text class="form-label">在线清除率 (mL/min)</text>
          <input class="form-input" type="digit" v-model="paramForm.online_Clearance_Rate" placeholder="请输入在线清除率" />
        </view>

        <view class="form-item">
          <text class="form-label">体温 (℃)</text>
          <input class="form-input" type="digit" v-model="paramForm.temperature" placeholder="请输入体温" />
        </view>

        <view class="form-item">
          <text class="form-label">收缩压 (mmHg)</text>
          <input class="form-input" type="digit" v-model="paramForm.systolic_Pressure" placeholder="请输入收缩压" />
        </view>

        <view class="form-item">
          <text class="form-label">舒张压 (mmHg)</text>
          <input class="form-input" type="digit" v-model="paramForm.diastolic_Pressure" placeholder="请输入舒张压" />
        </view>

        <view class="form-item">
          <text class="form-label">肝素追加</text>
          <view class="double-input-group">
            <input class="form-input short" type="digit" v-model="paramForm.anticoagulant" placeholder="剂量" />
            <input class="form-input short" v-model="paramForm.anticoagulantunit" placeholder="单位" />
          </view>
        </view>

        <view class="btn-container">
          <button class="btn btn-primary" @click="handleSave" :disabled="isLoading">保存</button>
        </view>
      </view>
    </scroll-view>

    <view class="loading-mask" v-if="isLoading">
      <view class="loading-content">
        <text class="loading-text">处理中...</text>
      </view>
    </view>

    <view class="bottom-tabs">
      <view class="bottom-tab-item" @click="navigateToDetail()">
        <text class="tab-icon">☰</text>
        <text class="tab-text">治疗信息</text>
      </view>
      <view class="bottom-tab-item" @click="navigateToOrders()">
        <text class="tab-icon">💊</text>
        <text class="tab-text">临时医嘱</text>
      </view>
      <view class="bottom-tab-item active">
        <text class="tab-icon">📊</text>
        <text class="tab-text">透析参数</text>
      </view>
      <view class="bottom-tab-item" @click="navigateToSummary()">
        <text class="tab-icon">📝</text>
        <text class="tab-text">透析小结</text>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { reactive, computed, onMounted } from 'vue'
import { useAppStore } from '@/stores/app'
import type { MedHemoParameter } from '@/utils/types'

const store = useAppStore()

const hemoParameters = computed(() => store.hemoParameters)
const isLoading = computed(() => store.isLoading)

const paramForm = reactive({
  create_Date: '',
  venous_Pressure: '' as number | string,
  transmembrane_Pressure: '' as number | string,
  blood_Flow: '' as number | string,
  conductivity: '' as number | string,
  dialysate_Rate: '' as number | string,
  urf: '' as number | string,
  online_Clearance_Rate: '' as number | string,
  temperature: '' as number | string,
  systolic_Pressure: '' as number | string,
  diastolic_Pressure: '' as number | string,
  anticoagulant: '' as number | string,
  anticoagulantunit: ''
})

const formatDate = (dateStr: string) => {
  if (!dateStr) return ''
  try {
    const date = new Date(dateStr)
    const hours = date.getHours().toString().padStart(2, '0')
    const minutes = date.getMinutes().toString().padStart(2, '0')
    return `${hours}:${minutes}`
  } catch {
    return dateStr
  }
}

const onTimeChange = (e: any) => {
  paramForm.create_Date = e.detail.value
}

const resetForm = () => {
  const now = new Date()
  const hours = now.getHours().toString().padStart(2, '0')
  const minutes = now.getMinutes().toString().padStart(2, '0')
  paramForm.create_Date = `${hours}:${minutes}`
  paramForm.venous_Pressure = ''
  paramForm.transmembrane_Pressure = ''
  paramForm.blood_Flow = ''
  paramForm.conductivity = ''
  paramForm.dialysate_Rate = ''
  paramForm.urf = ''
  paramForm.online_Clearance_Rate = ''
  paramForm.temperature = ''
  paramForm.systolic_Pressure = ''
  paramForm.diastolic_Pressure = ''
  paramForm.anticoagulant = ''
  paramForm.anticoagulantunit = ''
}

const handleSave = async () => {
  const cureId = store.currentCureInfo?.cure_Id || ''
  if (!cureId) {
    uni.showToast({ title: '无法获取治疗信息', icon: 'none' })
    return
  }

  if (!paramForm.venous_Pressure && !paramForm.transmembrane_Pressure && !paramForm.blood_Flow) {
    uni.showToast({ title: '请至少填写一项参数', icon: 'none' })
    return
  }

  try {
    const now = new Date()
    const year = now.getFullYear()
    const month = (now.getMonth() + 1).toString().padStart(2, '0')
    const day = now.getDate().toString().padStart(2, '0')
    const fullDate = `${year}-${month}-${day} ${paramForm.create_Date || '00:00'}:00`

    const param: MedHemoParameter = {
      hemodialysis_Parameters_Id: '',
      cure_Id: cureId,
      recipe_Id: store.currentCureInfo?.recipe_Id || '',
      create_Date: fullDate,
      venous_Pressure: Number(paramForm.venous_Pressure) || 0,
      transmembrane_Pressure: Number(paramForm.transmembrane_Pressure) || 0,
      temperature: Number(paramForm.temperature) || 0,
      systolic_Pressure: Number(paramForm.systolic_Pressure) || 0,
      diastolic_Pressure: Number(paramForm.diastolic_Pressure) || 0,
      cardiotach: 0,
      breath: 0,
      kt_v: '',
      cure_Mode: '',
      clinical_Manifestation: '',
      blood_Flow: Number(paramForm.blood_Flow) || 0,
      sodium_Ion: 0,
      dialysate_Rate: Number(paramForm.dialysate_Rate) || 0,
      urf: Number(paramForm.urf) || 0,
      online_Clearance_Rate: Number(paramForm.online_Clearance_Rate) || 0,
      conductivity: Number(paramForm.conductivity) || 0,
      nurse_Id: store.userId,
      displacement: 0,
      vascular_Access_Errhyisis: '',
      vascular_Access_Glide: '',
      extended_Field_1: '',
      extended_Field_2: '',
      extended_Field_3: '',
      extended_Field_4: '',
      extended_Field_5: '',
      anticoagulant: Number(paramForm.anticoagulant) || 0,
      anticoagulantunit: paramForm.anticoagulantunit || '',
      venous_Pressure_Unit: 'mmHg',
      artery_Pressure: null,
      crrt_Class: ''
    }

    const result = await store.saveHemoParameter(param)
    if (result.success) {
      uni.showToast({ title: '保存成功', icon: 'success' })
      resetForm()
    } else {
      uni.showToast({ title: result.message || '保存失败', icon: 'none' })
    }
  } catch (error) {
    uni.showToast({ title: '保存失败', icon: 'none' })
  }
}

const handleDelete = (param: MedHemoParameter) => {
  uni.showModal({
    title: '确认删除',
    content: '确定删除该条透析参数记录吗？',
    success: async (res) => {
      if (res.confirm) {
        try {
          const result = await store.deleteHemoParameter(param.hemodialysis_Parameters_Id)
          if (result.success) {
            uni.showToast({ title: '删除成功', icon: 'success' })
          } else {
            uni.showToast({ title: result.message || '删除失败', icon: 'none' })
          }
        } catch (error) {
          uni.showToast({ title: '删除失败', icon: 'none' })
        }
      }
    }
  })
}

const navigateToDetail = () => {
  uni.navigateBack()
}

const navigateToOrders = () => {
  uni.navigateTo({ url: '/pages/orders/index' })
}

const navigateToSummary = () => {
  uni.navigateTo({ url: '/pages/summary/index' })
}

onMounted(async () => {
  resetForm()
  const cureId = store.currentCureInfo?.cure_Id || ''
  if (cureId) {
    try {
      await store.fetchHemoParameters(cureId)
    } catch (error) {
      console.error('Failed to fetch hemo parameters:', error)
    }
  }
})
</script>

<style lang="scss" scoped>
.dialysis-param-page {
  min-height: 100vh;
  background: $background-color;
  padding-top: 88rpx;
  padding-bottom: 120rpx;
}

.param-scroll {
  height: calc(100vh - 88rpx - 120rpx);
}

.param-content {
  padding: $spacing-lg;
}

.section-header {
  padding: $spacing-md;
}

.section-title {
  font-size: $font-size-lg;
  font-weight: 600;
  color: $text-primary;
}

.param-list {
  padding: 0 $spacing-md;
}

.param-item {
  background: $card-background;
  border-radius: $radius-md;
  padding: $spacing-md;
  margin-bottom: $spacing-sm;
}

.param-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: $spacing-sm;
  padding-bottom: $spacing-sm;
  border-bottom: 1rpx solid $divider-color;
}

.param-time {
  font-size: $font-size-sm;
  color: $text-secondary;
}

.param-delete {
  font-size: $font-size-sm;
  color: $error-color;
}

.param-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: $spacing-sm;
}

.param-cell {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: $spacing-xs 0;
}

.cell-label {
  font-size: $font-size-sm;
  color: $text-hint;
}

.cell-value {
  font-size: $font-size-sm;
  color: $text-primary;
  font-weight: 500;
}

.empty-state {
  padding: $spacing-xl;
  text-align: center;
}

.empty-text {
  font-size: $font-size-sm;
  color: $text-hint;
}

.form-card {
  margin: 0 $spacing-md;
  background: $card-background;
  border-radius: $radius-md;
  padding: $spacing-md;
}

.form-item {
  display: flex;
  align-items: center;
  padding: $spacing-sm 0;
  border-bottom: 1rpx solid $divider-color;

  &:last-child {
    border-bottom: none;
  }
}

.form-label {
  width: 280rpx;
  font-size: $font-size-base;
  color: $text-secondary;
  flex-shrink: 0;
}

.form-input {
  flex: 1;
  height: 72rpx;
  font-size: $font-size-base;
  padding: 0 $spacing-sm;

  &.short {
    width: 140rpx;
    text-align: center;
  }
}

.picker-value {
  flex: 1;
  height: 72rpx;
  font-size: $font-size-base;
  padding: 0 $spacing-sm;
  display: flex;
  align-items: center;
  color: $text-primary;
}

.double-input-group {
  flex: 1;
  display: flex;
  align-items: center;
  gap: $spacing-sm;
}

.btn-container {
  margin-top: $spacing-xl;
  display: flex;
  flex-direction: column;
  gap: $spacing-sm;
}

.btn {
  width: 100%;
  height: 88rpx;
  border-radius: $radius-md;
  font-size: $font-size-lg;
  font-weight: 500;
  border: none;

  &.btn-primary {
    background: $primary-color;
    color: #FFFFFF;
  }

  &.btn-secondary {
    background: $card-background;
    color: $text-primary;
    border: 2rpx solid $border-color;
  }

  &[disabled] {
    opacity: 0.6;
  }
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
