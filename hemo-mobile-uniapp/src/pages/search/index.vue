<template>
  <view class="search-page">
    <view class="search-content">
      <view class="search-item">
        <text class="item-label">选择日期</text>
        <view class="item-value" @click="showDatePicker">
          <text>{{ selectedDate }}</text>
          <text class="arrow-icon">▾</text>
        </view>
      </view>
      
      <view class="search-item">
        <text class="item-label">选择时段</text>
        <PickerSelect 
          v-model="selectedTimeSlot" 
          title="选择时段" 
          :options="timeSlotOptions"
          placeholder="请选择时段"
        />
      </view>
      
      <view class="search-item">
        <text class="item-label">选择透析室</text>
        <PickerSelect 
          v-model="selectedRoom" 
          title="选择透析室" 
          :options="roomOptions"
          placeholder="请选择透析室"
        />
      </view>
      
      <view class="search-btn-container">
        <view class="search-btn" @click="handleSearch">
          <text>搜索</text>
        </view>
      </view>
    </view>

    <!-- 日期选择弹窗 -->
    <view class="date-picker-mask" v-if="showPicker" @click="hidePicker">
      <view class="date-picker-content" @click.stop>
        <view class="picker-header">
          <text class="picker-cancel" @click="hidePicker">取消</text>
          <text class="picker-title">选择日期</text>
          <text class="picker-confirm" @click="confirmDate">确定</text>
        </view>
        <picker-view 
          class="date-picker-view" 
          :value="pickerValue" 
          @change="onDateChange"
          indicator-style="height: 50px;"
        >
          <picker-view-column>
            <view class="picker-item" v-for="(year, index) in years" :key="index">
              {{ year }}年
            </view>
          </picker-view-column>
          <picker-view-column>
            <view class="picker-item" v-for="(month, index) in months" :key="index">
              {{ month }}月
            </view>
          </picker-view-column>
          <picker-view-column>
            <view class="picker-item" v-for="(day, index) in days" :key="index">
              {{ day }}日
            </view>
          </picker-view-column>
        </picker-view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { useAppStore } from '@/stores/app'
import PickerSelect from '@/components/PickerSelect.vue'
import type { PatientSearchParam } from '@/utils/types'

const store = useAppStore()

const today = new Date()
const selectedDate = ref(`${today.getFullYear()}-${String(today.getMonth() + 1).padStart(2, '0')}-${String(today.getDate()).padStart(2, '0')}`)
const selectedTimeSlot = ref('上午')
const selectedRoom = ref('')
const isSearching = ref(false)

const showPicker = ref(false)
const pickerValue = ref([0, 0, 0])

const years = computed(() => {
  const currentYear = today.getFullYear()
  return Array.from({ length: 10 }, (_, i) => currentYear - 5 + i)
})

const months = computed(() => {
  return Array.from({ length: 12 }, (_, i) => i + 1)
})

const days = computed(() => {
  const year = years.value[pickerValue.value[0]]
  const month = months.value[pickerValue.value[1]]
  const daysInMonth = new Date(year, month, 0).getDate()
  return Array.from({ length: daysInMonth }, (_, i) => i + 1)
})

const timeSlotOptions = [
  { value: '上午', label: '上午' },
  { value: '下午', label: '下午' },
  { value: '晚班', label: '晚班' },
  { value: '急诊', label: '急诊' }
]

const roomOptions = [
  { value: '1', label: '透析室A区' },
  { value: '2', label: '透析室B区' },
  { value: '3', label: '透析室C区' },
  { value: '4', label: '透析室D区' },
  { value: '5', label: '透析室E区' },
  { value: '6', label: '透析室F区' },
  { value: '7', label: '透析室G区' }
]

// 初始化日期选择器位置
const initDatePicker = () => {
  const parts = selectedDate.value.split('-')
  const year = parseInt(parts[0])
  const month = parseInt(parts[1])
  const day = parseInt(parts[2])
  
  const yearIndex = years.value.indexOf(year)
  const monthIndex = month - 1
  const dayIndex = day - 1
  
  pickerValue.value = [
    yearIndex >= 0 ? yearIndex : 0,
    monthIndex >= 0 ? monthIndex : 0,
    dayIndex >= 0 ? dayIndex : 0
  ]
}

const showDatePicker = () => {
  initDatePicker()
  showPicker.value = true
}

const hidePicker = () => {
  showPicker.value = false
}

const onDateChange = (e: any) => {
  pickerValue.value = e.detail.value
}

const confirmDate = () => {
  const year = years.value[pickerValue.value[0]]
  const month = months.value[pickerValue.value[1]]
  const day = days.value[pickerValue.value[2]]
  
  selectedDate.value = `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`
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
  background: $bg-light;
  padding-top: 88rpx;
}

.search-content {
  padding: $spacing-base;
}

.search-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: $spacing-base;
  background: $card-background;
  margin-bottom: $spacing-sm;
  border-radius: $radius-base;
}

.item-label {
  font-size: $font-size-base;
  color: $text-secondary;
  min-width: 100px;
}

.item-value {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: $font-size-base;
  color: $text-primary;
  padding: 16px 12px;
  background: $bg-light;
  border-radius: $radius-sm;
  cursor: pointer;
}

.arrow-icon {
  font-size: $font-size-sm;
  color: $text-hint;
}

.search-btn-container {
  margin-top: $spacing-base;
}

.search-btn {
  width: 100%;
  height: 48px;
  background: $primary-color;
  color: #FFFFFF;
  border-radius: $radius-base;
  font-size: $font-size-base;
  font-weight: 500;
  display: flex;
  align-items: center;
  justify-content: center;
}

.date-picker-mask {
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

.date-picker-content {
  width: 100%;
  background: $card-background;
  border-radius: $radius-lg $radius-lg 0 0;
}

.picker-header {
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px;
  border-bottom: 1px solid $border-color;
}

.picker-cancel,
.picker-confirm {
  color: $primary-color;
  font-size: $font-size-base;
}

.picker-title {
  font-size: $font-size-base;
  color: $text-primary;
  font-weight: 500;
}

.date-picker-view {
  height: 200px;
}

.picker-item {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: $font-size-lg;
  color: $text-primary;
}

page {
  background: $bg-light;
}
</style>