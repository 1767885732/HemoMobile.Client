<template>
  <view class="picker-wrapper">
    <view class="picker-value" @click="showPicker">
      <text :class="{ placeholder: !value }">{{ value || placeholder }}</text>
      <text class="picker-arrow">▾</text>
    </view>
    
    <view class="picker-mask" v-if="visible" @click="hidePicker">
      <view class="picker-content" @click.stop>
        <view class="picker-header">{{ title }}</view>
        <scroll-view scroll-y class="picker-list">
          <view 
            v-for="item in options" 
            :key="item.value"
            class="picker-item"
            :class="{ active: value === item.value }"
            @click="selectItem(item)"
          >
            <text>{{ item.label }}</text>
          </view>
        </scroll-view>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'

interface PickerOption {
  value: string
  label: string
}

const props = withDefaults(defineProps<{
  modelValue: string
  title: string
  options: PickerOption[]
  placeholder?: string
}>(), {
  placeholder: '请选择'
})

const emit = defineEmits<{
  (e: 'update:modelValue', value: string): void
}>()

const visible = ref(false)
const value = ref(props.modelValue)

watch(() => props.modelValue, (newVal) => {
  value.value = newVal
})

const showPicker = () => {
  visible.value = true
}

const hidePicker = () => {
  visible.value = false
}

const selectItem = (item: PickerOption) => {
  value.value = item.value
  emit('update:modelValue', item.value)
  hidePicker()
}
</script>

<style lang="scss" scoped>
.picker-wrapper {
  width: 100%;
}

.picker-value {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 72rpx;
  padding: 0 $spacing-sm;
  background: $card-background;
  border-radius: $radius-sm;
  font-size: $font-size-base;
  
  &.placeholder {
    color: $text-hint;
  }
}

.picker-arrow {
  font-size: $font-size-sm;
  color: $text-hint;
  margin-left: $spacing-sm;
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
    font-weight: 500;
  }
}
</style>