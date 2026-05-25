<template>
  <view class="patient-detail-page">
    <NavBar :title="patientName" showBack>
      <template #right>
        <text class="refresh-icon" @click="handleRefresh">↻</text>
      </template>
    </NavBar>

    <view class="loading-mask" v-if="store.isLoading">
      <view class="loading-content">
        <text class="loading-text">加载中...</text>
      </view>
    </view>

    <view class="page-content">
      <view class="patient-header">
        <view class="patient-info-card">
          <view class="info-row">
            <text class="info-label">透析号：</text>
            <text class="info-value">{{ store.currentPatient?.HEMODIALYSIS_ID || '-' }}</text>
          </view>
          <view class="info-row">
            <text class="info-label">床位：</text>
            <text class="info-value">{{ store.currentPatient?.BEDNAME || '-' }}</text>
          </view>
          <view class="info-row">
            <text class="info-label">治疗项目：</text>
            <text class="info-value">{{ store.currentPatient?.PURIFICATION_MODE || '-' }}</text>
          </view>
          <view class="info-row">
            <text class="info-label">净化器：</text>
            <text class="info-value">{{ store.currentPatient?.MODEL_NAME || '-' }}</text>
          </view>
        </view>
        <view class="patient-status-badge" :class="getStatusClass(store.currentPatient?.STATUS)">
          <text>{{ formatStatus(store.currentPatient?.STATUS) }}</text>
        </view>
      </view>

      <view class="tabs-header">
        <view
          class="tab-item"
          :class="{ active: activeTab === 'info' }"
          @click="activeTab = 'info'"
        >
          <text>治疗信息</text>
        </view>
        <view
          class="tab-item"
          :class="{ active: activeTab === 'assessment' }"
          @click="activeTab = 'assessment'"
        >
          <text>评估表单</text>
        </view>
      </view>

      <view class="tab-content" v-if="activeTab === 'info'">
        <view class="form-section">
          <view class="section-title">基本信息</view>
          <view class="form-item">
            <text class="form-label">治疗方式</text>
            <PickerSelect 
              v-model="cureInfoForm.purification_Mode" 
              title="选择治疗方式" 
              :options="purificationModeOptions"
              placeholder="请选择治疗方式"
            />
          </view>
          <view class="form-item">
            <text class="form-label">治疗时间</text>
            <input class="form-input" v-model="cureInfoForm.frequency_Hours" placeholder="请输入治疗时间" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">透析机型号</text>
            <input class="form-input" v-model="cureInfoForm.machine_Type" placeholder="请输入透析机型号" />
          </view>
          <view class="form-item">
            <text class="form-label">净化器类型</text>
            <input class="form-input" v-model="cureInfoForm.purifier_Name" placeholder="请输入净化器类型" />
          </view>
        </view>

        <view class="form-section">
          <view class="section-title">透析参数</view>
          <view class="form-item">
            <text class="form-label">血流速</text>
            <input class="form-input" v-model="cureInfoForm.bloow_Flow" placeholder="ml/min" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">透析液流速</text>
            <input class="form-input" v-model="cureInfoForm.dialysate_Flow" placeholder="ml/min" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">透析液温度</text>
            <input class="form-input" v-model="cureInfoForm.dialysate_Temperature" placeholder="℃" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">钙离子</text>
            <input class="form-input" v-model="cureInfoForm.calcium_Ion" placeholder="mmol/L" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">钠离子</text>
            <input class="form-input" v-model="cureInfoForm.sodion" placeholder="mmol/L" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">钾离子</text>
            <input class="form-input" v-model="cureInfoForm.potassium_Ion" placeholder="mmol/L" type="number" />
          </view>
        </view>

        <view class="form-section">
          <view class="section-title">抗凝剂</view>
          <view class="form-item">
            <text class="form-label">肝素种类</text>
            <PickerSelect 
              v-model="cureInfoForm.heparin_Species" 
              title="选择肝素种类" 
              :options="heparinSpeciesOptions"
              placeholder="请选择肝素种类"
            />
          </view>
          <view class="form-item">
            <text class="form-label">首剂</text>
            <input class="form-input" v-model="cureInfoForm.first_Heparin" placeholder="IU" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">追加</text>
            <input class="form-input" v-model="cureInfoForm.dosis_Sustentativa" placeholder="IU/h" type="number" />
          </view>
        </view>

        <view class="form-section">
          <view class="section-title">血管通路</view>
          <view class="form-item">
            <text class="form-label">通路类型</text>
            <PickerSelect 
              v-model="cureInfoForm.vascular_Access_Type" 
              title="选择通路类型" 
              :options="vascularAccessOptions"
              placeholder="请选择通路类型"
            />
          </view>
          <view class="form-item">
            <text class="form-label">通路ID</text>
            <PickerSelect 
              v-model="cureInfoForm.vascular_Access_Id" 
              title="选择通路ID" 
              :options="vascularAccessIdOptions"
              placeholder="请选择通路ID"
            />
          </view>
        </view>

        <view class="form-section">
          <view class="section-title">A/B液</view>
          <view class="form-item">
            <text class="form-label">A液</text>
            <input class="form-input" v-model="cureInfoForm.a_Liquid" placeholder="请输入A液" />
          </view>
          <view class="form-item">
            <text class="form-label">B液</text>
            <input class="form-input" v-model="cureInfoForm.b_Liquid" placeholder="请输入B液" />
          </view>
          <view class="form-item">
            <text class="form-label">碳酸氢根</text>
            <input class="form-input" v-model="cureInfoForm.bircarbonate" placeholder="mmol/L" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">葡萄糖</text>
            <input class="form-input" v-model="cureInfoForm.amylaceum" placeholder="mmol/L" type="number" />
          </view>
        </view>

        <view class="form-section">
          <view class="section-title">人员信息</view>
          <view class="form-item">
            <text class="form-label">责任医生</text>
            <PickerSelect 
              v-model="cureInfoForm.primary_Doctor" 
              title="选择责任医生" 
              :options="doctorOptions"
              placeholder="请选择责任医生"
            />
          </view>
          <view class="form-item">
            <text class="form-label">责任护士</text>
            <PickerSelect 
              v-model="cureInfoForm.primary_Nurse" 
              title="选择责任护士" 
              :options="nurseOptions"
              placeholder="请选择责任护士"
            />
          </view>
          <view class="form-item">
            <text class="form-label">穿刺护士</text>
            <PickerSelect 
              v-model="cureInfoForm.puncture_Nurse" 
              title="选择穿刺护士" 
              :options="nurseOptions"
              placeholder="请选择穿刺护士"
            />
          </view>
        </view>
      </view>

      <view class="tab-content" v-if="activeTab === 'assessment'">
        <view class="form-section">
          <view class="section-title">体重信息</view>
          <view class="form-item">
            <text class="form-label">导管相关血流感染</text>
            <view class="radio-group">
              <view
                class="radio-item"
                :class="{ active: cureInfoForm.vascular_Access_Blood_Infect === '是' }"
                @click="cureInfoForm.vascular_Access_Blood_Infect = '是'"
              >
                <view class="radio-circle" :class="{ checked: cureInfoForm.vascular_Access_Blood_Infect === '是' }"></view>
                <text>是</text>
              </view>
              <view
                class="radio-item"
                :class="{ active: cureInfoForm.vascular_Access_Blood_Infect === '否' }"
                @click="cureInfoForm.vascular_Access_Blood_Infect = '否'"
              >
                <view class="radio-circle" :class="{ checked: cureInfoForm.vascular_Access_Blood_Infect === '否' }"></view>
                <text>否</text>
              </view>
            </view>
          </view>
          <view class="form-item">
            <text class="form-label">透前体重</text>
            <input class="form-input" v-model="cureInfoForm.before_Dry_Weight" placeholder="请输入透前体重" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">透后体重</text>
            <input class="form-input" v-model="cureInfoForm.after_Dry_Weight" placeholder="请输入透后体重" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">上次透后体重</text>
            <input class="form-input" v-model="cureInfoForm.last_Time_Dry_Weight" placeholder="请输入上次透后体重" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">干体重</text>
            <input class="form-input" v-model="cureInfoForm.dry_Weight" placeholder="请输入干体重" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">超滤量</text>
            <input class="form-input" v-model="cureInfoForm.sum_Uf" placeholder="请输入超滤量" type="number" />
          </view>
        </view>

        <view class="form-section">
          <view class="section-title">透前生命体征</view>
          <view class="form-item">
            <text class="form-label">收缩压</text>
            <input class="form-input" v-model="cureInfoForm.before_Systolic_Pressure" placeholder="mmHg" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">舒张压</text>
            <input class="form-input" v-model="cureInfoForm.before_Diastolic_Pressure" placeholder="mmHg" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">体温</text>
            <input class="form-input" v-model="cureInfoForm.before_Temperature" placeholder="℃" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">心率</text>
            <input class="form-input" v-model="cureInfoForm.before_Heart_Rate" placeholder="次/分" type="number" />
          </view>
        </view>

        <view class="form-section">
          <view class="section-title">透后生命体征</view>
          <view class="form-item">
            <text class="form-label">收缩压</text>
            <input class="form-input" v-model="cureInfoForm.after_Systolic_Pressure" placeholder="mmHg" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">舒张压</text>
            <input class="form-input" v-model="cureInfoForm.after_Diastolic_Pressure" placeholder="mmHg" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">体温</text>
            <input class="form-input" v-model="cureInfoForm.after_Temperature" placeholder="℃" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">心率</text>
            <input class="form-input" v-model="cureInfoForm.after_Heart_Rate" placeholder="次/分" type="number" />
          </view>
        </view>

        <view class="form-section">
          <view class="section-title">置换液</view>
          <view class="form-item">
            <text class="form-label">置换液总量</text>
            <input class="form-input" v-model="cureInfoForm.filtration_Displacement_Liquid" placeholder="L" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">滤出液</text>
            <input class="form-input" v-model="cureInfoForm.filtration_Percolate" placeholder="L" type="number" />
          </view>
        </view>

        <view class="form-section">
          <view class="section-title">透析器信息</view>
          <view class="form-item">
            <text class="form-label">使用类型</text>
            <PickerSelect 
              v-model="cureInfoForm.use_Type" 
              title="选择使用类型" 
              :options="useTypeOptions"
              placeholder="请选择使用类型"
            />
          </view>
          <view class="form-item">
            <text class="form-label">复用次数</text>
            <input class="form-input" v-model="cureInfoForm.reuse_Times" placeholder="次" type="number" />
          </view>
          <view class="form-item">
            <text class="form-label">透析器面积</text>
            <input class="form-input" v-model="cureInfoForm.purifier_M2" placeholder="m²" type="number" />
          </view>
        </view>

        <view class="form-section">
          <view class="section-title">其他信息</view>
          <view class="form-item textarea-item">
            <text class="form-label">特殊情况</text>
            <textarea class="form-textarea" v-model="cureInfoForm.special_Matter" placeholder="请输入特殊情况" />
          </view>
          <view class="form-item textarea-item">
            <text class="form-label">医嘱备注</text>
            <textarea class="form-textarea" v-model="cureInfoForm.doctor_Advice" placeholder="请输入医嘱备注" />
          </view>
        </view>

        <view class="save-btn-container">
          <button class="save-btn" @click="handleSave" :disabled="store.isLoading">
            <text>{{ store.isLoading ? '保存中...' : '保存' }}</text>
          </button>
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
        @click="navigateToOrders"
      >
        <text class="tab-icon">💊</text>
        <text class="tab-text">临时医嘱</text>
      </view>
      <view
        class="bottom-tab-item"
        :class="{ active: bottomTab === 'longOrders' }"
        @click="navigateToLongOrders"
      >
        <text class="tab-icon">📋</text>
        <text class="tab-text">长期医嘱</text>
      </view>
      <view
        class="bottom-tab-item"
        :class="{ active: bottomTab === 'params' }"
        @click="navigateToParams"
      >
        <text class="tab-icon">📊</text>
        <text class="tab-text">透析参数</text>
      </view>
      <view
        class="bottom-tab-item"
        :class="{ active: bottomTab === 'summary' }"
        @click="navigateToSummary"
      >
        <text class="tab-icon">📝</text>
        <text class="tab-text">透析小结</text>
      </view>
    </view>
  </view>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import NavBar from '@/components/NavBar.vue'
