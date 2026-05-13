<template>
  <view class="summary-page">
    <view class="summary-content">
      <view class="template-btn" @click="showTemplatePicker">
        <text class="template-icon">⊕</text>
        <text class="template-text">使用模板</text>
      </view>

      <view class="textarea-wrapper">
        <view class="textarea-label">透析小结</view>
        <textarea
          class="summary-textarea"
          v-model="summaryForm.summary"
          placeholder="请输入透析小结内容"
          :maxlength="-1"
        />
      </view>

      <view class="textarea-wrapper">
        <view class="textarea-label">备注</view>
        <textarea
          class="summary-textarea small"
          v-model="summaryForm.summary2"
          placeholder="请输入备注信息"
          :maxlength="-1"
        />
      </view>

      <view class="save-btn-container">
        <button class="save-btn" @click="handleSave" :disabled="isLoading">保存</button>
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
        class="bottom-tab-item"
        @click="navigateToOrders()"
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
        class="bottom-tab-item active"
      >
        <text class="tab-icon">📝</text>
        <text class="tab-text">透析小结</text>
      </view>
    </view>

    <view class="picker-mask" v-if="showTemplateModal" @click="showTemplateModal = false">
      <view class="picker-content" @click.stop>
        <view class="picker-header">选择模板</view>
        <view class="picker-list">
          <view
            v-for="template in templates"
            :key="template.id"
            class="picker-item"
            @click="selectTemplate(template)"
          >
            {{ template.name }}
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { reactive, computed, ref, onMounted } from 'vue'
import { useAppStore } from '@/stores/app'
import type { MedCureInfo } from '@/utils/types'

const store = useAppStore()

const isLoading = computed(() => store.isLoading)

const summaryForm = reactive({
  summary: '',
  summary2: ''
})

const showTemplateModal = ref(false)

const templates = [
  { id: '1', name: '平安离室', content: '平安离室。' },
  { id: '2', name: '病情稳定', content: '患者病情稳定，透析过程顺利，无明显不适。' },
  { id: '3', name: '血压异常', content: '透析过程中血压波动，已及时处理。' },
  { id: '4', name: '需关注', content: '患者状态需密切关注，建议加强监测。' }
]

const showTemplatePicker = () => {
  showTemplateModal.value = true
}

const selectTemplate = (template: { id: string; name: string; content: string }) => {
  summaryForm.summary = template.content
  showTemplateModal.value = false
}

const handleSave = async () => {
  const currentCureInfo = store.currentCureInfo
  if (!currentCureInfo) {
    uni.showToast({ title: '无法获取治疗信息', icon: 'none' })
    return
  }

  if (!summaryForm.summary.trim()) {
    uni.showToast({ title: '请输入透析小结内容', icon: 'none' })
    return
  }

  try {
    const cureInfo: MedCureInfo = {
      ...currentCureInfo,
      summary: summaryForm.summary,
      summary2: summaryForm.summary2
    }

    const result = await store.saveCureInfo(cureInfo)
    if (result.success) {
      uni.showToast({ title: '保存成功', icon: 'success' })
    } else {
      uni.showToast({ title: result.message || '保存失败', icon: 'none' })
    }
  } catch (error) {
    uni.showToast({ title: '保存失败', icon: 'none' })
  }
}

const navigateToDetail = () => {
  uni.navigateBack()
}

const navigateToOrders = () => {
  uni.navigateTo({ url: '/pages/orders/index' })
}

const navigateToParams = () => {
  uni.navigateTo({ url: '/pages/dialysis-param/index' })
}

onMounted(() => {
  const currentCureInfo = store.currentCureInfo
  if (currentCureInfo) {
    summaryForm.summary = currentCureInfo.summary || ''
    summaryForm.summary2 = currentCureInfo.summary2 || ''
  }
})
</script>

<style lang="scss" scoped>
.summary-page {
  min-height: 100vh;
  background: $background-color;
  padding-top: 88rpx;
  padding-bottom: 120rpx;
}

.summary-content {
  padding: $spacing-md;
}

.template-btn {
  display: flex;
  align-items: center;
  padding: $spacing-md;
  background: $card-background;
  border-radius: $radius-md;
  margin-bottom: $spacing-md;
}

.template-icon {
  width: 48rpx;
  height: 48rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  background: $primary-color;
  color: #FFFFFF;
  border-radius: 50%;
  font-size: $font-size-lg;
  margin-right: $spacing-sm;
}

.template-text {
  font-size: $font-size-base;
  color: $primary-color;
}

.textarea-wrapper {
  background: $card-background;
  border-radius: $radius-md;
  padding: $spacing-md;
  margin-bottom: $spacing-md;
}

.textarea-label {
  font-size: $font-size-sm;
  color: $text-secondary;
  margin-bottom: $spacing-sm;
}

.summary-textarea {
  width: 100%;
  height: 400rpx;
  font-size: $font-size-base;
  line-height: 1.6;

  &.small {
    height: 200rpx;
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

  &:active {
    background: $background-color;
  }
}

page {
  background: $background-color;
}
</style>
