<template>
  <view class="patient-list-page">
    <NavBar title="患者列表" showBack>
      <template #right>
        <text class="refresh-icon" @click="handleRefresh">↻</text>
      </template>
    </NavBar>

    <view class="loading-mask" v-if="store.isLoading">
      <view class="loading-content">
        <text class="loading-text">加载中...</text>
      </view>
    </view>

    <view class="patient-list-content">
      <view v-if="store.patients.length === 0 && !store.isLoading" class="empty-state">
        <text class="empty-icon">📋</text>
        <text class="empty-text">暂无患者数据</text>
        <view class="empty-btn" @click="handleRefresh">
          <text>刷新列表</text>
        </view>
      </view>

      <view
        v-for="patient in store.patients"
        :key="patient.PATIENT_SCHEDULE_ID"
        class="patient-card"
        :class="{ active: selectedPatient?.PATIENT_SCHEDULE_ID === patient.PATIENT_SCHEDULE_ID }"
        @click.stop="selectPatient(patient)"
      >
        <view class="patient-info">
          <view class="patient-name-row">
            <text class="patient-name">{{ patient.PATIENTNAME }}</text>
            <text class="patient-gender">{{ patient.SEX }}</text>
          </view>
          <view class="patient-detail-row">
            <text class="detail-label">透析号：</text>
            <text class="detail-value">{{ patient.HEMODIALYSIS_ID || '-' }}</text>
          </view>
          <view class="patient-detail-row">
            <text class="detail-label">治疗项目：</text>
            <text class="detail-value">{{ patient.PURIFICATION_MODE || '-' }}</text>
          </view>
          <view class="patient-detail-row">
            <text class="detail-label">传染病：</text>
            <text class="detail-value">{{ patient.INFECTIOUS_CHECK_RESULT || '-' }}</text>
          </view>
          <view class="patient-detail-row">
            <text class="detail-label">净化器：</text>
            <text class="detail-value">{{ patient.MODEL_NAME || '-' }}</text>
          </view>
          <view class="patient-detail-row">
            <text class="detail-label">预定时间：</text>
            <text class="detail-value">{{ patient.FREQUENCY_HOURS || '0' }}小时</text>
          </view>
          <view class="patient-detail-row">
            <text class="detail-label">床位：</text>
            <text class="detail-value">{{ patient.BEDNAME || '-' }}</text>
          </view>
        </view>
        <view class="patient-status" :class="getStatusClass(patient.STATUS)">
          <text>{{ formatStatus(patient.STATUS) }}</text>
        </view>
      </view>
    </view>

    <view class="action-modal" v-if="showActionModal" @click="showActionModal = false">
      <view class="action-modal-content" @click.stop>
        <view class="modal-header">
          <text class="modal-title">{{ selectedPatient?.PATIENTNAME }}</text>
          <text class="modal-subtitle">{{ selectedPatient?.HEMODIALYSIS_ID }}</text>
        </view>
        <view class="action-btn" @click="handlePatientPhoto">
          <text class="btn-icon">📷</text>
          <text class="btn-text">患者拍照</text>
        </view>
        <view class="action-btn primary" @click="handleEditTreatment">
          <text class="btn-icon">📝</text>
          <text class="btn-text">编辑治疗</text>
        </view>
        <view class="action-btn" v-if="canStartTreatment" @click="handleStartTreatment">
          <text class="btn-icon">▶️</text>
          <text class="btn-text">开始治疗</text>
        </view>
        <view class="action-btn" v-if="canEndTreatment" @click="handleEndTreatment">
          <text class="btn-icon">⏹️</text>
          <text class="btn-text">结束治疗</text>
        </view>
        <view class="action-btn cancel" @click="showActionModal = false">
          <text class="btn-text">取消</text>
        </view>
      </view>
    </view>

    <view class="refresh-btn" @click="handleRefresh">
      <text class="refresh-icon">↻</text>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import NavBar from '@/components/NavBar.vue'
import { useAppStore } from '@/stores/app'
import type { MedPatientSchedule } from '@/utils/types'

const store = useAppStore()
const selectedPatient = ref<MedPatientSchedule | null>(null)
const showActionModal = ref(false)

onMounted(() => {
  if (store.patients.length === 0) {
    uni.showToast({
      title: '请先搜索患者',
      icon: 'none',
      duration: 2000
    })
    setTimeout(() => {
      uni.navigateBack()
    }, 2000)
  }
})