import PickerSelect from '@/components/PickerSelect.vue'
import { useAppStore } from '@/stores/app'
import type { MedCureInfo } from '@/utils/types'

const store = useAppStore()
const activeTab = ref<'info' | 'assessment'>('info')
const bottomTab = ref<'info' | 'orders' | 'longOrders' | 'params' | 'summary'>('info')
const scheduleId = ref('')

const patientName = computed(() => store.currentPatient?.PATIENTNAME || '患者详情')

const purificationModeOptions = [
  { value: 'HD', label: 'HD' },
  { value: 'HDF', label: 'HDF' },
  { value: 'CRRT', label: 'CRRT' },
  { value: 'SLED', label: 'SLED' }
]

const heparinSpeciesOptions = [
  { value: '普通肝素', label: '普通肝素' },
  { value: '低分子肝素', label: '低分子肝素' },
  { value: '无肝素', label: '无肝素' }
]

const vascularAccessOptions = computed(() => {
  const types = new Set<string>()
  store.vascularAccessList.forEach(item => {
    if (item.vascular_Access_Type) {
      types.add(item.vascular_Access_Type)
    }
  })
  return Array.from(types).map(type => ({ value: type, label: type }))
})

const vascularAccessIdOptions = computed(() => {
  return store.vascularAccessList.map(item => ({
    value: item.vascular_Access_Id,
    label: item.vascular_Access_Id
  }))
})

