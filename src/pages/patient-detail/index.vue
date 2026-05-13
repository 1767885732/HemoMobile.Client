<template>
  <view class="patient-detail-page">
    <NavBar title="" showBack>
      <template #right>
        <text class="refresh-icon" @click="handleRefresh">↻</text>
      </template>
    </NavBar>
    
    <view class="page-content">
      <view class="tabs-header">
        <view 
          class="tab-item" 
          :class="{ active: activeTab === 'info' }"
          @click="activeTab = 'info'"
        >
          <text>透析信息</text>
        </view>
        <view 
          class="tab-item" 
          :class="{ active: activeTab === 'assessment' }"
          @click="activeTab = 'assessment'"
        >
          <text>评估与治疗情况</text>
        </view>
      </view>
      
      <view class="tab-content" v-if="activeTab === 'info'">
        <view class="form-item">
          <text class="form-label">治疗方式</text>
          <input class="form-input" v-model="treatmentInfo.treatmentType" placeholder="请选择治疗方式" />
        </view>
        <view class="form-item">
          <text class="form-label">治疗时间</text>
          <view class="time-input-group">
            <input class="form-input short" v-model="treatmentInfo.treatmentHours" placeholder="0" />
            <text class="time-unit">h</text>
            <input class="form-input short" v-model="treatmentInfo.treatmentMinutes" placeholder="0" />
            <text class="time-unit">min</text>
          </view>
        </view>
        <view class="form-item">
          <text class="form-label">净化次数</text>
          <input class="form-input" v-model="treatmentInfo.purifyCount" placeholder="请输入净化次数" />
        </view>
        <view class="form-item">
          <text class="form-label">血管通路</text>
          <input class="form-input" v-model="treatmentInfo.bloodVessel" placeholder="请输入血管通路" />
        </view>
        <view class="form-item">
          <text class="form-label">净化器类型</text>
          <input class="form-input" v-model="treatmentInfo.purifierType" placeholder="请选择净化器类型" />
        </view>
        <view class="form-item">
          <text class="form-label">透析机型号</text>
          <input class="form-input" v-model="treatmentInfo.dialysisModel" placeholder="请选择透析机型号" />
        </view>
        <view class="form-item">
          <text class="form-label">透析膜</text>
          <input class="form-input" v-model="treatmentInfo.dialysisMembrane" placeholder="请选择透析膜" />
        </view>
        <view class="form-item">
          <text class="form-label">面积M²</text>
          <input class="form-input" v-model="treatmentInfo.area" placeholder="请输入面积" />
        </view>
        <view class="form-item">
          <text class="form-label">首剂</text>
          <view class="double-input-group">
            <input class="form-input short" v-model="treatmentInfo.firstDose" placeholder="请输入首剂" />
            <input class="form-input short" v-model="treatmentInfo.firstDoseUnit" placeholder="IU" />
          </view>
        </view>
        <view class="form-item">
          <text class="form-label">追加</text>
          <view class="double-input-group">
            <input class="form-input short" v-model="treatmentInfo追加" placeholder="请输入追加剂量" />
            <input class="form-input short" v-model="treatmentInfo追加Unit" placeholder="IU" />
          </view>
        </view>
      </view>
      
      <view class="tab-content" v-if="activeTab === 'assessment'">
        <view class="form-item">
          <text class="form-label">导管相关血流感染</text>
          <view class="radio-group">
            <view class="radio-item" :class="{ active: assessmentInfo.catheterInfection === '是' }" @click="assessmentInfo.catheterInfection = '是'">
              <view class="radio-circle" :class="{ checked: assessmentInfo.catheterInfection === '是' }"></view>
              <text>是</text>
            </view>
            <view class="radio-item" :class="{ active: assessmentInfo.catheterInfection === '否' }" @click="assessmentInfo.catheterInfection = '否'">
              <view class="radio-circle" :class="{ checked: assessmentInfo.catheterInfection === '否' }"></view>
              <text>否</text>
            </view>
          </view>
        </view>
        <view class="form-item">
          <text class="form-label">透前体重Kg</text>
          <input class="form-input" v-model="assessmentInfo.preWeight" placeholder="请输入透前体重" />
        </view>
        <view class="form-item">
          <text class="form-label">透后体重Kg</text>
          <input class="form-input" v-model="assessmentInfo.postWeight" placeholder="请输入透后体重" />
        </view>
        <view class="form-item">
          <text class="form-label">上次透后体重Kg</text>
          <input class="form-input" v-model="assessmentInfo.lastPostWeight" placeholder="请输入上次透后体重" />
        </view>
        <view class="form-item">
          <text class="form-label">置换液总量L</text>
          <input class="form-input" v-model="assessmentInfo.replacementFluid" placeholder="请输入置换液总量" />
        </view>
        <view class="form-item">
          <text class="form-label">滤出液总量L</text>
          <input class="form-input" v-model="assessmentInfo.filtrateFluid" placeholder="请输入滤出液总量" />
        </view>
        <view class="form-item">
          <text class="form-label">血浆总量L</text>
          <input class="form-input" v-model="assessmentInfo.plasmaVolume" placeholder="请输入血浆总量" />
        </view>
        <view class="form-item">
          <text class="form-label">滤出血浆总量L</text>
          <input class="form-input" v-model="assessmentInfo.filtratePlasma" placeholder="请输入滤出血浆总量" />
        </view>
        <view class="form-item">
          <text class="form-label">责任医生</text>
          <input class="form-input" v-model="assessmentInfo.doctor" placeholder="请选择责任医生" />
        </view>
        <view class="form-item">
          <text class="form-label">责任护士</text>
          <input class="form-input" v-model="assessmentInfo.nurse" placeholder="请选择责任护士" />
        </view>
        <view class="form-item">
          <text class="form-label">穿刺护士</text>
          <input class="form-input" v-model="assessmentInfo.punctureNurse" placeholder="请选择穿刺护士" />
        </view>
        
        <view class="save-btn-container">
          <button class="save-btn" @click="handleSave">保存</button>
        </view>
      </view>
    </view>
    
    <view class="bottom-tabs">
      <view 
        class="bottom-tab-item" 
        :class="{ active: bottomTab === 'info' }"
        @click="bottomTab = 'info'; activeTab = 'info'"
      >
        <text class="tab-icon">☰</text>
        <text class="tab-text">治疗信息</text>
      </view>
      <view 
        class="bottom-tab-item" 
        :class="{ active: bottomTab === 'orders' }"
        @click="bottomTab = 'orders'; navigateToOrders()"
      >
        <text class="tab-icon">💊</text>
        <text class="tab-text">临时医嘱</text>
      </view>
      <view 
        class="bottom-tab-item" 
        :class="{ active: bottomTab === 'params' }"
        @click="bottomTab = 'params'; navigateToParams()"
      >
        <text class="tab-icon">📊</text>
        <text class="tab-text">透析参数</text>
      </view>
      <view 
        class="bottom-tab-item" 
        :class="{ active: bottomTab === 'summary' }"
        @click="bottomTab = 'summary'; navigateToSummary()"
      >
        <text class="tab-icon">📝</text>
        <text class="tab-text">透析小结</text>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import NavBar from '@/components/NavBar.vue'