const formatStatus = (status: string | number): string => {
  const statusMap: Record<string | number, string> = {
    '0': '未开始',
    '1': '治疗中',
    '2': '已完成',
    '未开始': '未开始',
    '治疗中': '治疗中',
    '已完成': '已完成'
  }
  return statusMap[status] || status
}

const getStatusClass = (status: string | number): string => {
  const statusText = formatStatus(status)
  switch (statusText) {
    case '治疗中':
      return 'status-treating'
    case '未开始':
      return 'status-pending'
    case '已完成':
      return 'status-completed'
    default:
      return ''
  }
}

const canStartTreatment = computed(() => {
  if (!selectedPatient.value) return false
  const status = selectedPatient.value.STATUS
  return status === '0' || status === '未开始'
})

const canEndTreatment = computed(() => {
  if (!selectedPatient.value) return false
  const status = selectedPatient.value.STATUS
  return status === '1' || status === '治疗中'
})

const selectPatient = async (patient: MedPatientSchedule) => {
  selectedPatient.value = patient
  showActionModal.value = true

  try {
    const result = await store.fetchPatientDetail(patient)
    if (!result.success) {
      uni.showToast({ title: result.message || '获取患者详情失败', icon: 'none' })
    }
  } catch (error: any) {
    console.error('Select patient error:', error)
  }
}

const handlePatientPhoto = () => {
  showActionModal.value = false
  uni.showToast({ title: '拍照功能开发中', icon: 'none' })
}

const handleEditTreatment = () => {
  showActionModal.value = false
  if (selectedPatient.value) {
    uni.navigateTo({
      url: `/pages/patient-detail/index?id=${selectedPatient.value.PATIENT_SCHEDULE_ID}`
    })
  }
}

const handleStartTreatment = async () => {
  showActionModal.value = false
  if (!selectedPatient.value) return

  uni.showModal({
    title: '确认开始',
    content: `确定要为 ${selectedPatient.value.PATIENTNAME} 开始治疗吗？`,
    success: async (res) => {
      if (res.confirm) {
        try {
          uni.showLoading({ title: '正在开始治疗...' })
          const result = await store.startCure(
            selectedPatient.value!.HEMODIALYSIS_ID,
            selectedPatient.value!.DIALYSIS_DATE
          )
          uni.hideLoading()

          if (result.success) {
            const patient = store.patients.find(
              p => p.PATIENT_SCHEDULE_ID === selectedPatient.value?.PATIENT_SCHEDULE_ID
            )
            if (patient) {
              patient.STATUS = '1'
            }
            uni.showToast({ title: '已开始治疗', icon: 'success' })
          } else {
            uni.showToast({ title: result.message || '开始治疗失败', icon: 'none' })
          }
        } catch (error: any) {
          uni.hideLoading()
          console.error('Start treatment error:', error)
          uni.showToast({ title: '开始治疗失败', icon: 'none' })
        }
      }
    }
  })
}

const handleEndTreatment = () => {
  showActionModal.value = false
  if (!selectedPatient.value) return

  uni.showModal({
    title: '确认结束',
    content: `确定要结束 ${selectedPatient.value.PATIENTNAME} 的治疗吗？`,
    success: async (res) => {
      if (res.confirm) {
        try {
          uni.showLoading({ title: '正在结束治疗...' })
          const result = await store.finishCure()
          uni.hideLoading()

          if (result.success) {
            const patient = store.patients.find(
              p => p.PATIENT_SCHEDULE_ID === selectedPatient.value?.PATIENT_SCHEDULE_ID
            )
            if (patient) {
              patient.STATUS = '2'
            }
            uni.showToast({ title: '已结束治疗', icon: 'success' })
          } else {
            uni.showToast({ title: result.message || '结束治疗失败', icon: 'none' })
          }
        } catch (error: any) {
          uni.hideLoading()
          console.error('End treatment error:', error)
          uni.showToast({ title: '结束治疗失败', icon: 'none' })
        }
      }
    }
  })
}

const handleRefresh = async () => {
  try {
    uni.showLoading({ title: '刷新中...' })
    const searchParam = {
      date: new Date().toISOString().split('T')[0],
      timeRangeType: '',
      roomId: ''
    }
    await store.fetchPatients(searchParam)
    uni.hideLoading()
    uni.showToast({ title: '刷新成功', icon: 'success' })
  } catch (error: any) {
    uni.hideLoading()
    console.error('Refresh error:', error)
    uni.showToast({ title: '刷新失败', icon: 'none' })
  }
}
</script>