const useTypeOptions = [
  { value: '新', label: '新' },
  { value: '复用', label: '复用' }
]

const doctorOptions = [
  { value: '张医生', label: '张医生' },
  { value: '李医生', label: '李医生' },
  { value: '王医生', label: '王医生' },
  { value: '赵医生', label: '赵医生' }
]

const nurseOptions = [
  { value: '护士A', label: '护士A' },
  { value: '护士B', label: '护士B' },
  { value: '护士C', label: '护士C' },
  { value: '护士D', label: '护士D' }
]

const cureInfoForm = reactive<Partial<MedCureInfo>>({
  purification_Mode: '',
  frequency_Hours: null,
  machine_Type: '',
  purifier_Name: '',
  bloow_Flow: null,
  dialysate_Flow: null,
  dialysate_Temperature: null,
  calcium_Ion: null,
  sodion: null,
  potassium_Ion: null,
  heparin_Species: '',
  first_Heparin: null,
  dosis_Sustentativa: null,
  vascular_Access_Type: '',
  vascular_Access_Id: '',
  a_Liquid: '',
  b_Liquid: '',
  bircarbonate: null,
  amylaceum: null,
  primary_Doctor: '',
  primary_Nurse: '',
  puncture_Nurse: '',
  vascular_Access_Blood_Infect: '否',
  before_Dry_Weight: null,
  after_Dry_Weight: null,
  last_Time_Dry_Weight: null,
  dry_Weight: null,
  sum_Uf: null,
  before_Systolic_Pressure: null,
  before_Diastolic_Pressure: null,
  before_Temperature: null,
  before_Heart_Rate: null,
  after_Systolic_Pressure: null,
  after_Diastolic_Pressure: null,
  after_Temperature: null,
  after_Heart_Rate: null,
  filtration_Displacement_Liquid: null,
  filtration_Percolate: null,
  use_Type: '',
  reuse_Times: null,
  purifier_M2: null,
  special_Matter: '',
  doctor_Advice: ''
})

