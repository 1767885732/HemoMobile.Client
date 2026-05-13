<template>
  <view class="dialysis-param-page">
    <view class="param-content">
      <view class="form-item">
        <text class="form-label">记录时间</text>
        <input class="form-input" v-model="paramForm.recordTime" placeholder="请输入记录时间" />
      </view>
      <view class="form-item">
        <text class="form-label">静脉压</text>
        <view class="double-input-group">
          <input class="form-input short" v-model="paramForm.venousPressure" placeholder="请输入静脉压" />
          <input class="form-input short" v-model="paramForm.venousPressureUnit" placeholder="mmHg" />
        </view>
      </view>
      <view class="form-item">
        <text class="form-label">跨膜压mmHg</text>
        <input class="form-input" v-model="paramForm.transmembranePressure" placeholder="请输入跨膜压" />
      </view>
      <view class="form-item">
        <text class="form-label">电导度ms/cm</text>
        <input class="form-input" v-model="paramForm.conductivity" placeholder="请输入电导度" />
      </view>
      <view class="form-item">
        <text class="form-label">血流量ml/min</text>
        <input class="form-input" v-model="paramForm.bloodFlow" placeholder="请输入血流量" />
      </view>
      <view class="form-item">
        <text class="form-label">透析液流量ml/h</text>
        <input class="form-input" v-model="paramForm.dialysisFlow" placeholder="请输入透析液流量" />
      </view>
      <view class="form-item">
        <text class="form-label">超滤率ml/h</text>
        <input class="form-input" v-model="paramForm.ultrafiltrationRate" placeholder="请输入超滤率" />
      </view>
      <view class="form-item">
        <text class="form-label">在线清除率mL/min</text>
        <input class="form-input" v-model="paramForm.onlineClearance" placeholder="请输入在线清除率" />
      </view>
      <view class="form-item">
        <text class="form-label">肝素追加</text>
        <view class="double-input-group">
          <input class="form-input short" v-model="paramForm.heparinAdditional" placeholder="请输入肝素追加" />
          <input class="form-input short" v-model="paramForm.heparinUnit" placeholder="IU" />
        </view>
      </view>
      <view class="form-item">
        <text class="form-label">体温℃</text>
        <input class="form-input" v-model="paramForm.temperature" placeholder="请输入体温" />
      </view>
      <view class="form-item">
        <text class="form-label">收缩压mmHg</text>
        <input class="form-input" v-model="paramForm.systolicPressure" placeholder="请输入收缩压" />
      </view>
      
      <view class="btn-container">
        <button class="btn btn-primary" @click="handleSave">保存</button>
        <button class="btn btn-secondary" @click="handleCancel">取消</button>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { reactive } from 'vue'

const paramForm = reactive({
  recordTime: '15:13',
  venousPressure: '',
  venousPressureUnit: 'mmHg',
  transmembranePressure: '',
  conductivity: '14.0',
  bloodFlow: '',
  dialysisFlow: '500',
  ultrafiltrationRate: '17575',
  onlineClearance: '',
  heparinAdditional: '',
  heparinUnit: '',
  temperature: '',
  systolicPressure: ''
})

const handleSave = () => {
  uni.showToast({ title: '保存成功', icon: 'success' })
}

const handleCancel = () => {
  uni.navigateBack()
}
</script>

<style lang="scss" scoped>
.dialysis-param-page {
  min-height: 100vh;
  background: $background-color;
  padding-top: 88rpx;
}

.param-content {
  padding: $spacing-lg;
}

.form-item {
  display: flex;
  align-items: center;
  padding: $spacing-md 0;
  border-bottom: 1rpx solid $divider-color;
}

.form-label {
  width: 220rpx;
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

.double-input-group {
  flex: 1;
  display: flex;
  align-items: center;
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
}

page {
  background: $background-color;
}
</style>