<style lang="scss" scoped>
.patient-list-page {
  min-height: 100vh;
  background: $background-color;
  padding-bottom: 120rpx;
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
  padding: 40rpx 60rpx;
  border-radius: $radius-md;
}

.loading-text {
  font-size: $font-size-base;
  color: $text-primary;
}

.patient-list-content {
  padding: $spacing-md;
  padding-top: calc(88rpx + env(safe-area-inset-top));
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 100rpx 0;
}

.empty-icon {
  font-size: 80rpx;
  margin-bottom: $spacing-md;
}

.empty-text {
  font-size: $font-size-base;
  color: $text-hint;
  margin-bottom: $spacing-lg;
}

.empty-btn {
  padding: $spacing-sm $spacing-lg;
  background: $primary-color;
  border-radius: $radius-md;
  color: #FFFFFF;
  font-size: $font-size-base;
}

.patient-card {
  background: $card-background;
  padding: $spacing-md;
  margin-bottom: $spacing-sm;
  border-radius: $radius-md;
  display: flex;
  justify-content: space-between;
  border-left: 6rpx solid transparent;

  &.active {
    background: rgba($primary-color, 0.1);
    border-left-color: $primary-color;
  }
}

.patient-info {
  flex: 1;
}

.patient-name-row {
  display: flex;
  align-items: center;
  margin-bottom: $spacing-sm;
}

.patient-name {
  font-size: $font-size-lg;
  font-weight: 600;
  color: $text-primary;
}

.patient-gender {
  font-size: $font-size-sm;
  color: $text-secondary;
  margin-left: $spacing-sm;
  padding: $spacing-xs $spacing-sm;
  background: $background-color;
  border-radius: $radius-sm;
}

.patient-detail-row {
  display: flex;
  font-size: $font-size-sm;
  margin-bottom: $spacing-xs;
}

.detail-label {
  color: $text-hint;
}

.detail-value {
  color: $text-secondary;
}

.patient-status {
  padding: $spacing-sm $spacing-md;
  border-radius: $radius-md;
  font-size: $font-size-sm;
  font-weight: 500;
  height: fit-content;
  align-self: center;

  &.status-pending {
    background: $warning-color;
    color: #FFFFFF;
  }

  &.status-treating {
    background: $success-color;
    color: #FFFFFF;
  }

  &.status-completed {
    background: $text-hint;
    color: #FFFFFF;
  }
}

.action-modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: flex-end;
  z-index: 1000;
}

.action-modal-content {
  width: 100%;
  background: $card-background;
  padding: $spacing-md;
  padding-bottom: calc($spacing-md + env(safe-area-inset-bottom));
  border-radius: $radius-lg $radius-lg 0 0;
}

.modal-header {
  text-align: center;
  padding-bottom: $spacing-md;
  border-bottom: 1rpx solid $divider-color;
  margin-bottom: $spacing-md;
}

.modal-title {
  display: block;
  font-size: $font-size-lg;
  font-weight: 600;
  color: $text-primary;
}

.modal-subtitle {
  display: block;
  font-size: $font-size-sm;
  color: $text-hint;
  margin-top: $spacing-xs;
}

.action-btn {
  height: 88rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: $font-size-lg;
  background: $background-color;
  color: $text-primary;
  border-radius: $radius-md;
  margin-bottom: $spacing-sm;

  &.primary {
    background: $primary-color;
    color: #FFFFFF;
  }

  &.cancel {
    background: transparent;
    color: $text-hint;
    margin-top: $spacing-sm;
  }
}

.btn-icon {
  font-size: $font-size-xl;
  margin-right: $spacing-sm;
}

.btn-text {
  font-size: $font-size-lg;
}

.refresh-btn {
  position: fixed;
  top: calc(100rpx + env(safe-area-inset-top));
  right: $spacing-md;
  width: 72rpx;
  height: 72rpx;
  background: $card-background;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: $shadow-md;
  z-index: 100;
}

.refresh-icon {
  font-size: $font-size-xl;
  color: $primary-color;
}

page {
  background: $background-color;
}
</style>
