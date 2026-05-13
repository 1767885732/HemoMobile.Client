<template>
  <view class="search-page">
    <view class="search-content">
      <view class="search-item">
        <text class="item-label">选择日期</text>
        <view class="item-value" @click="showDatePicker">
          <text>{{ selectedDate }}</text>
        </view>
      </view>
      
      <view class="search-item">
        <text class="item-label">选择时段</text>
        <view class="item-value" @click="showTimeSlotPicker">
          <text>{{ selectedTimeSlot }}</text>
          <text class="arrow-icon">▾</text>
        </view>
      </view>
      
      <view class="search-item">
        <text class="item-label">选择透析室</text>
        <view class="item-value" @click="showRoomPicker">
          <text>{{ selectedRoom }}</text>
          <text class="arrow-icon">▾</text>
        </view>
      </view>
      
      <view class="search-btn-container">
        <button class="search-btn" @click="handleSearch">搜索</button>
      </view>
    </view>
    
    <view class="picker-mask" v-if="showPicker" @click="hidePicker">
      <view class="picker-content" @click.stop>
        <view class="picker-header">{{ pickerTitle }}</view>
        <view class="picker-list">
          <view 
            v-for="item in pickerOptions" 
            :key="item"
            class="picker-item"
            :class="{ active: pickerValue === item }"
            @click="selectPickerItem(item)"
          >
            {{ item }}
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useAppStore } from '@/stores/app'
import type { PatientSearchParam } from '@/utils/types'

const store = useAppStore()

const selectedDate = ref('2026-05-13')
const selectedTimeSlot = ref('上午')
const selectedRoom = ref('')
const isSearching = ref(false)

const showPicker = ref(false)
const pickerTitle = ref('')
const pickerType = ref<'timeSlot' | 'room'>('timeSlot')
const pickerValue = ref('')

const timeSlots = ['上午', '下午', '晚班', '急诊']
const rooms = ['透析室A区', '透析室B区', '透析室C区', '透析室D区', '透析室E区', '透析室F区', '透析室G区']

const pickerOptions = ref<string[]>([])

const showDatePicker = () => {
  const now = new Date()
  const year = now.getFullYear()
  const month = now.getMonth() + 1
  const day = now.getDate()
  
  uni.showActionSheet({
    itemList: [`${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`],
    success: (res) => {
      selectedDate.value = `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`
    }
  })
}

const showTimeSlotPicker = () => {
  pickerTitle.value = '选择时段'
  pickerType.value = 'timeSlot'
  pickerOptions.value = timeSlots
  pickerValue.value = selectedTimeSlot.value
  showPicker.value = true
}

const showRoomPicker = () => {
  pickerTitle.value = '选择透析室'
  pickerType.value = 'room'
  pickerOptions.value = rooms
  pickerValue.value = selectedRoom.value
  showPicker.value = true
}

const hidePicker = () => {
  showPicker.value = false
}

const selectPickerItem = (item: string) => {
  if (pickerType.value === 'timeSlot') {
    selectedTimeSlot.value = item
  } else {
    selectedRoom.value = item
  }
  hidePicker()
}

const handleSearch = async () => {
  if (!selectedRoom.value) {
    uni.showToast({ title: '请选择透析室', icon: 'none' })
    return
  }

  isSearching.value = true
  uni.showLoading({ title: '搜索中...' })

  try {
    const searchParam: PatientSearchParam = {
      date: selectedDate.value,
      timeRangeType: selectedTimeSlot.value,
      roomId: selectedRoom.value
    }

    const result = await store.fetchPatients(searchParam)

    if (result.success) {
      uni.hideLoading()
      
      if (result.data && result.data.length > 0) {
        uni.showToast({ 
          title: `找到 ${result.data.length} 位患者`, 
          icon: 'success' 
        })
        setTimeout(() => {
          uni.navigateTo({
            url: `/pages/patient-list/index?date=${selectedDate.value}&timeSlot=${selectedTimeSlot.value}&room=${selectedRoom.value}`
          })
        }, 1500)
      } else {
        uni.showToast({ 
          title: '未找到患者', 
          icon: 'none' 
        })
        setTimeout(() => {
          uni.navigateTo({
            url: `/pages/patient-list/index?date=${selectedDate.value}&timeSlot=${selectedTimeSlot.value}&room=${selectedRoom.value}`
          })
        }, 1500)
      }
    } else {
      uni.hideLoading()
      uni.showToast({ 
        title: result.message || '搜索失败', 
        icon: 'none' 
      })
    }
  } catch (error: any) {
    uni.hideLoading()
    uni.showToast({ 
      title: error.message || '搜索异常，请重试', 
      icon: 'none' 
    })
  } finally {
    isSearching.value = false
  }
}
</script>

<style lang="scss" scoped>
.search-page {
  min-height: 100vh;
  background: $background-color;
  padding-top: 88rpx;
}

.search-content {
  padding: $spacing-lg;
}

.search-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: $spacing-md;
  background: $card-background;
  margin-bottom: $spacing-sm;
  border-radius: $radius-md;
}

.item-label {
  font-size: $font-size-base;
  color: $text-secondary;
}

.item-value {
  display: flex;
  align-items: center;
  font-size: $font-size-base;
  color: $text-primary;
}

.arrow-icon {
  margin-left: $spacing-sm;
  font-size: $font-size-sm;
  color: $text-hint;
}

.search-btn-container {
  margin-top: $spacing-xl;
}

.search-btn {
  width: 100%;
  height: 88rpx;
  background: $primary-color;
  color: #FFFFFF;
  border-radius: $radius-md;
  font-size: $font-size-lg;
  font-weight: 500;
  border: none;
}

.picker-mask {
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

.picker-content {
  width: 100%;
  background: $card-background;
  border-radius: $radius-lg $radius-lg 0 0;
  max-height: 60vh;
}

.picker-header {
  height: 88rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: $font-size-lg;
  font-weight: 500;
  border-bottom: 1rpx solid $divider-color;
  color: $primary-color;
}

.picker-list {
  max-height: 400rpx;
  overflow-y: auto;
}

.picker-item {
  height: 80rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: $font-size-base;
  border-bottom: 1rpx solid $divider-color;
  
  &.active {
    background: $primary-light;
    color: $primary-dark;
  }
}

page {
  background: $background-color;
}
</style>