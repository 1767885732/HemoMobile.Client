<template>
  <view class="patient-list-page">
    <view class="patient-list-content">
      <view 
        v-for="patient in patients" 
        :key="patient.id"
        class="patient-card"
        :class="{ active: selectedPatient?.id === patient.id }"
        @click="selectPatient(patient)"
      >
        <view class="patient-info">
          <view class="patient-name-row">
            <text class="patient-name">{{ patient.name }}</text>
            <text class="patient-gender">{{ patient.gender }}</text>
          </view>
          <view class="patient-detail-row">
            <text class="detail-label">透析号：</text>
            <text class="detail-value">{{ patient.dialysisNo }}</text>
          </view>
          <view class="patient-detail-row">
            <text class="detail-label">治疗项目：</text>
            <text class="detail-value">{{ patient.treatmentProject || '-' }}</text>
          </view>
          <view class="patient-detail-row">
            <text class="detail-label">传染病：</text>
            <text class="detail-value">{{ patient.infectiousDisease || '-' }}</text>
          </view>
          <view class="patient-detail-row">
            <text class="detail-label">净化器：</text>
            <text class="detail-value">{{ patient.purifier || '-' }}</text>
          </view>
          <view class="patient-detail-row">
            <text class="detail-label">预定时间：</text>
            <text class="detail-value">{{ patient.scheduledTime }}小时</text>
          </view>
          <view class="patient-detail-row">
            <text class="detail-label">签到时间：</text>
            <text class="detail-value">{{ patient.checkInTime || '-' }}</text>
          </view>
          <view class="patient-detail-row">
            <text class="detail-label">签到顺序：</text>
            <text class="detail-value">{{ patient.checkInOrder || '-' }}</text>
          </view>
        </view>
        <view class="patient-status" :class="getStatusClass(patient.status)">
          <text>{{ patient.status }}</text>
        </view>
      </view>
    </view>
    
    <view class="action-modal" v-if="showActionModal">
      <view class="action-modal-content">
        <view class="action-btn" @click="handlePatientPhoto">患者拍照</view>
        <view class="action-btn primary" @click="handleEditTreatment">编辑治疗</view>
        <view class="action-btn" @click="handleEndTreatment">结束治疗</view>
      </view>
    </view>
    
    <view class="refresh-btn" @click="handleRefresh">
      <text class="refresh-icon">↻</text>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useAppStore, type Patient } from '@/stores/app'

const store = useAppStore()
const patients = ref(store.patients)
const selectedPatient = ref<Patient | null>(null)
const showActionModal = ref(false)

const selectPatient = (patient: Patient) => {
  selectedPatient.value = patient
  showActionModal.value = true
}

const getStatusClass = (status: string) => {
  switch (status) {
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

const handlePatientPhoto = () => {
  showActionModal.value = false
  uni.showToast({ title: '拍照功能开发中', icon: 'none' })
}

const handleEditTreatment = () => {
  showActionModal.value = false
  if (selectedPatient.value) {
    uni.navigateTo({
      url: `/pages/patient-detail/index?patientId=${selectedPatient.value.id}`
    })
  }
}

const handleEndTreatment = () => {
  showActionModal.value = false
  uni.showModal({
    title: '确认结束',
    content: `确定要结束 ${selectedPatient.value?.name} 的治疗吗？`,
    success: (res) => {
      if (res.confirm) {
        uni.showToast({ title: '治疗已结束', icon: 'success' })
      }
    }
  })
}

const handleRefresh = () => {
  uni.showToast({ title: '刷新成功', icon: 'none' })
}
</script>

<style lang="scss" scoped>
.patient-list-page {
  min-height: 100vh;
  background: $background-color;
  padding-top: 88rpx;
  padding-bottom: 120rpx;
}

.patient-list-content {
  padding: $spacing-md;
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
}

.refresh-btn {
  position: fixed;
  top: 100rpx;
  right: $spacing-md;
  width: 72rpx;
  height: 72rpx;
  background: $card-background;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: $shadow-md;
}

.refresh-icon {
  font-size: $font-size-xl;
  color: $primary-color;
}

page {
  background: $background-color;
}
</style>