const activeTab = ref<'info' | 'assessment'>('info')
const bottomTab = ref<'info' | 'orders' | 'params' | 'summary'>('info')

const treatmentInfo = reactive({
  treatmentType: 'HD',
  treatmentHours: '4',
  treatmentMinutes: '',
  purifyCount: '439',
  bloodVessel: '右侧颈内静脉置管颈内静脉半永久通管2023/01/18',
  purifierType: '贝朗F14',
  dialysisModel: '',
  dialysisMembrane: '聚砜膜',
  area: '1.4',
  firstDose: '4000',
  firstDoseUnit: 'IU',
  追加: '',
  追加Unit: 'IU'
})

const assessmentInfo = reactive({
  catheterInfection: '是',
  preWeight: '',
  postWeight: '',
  lastPostWeight: '69',
  replacementFluid: '',
  filtrateFluid: '',
  plasmaVolume: '',
  filtratePlasma: '',
  doctor: 'admin',
  nurse: '',
  punctureNurse: ''
})

const handleRefresh = () => {
  uni.showToast({ title: '刷新成功', icon: 'none' })
}

const handleSave = () => {
  uni.showToast({ title: '保存成功', icon: 'success' })
}

const navigateToOrders = () => {
  uni.navigateTo({ url: '/pages/orders/index' })
}

const navigateToParams = () => {
  uni.navigateTo({ url: '/pages/dialysis-param/index' })
}

const navigateToSummary = () => {
  uni.navigateTo({ url: '/pages/summary/index' })
}
</script>

<style lang="scss" scoped>
.patient-detail-page {
  min-height: 100vh;
  background: $background-color;
  padding-bottom: 120rpx;
}

.page-content {
  padding-top: calc(88rpx + env(safe-area-inset-top));
}

.tabs-header {
  display: flex;
  background: $card-background;
  border-bottom: 1rpx solid $divider-color;
}

.tab-item {
  flex: 1;
  height: 80rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: $font-size-base;
  color: $text-secondary;
  position: relative;
  
  &.active {
    color: $primary-color;
    
    &::after {
      content: '';
      position: absolute;
      bottom: 0;
      left: 50%;
      transform: translateX(-50%);
      width: 60rpx;
      height: 4rpx;
      background: $primary-color;
      border-radius: 2rpx;
    }
  }
}

.tab-content {
  padding: $spacing-md;
}

.form-item {
  display: flex;
  align-items: center;
  padding: $spacing-md 0;
  border-bottom: 1rpx solid $divider-color;
}

.form-label {
  width: 180rpx;
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
    width: 120rpx;
    text-align: center;
  }
}

.time-input-group,
.double-input-group {
  flex: 1;
  display: flex;
  align-items: center;
}

.time-unit {
  font-size: $font-size-sm;
  color: $text-hint;
  margin: 0 $spacing-xs;
}

.radio-group {
  flex: 1;
  display: flex;
  gap: $spacing-lg;
}

.radio-item {
  display: flex;
  align-items: center;
  
  &.active {
    .radio-circle {
      background: $primary-color;
      
      &::after {
        display: block;
      }
    }
  }
}

.radio-circle {
  width: 32rpx;
  height: 32rpx;
  border: 2rpx solid $border-color;
  border-radius: 50%;
  margin-right: $spacing-sm;
  position: relative;
  
  &::after {
    content: '';
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 16rpx;
    height: 16rpx;
    background: #FFFFFF;
    border-radius: 50%;
    display: none;
  }
  
  &.checked {
    background: $primary-color;
    
    &::after {
      display: block;
    }
  }
}

.save-btn-container {
  margin-top: $spacing-xl;
}

.save-btn {
  width: 100%;
  height: 88rpx;
  background: $primary-color;
  color: #FFFFFF;
  border-radius: $radius-md;
  font-size: $font-size-lg;
  font-weight: 500;
  border: none;
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

.refresh-icon {
  font-size: $font-size-xl;
  color: #FFFFFF;
}

page {
  background: $background-color;
}
</style>