const formatStatus = (status: string | number | undefined): string => {
  if (!status) return '-'
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

const getStatusClass = (status: string | number | undefined): string => {
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

const loadPatientData = async () => {
  try {
    uni.showLoading({ title: '加载中...' })

    if (!store.currentPatient && scheduleId.value) {
      const patient = store.patients.find(p => p.PATIENT_SCHEDULE_ID === scheduleId.value)
      if (patient) {
        await store.fetchPatientDetail(patient)
      }
    }

    if (store.currentCureInfo) {
      Object.assign(cureInfoForm, store.currentCureInfo)
    }

    if (store.currentPatient) {
      if (!cureInfoForm.purification_Mode && store.currentPatient.PURIFICATION_MODE) {
        cureInfoForm.purification_Mode = store.currentPatient.PURIFICATION_MODE
      }
      if (!cureInfoForm.purifier_Name && store.currentPatient.MODEL_NAME) {
        cureInfoForm.purifier_Name = store.currentPatient.MODEL_NAME
      }
      if (store.currentPatient.FREQUENCY_HOURS && !cureInfoForm.frequency_Hours) {
        cureInfoForm.frequency_Hours = parseFloat(store.currentPatient.FREQUENCY_HOURS)
      }

      await store.fetchVascularAccess(store.currentPatient.HEMODIALYSIS_ID)
    }

    uni.hideLoading()
  } catch (error: any) {
    uni.hideLoading()
    console.error('Load patient data error:', error)
    uni.showToast({ title: '加载数据失败', icon: 'none' })
  }
}

const handleSave = async () => {
  if (!store.currentPatient) {
    uni.showToast({ title: '未选择患者', icon: 'none' })
    return
  }

  try {
    uni.showLoading({ title: '保存中...' })

    const cureInfo: MedCureInfo = {
      cure_Id: store.currentCureInfo?.cure_Id || `cure_${Date.now()}`,
      recipe_Id: store.currentPatient.RECIPE_ID || '',
      hemodialysis_Id: store.currentPatient.HEMODIALYSIS_ID || '',
      recipe_Type: store.currentCureInfo?.recipe_Type || '',
      calcium_Ion: cureInfoForm.calcium_Ion ?? null,
      cure_Status: store.currentCureInfo?.cure_Status || '0',
      doctor_Id: store.currentCureInfo?.doctor_Id || '',
      recipe_Date: store.currentCureInfo?.recipe_Date || new Date().toISOString().split('T')[0],
      bloow_Flow: cureInfoForm.bloow_Flow ?? null,
      dialysate_Flow: cureInfoForm.dialysate_Flow ?? null,
      dialysate_Temperature: cureInfoForm.dialysate_Temperature ?? null,
      ufr: null,
      sodion: cureInfoForm.sodion ?? null,
      potassium_Ion: cureInfoForm.potassium_Ion ?? null,
      perform_Schedule: '',
      nurse_Id: '',
      purification_Mode: cureInfoForm.purification_Mode || '',
      clean_Up_Times: 0,
      frequency_Hours: cureInfoForm.frequency_Hours ?? null,
      begin_Time: '',
      end_Time: '',
      last_Time_Dry_Weight: cureInfoForm.last_Time_Dry_Weight ?? null,
      dry_Weight: cureInfoForm.dry_Weight ?? null,
      before_Dry_Weight: cureInfoForm.before_Dry_Weight ?? null,
      after_Dry_Weight: cureInfoForm.after_Dry_Weight ?? null,
      before_Systolic_Pressure: cureInfoForm.before_Systolic_Pressure ?? null,
      before_Diastolic_Pressure: cureInfoForm.before_Diastolic_Pressure ?? null,
      after_Systolic_Pressure: cureInfoForm.after_Systolic_Pressure ?? null,
      after_Diastolic_Pressure: cureInfoForm.after_Diastolic_Pressure ?? null,
      dry_Water_Value: null,
      before_Temperature: cureInfoForm.before_Temperature ?? null,
      after_Temperature: cureInfoForm.after_Temperature ?? null,
      before_Heart_Rate: cureInfoForm.before_Heart_Rate ?? null,
      after_Heart_Rate: cureInfoForm.after_Heart_Rate ?? null,
      primary_Nurse: cureInfoForm.primary_Nurse || '',
      primary_Doctor: cureInfoForm.primary_Doctor || '',
      puncture_Nurse: cureInfoForm.puncture_Nurse || '',
      machine_Id: '',
      vascular_Access_Id: cureInfoForm.vascular_Access_Id || '',
      heparin_Species: cureInfoForm.heparin_Species || '',
      first_Heparin: cureInfoForm.first_Heparin ?? null,
      dosis_Sustentativa: cureInfoForm.dosis_Sustentativa ?? null,
      machine_Type: cureInfoForm.machine_Type || '',
      purifier_Name: cureInfoForm.purifier_Name || '',
      purifier_M2: cureInfoForm.purifier_M2 ?? null,
      use_Type: cureInfoForm.use_Type || '',
      reuse_Times: cureInfoForm.reuse_Times ?? null,
      a_Liquid: cureInfoForm.a_Liquid || '',
      b_Liquid: cureInfoForm.b_Liquid || '',
      bircarbonate: cureInfoForm.bircarbonate ?? null,
      amylaceum: cureInfoForm.amylaceum ?? null,
      summary: '',
      cure_Create_Date: new Date().toISOString().split('T')[0],
      vascular_Access_Firm: '',
      vascular_Access_Glide: '',
      vascular_Access_Swelling: '',
      vascular_Access_Errhyisis: '',
      vascular_Access_Thrombus: '',
      vascular_Access_Blood: '',
      vascular_Access_Blood_Infect: cureInfoForm.vascular_Access_Blood_Infect || '否',
      filtration_Displacement_Liquid: cureInfoForm.filtration_Displacement_Liquid ?? null,
      filtration_Percolate: cureInfoForm.filtration_Percolate ?? null,
      displacement_Liquid: null,
      percolate: null,
      doctor_Advice: cureInfoForm.doctor_Advice || '',
      summary2: '',
      summary3: '',
      check_Nurse: '',
      first_Drug_Unit: '',
      second_Drug_Unit: '',
      vein: '',
      dry_Weight_Tag: '',
      before_Dry_Weight_Tag: '',
      after_Dry_Weight_Tag: '',
      reuse_Times_Tag: '',
      machine_Id_Tag: '',
      blood_Up: '',
      blood_Type: '',
      blood_Transfusion: '',
      coagulation_In_Dialyser: '',
      in_Basket_Clean: '',
      in_Basket_Red_Hot: '',
      in_Basket_Ecchymosis: '',
      in_Basket_Tremor: '',
      in_Basket_Noise: '',
      in_Basket_Vascular_Elasticity: '',
      in_Basket_Vascular_Other: '',
      in_Basket_Wound_Allergy: '',
      in_Basket_Plaster_Allergy: '',
      vascular_Access_Type: cureInfoForm.vascular_Access_Type || '',
      subjective_Comfort: '',
      before_BP: null,
      after_BP: null,
      frequency_Minute: null,
      displacement_Mode: '',
      displacement_Recipe: '',
      displacement_Special_Adjust: '',
      anticoagulant_Use: '',
      special_Matter: cureInfoForm.special_Matter || '',
      ufr2: null,
      displacement_Flow: null,
      uf: null,
      sum_Uf: cureInfoForm.sum_Uf ?? null,
      focus_Level: '',
      senses: '',
      allergic: '',
      bt: '',
      bp: '',
      br: '',
      afterbt: '',
      afterbp: '',
      afterbr: '',
      in_Bed: '',
      actual_Cleanup_Hour: null,
      actual_Cleanup_Minute: null
    }

    const result = await store.saveCureInfo(cureInfo)
    uni.hideLoading()

    if (result.success) {
      uni.showToast({ title: '保存成功', icon: 'success' })
    } else {
      uni.showToast({ title: result.message || '保存失败', icon: 'none' })
    }
  } catch (error: any) {
    uni.hideLoading()
    console.error('Save error:', error)
    uni.showToast({ title: '保存失败', icon: 'none' })
  }
}

const handleRefresh = async () => {
  try {
    uni.showLoading({ title: '刷新中...' })
    await loadPatientData()
    uni.hideLoading()
    uni.showToast({ title: '刷新成功', icon: 'success' })
  } catch (error: any) {
    uni.hideLoading()
    console.error('Refresh error:', error)
    uni.showToast({ title: '刷新失败', icon: 'none' })
  }
}

const navigateToOrders = async () => {
  bottomTab.value = 'orders'
  if (!store.currentPatient) {
    uni.showToast({ title: '未选择患者', icon: 'none' })
    return
  }

  try {
    uni.showLoading({ title: '加载医嘱...' })
    await store.fetchTempOrders(store.currentPatient.HEMODIALYSIS_ID)
    uni.hideLoading()
    uni.navigateTo({ url: '/pages/orders/index' })
  } catch (error: any) {
    uni.hideLoading()
    console.error('Fetch orders error:', error)
    uni.showToast({ title: '加载医嘱失败', icon: 'none' })
  }
}

const navigateToLongOrders = async () => {
  bottomTab.value = 'longOrders'
  if (!store.currentPatient) {
    uni.showToast({ title: '未选择患者', icon: 'none' })
    return
  }

  try {
    uni.showLoading({ title: '加载医嘱...' })
    await store.fetchLongOrders(store.currentPatient.HEMODIALYSIS_ID)
    uni.hideLoading()
    uni.navigateTo({ url: '/pages/long-orders/index' })
  } catch (error: any) {
    uni.hideLoading()
    console.error('Fetch long orders error:', error)
    uni.showToast({ title: '加载医嘱失败', icon: 'none' })
  }
}

const navigateToParams = async () => {
  bottomTab.value = 'params'
  if (!store.currentCureInfo?.cure_Id) {
    uni.showToast({ title: '请先保存治疗信息', icon: 'none' })
    return
  }

  try {
    uni.showLoading({ title: '加载参数...' })
    await store.fetchHemoParameters(store.currentCureInfo.cure_Id)
    uni.hideLoading()
    uni.navigateTo({ url: '/pages/dialysis-param/index' })
  } catch (error: any) {
    uni.hideLoading()
    console.error('Fetch parameters error:', error)
    uni.showToast({ title: '加载参数失败', icon: 'none' })
  }
}

const navigateToSummary = () => {
  bottomTab.value = 'summary'
  uni.navigateTo({ url: '/pages/summary/index' })
}

onMounted(() => {
  const pages = getCurrentPages()
  const currentPage = pages[pages.length - 1]
  const options = (currentPage as any)?.options || {}
  scheduleId.value = options.id || ''

  loadPatientData()
})
</script>

<style lang="scss" scoped>
.patient-detail-page {
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

.page-content {
  padding-top: calc(88rpx + env(safe-area-inset-top));
}

.patient-header {
  background: $card-background;
  padding: $spacing-md;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.patient-info-card {
  flex: 1;
}

.info-row {
  display: flex;
  font-size: $font-size-sm;
  margin-bottom: $spacing-xs;
}

.info-label {
  color: $text-hint;
  min-width: 140rpx;
}

.info-value {
  color: $text-secondary;
}

.patient-status-badge {
  padding: $spacing-xs $spacing-sm;
  border-radius: $radius-sm;
  font-size: $font-size-xs;
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

.form-section {
  background: $card-background;
  border-radius: $radius-md;
  margin-bottom: $spacing-md;
  overflow: hidden;
}

.section-title {
  padding: $spacing-sm $spacing-md;
  background: rgba($primary-color, 0.05);
  font-size: $font-size-sm;
  font-weight: 500;
  color: $primary-color;
}

.form-item {
  display: flex;
  align-items: center;
  padding: $spacing-md;
  border-bottom: 1rpx solid $divider-color;

  &:last-child {
    border-bottom: none;
  }
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
  text-align: right;
}

.textarea-item {
  align-items: flex-start;
}

.form-textarea {
  flex: 1;
  min-height: 120rpx;
  font-size: $font-size-base;
  padding: $spacing-sm;
  border: 1rpx solid $border-color;
  border-radius: $radius-sm;
  background: $background-color;
}

.radio-group {
  flex: 1;
  display: flex;
  gap: $spacing-lg;
  justify-content: flex-end;
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
    border-color: $primary-color;

    &::after {
      display: block;
    }
  }
}

.save-btn-container {
  margin-top: $spacing-xl;
  padding-bottom: $spacing-xl;
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
  display: flex;
  align-items: center;
  justify-content: center;

  &[disabled] {
    background: $text-hint;
  }
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
  z-index: 100;
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
