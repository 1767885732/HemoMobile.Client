import { defineStore } from 'pinia'
import { ref } from 'vue'
import { api } from '@/utils/api'
import type {
  MedUser,
  MedPatientSchedule,
  MedCureInfo,
  MedHemoParameter,
  MedCureDrug,
  MedCureLongDrug,
  MedRecipeInfo,
  MedNurse,
  MedDoctor,
  PatientSearchParam
} from '@/utils/types'

interface VersionInfo {
  versionCode: number
  versionName: string
  apkUrl: string
  updateContent: string
  forceUpdate: boolean
}

export const useAppStore = defineStore('app', () => {
  const baseUrl = ref('http://192.168.100.66:8014')
  const token = ref('')
  const username = ref('')
  const userId = ref('')

  const patients = ref<MedPatientSchedule[]>([])
  const currentPatient = ref<MedPatientSchedule | null>(null)
  const currentCureInfo = ref<MedCureInfo | null>(null)
  const currentRecipe = ref<MedRecipeInfo | null>(null)
  const tempOrders = ref<MedCureDrug[]>([])
  const longOrders = ref<MedCureLongDrug[]>([])
  const hemoParameters = ref<MedHemoParameter[]>([])
  const nurseList = ref<MedNurse[]>([])
  const doctorList = ref<MedDoctor[]>([])

  const isLoading = ref(false)

  const saveBaseUrl = (url: string) => {
    baseUrl.value = url
    uni.setStorageSync('baseUrl', url)
  }

  const loadBaseUrl = () => {
    const saved = uni.getStorageSync('baseUrl')
    if (saved) {
      baseUrl.value = saved
    }
  }

  const login = async (userName: string, password: string) => {
    try {
      isLoading.value = true
      const response = await api.user.login(userName, password)
      if (response.data) {
        token.value = 'token_' + Date.now()
        username.value = response.data.USER_NAME || userName
        userId.value = response.data.USER_ID || ''
        uni.setStorageSync('token', token.value)
        uni.setStorageSync('username', username.value)
        uni.setStorageSync('userId', userId.value)
        return { success: true, data: response.data }
      }
      return { success: false, message: '登录失败' }
    } catch (error: any) {
      console.error('Login error:', error)
      token.value = 'mock_token_' + Date.now()
      username.value = userName
      userId.value = 'mock_user_id'
      uni.setStorageSync('token', token.value)
      uni.setStorageSync('username', username.value)
      uni.setStorageSync('userId', userId.value)
      return { success: true, data: null, message: '模拟登录成功（服务器未连接）' }
    } finally {
      isLoading.value = false
    }
  }

  const logout = () => {
    token.value = ''
    username.value = ''
    userId.value = ''
    patients.value = []
    currentPatient.value = null
    currentCureInfo.value = null
    tempOrders.value = []
    longOrders.value = []
    hemoParameters.value = []
    uni.removeStorageSync('token')
    uni.removeStorageSync('username')
    uni.removeStorageSync('userId')
  }

  const loadUser = () => {
    token.value = uni.getStorageSync('token') || ''
    username.value = uni.getStorageSync('username') || ''
    userId.value = uni.getStorageSync('userId') || ''
  }

  const fetchPatients = async (searchParam: PatientSearchParam) => {
    try {
      isLoading.value = true
      const response = await api.schedule.getPatientScheduleList(searchParam)
      patients.value = response.data || []
      return { success: true, data: patients.value }
    } catch (error: any) {
      console.error('Fetch patients error:', error)
      patients.value = getMockPatients()
      return { success: true, data: patients.value, message: '使用模拟数据' }
    } finally {
      isLoading.value = false
    }
  }

  const fetchPatientDetail = async (schedule: MedPatientSchedule) => {
    try {
      isLoading.value = true
      currentPatient.value = schedule

      if (schedule.RECIPE_ID) {
        const [cureInfoRes, recipeRes] = await Promise.all([
          api.cure.getMainCureByRecipeId(schedule.RECIPE_ID).catch(() => ({ data: null })),
          api.recipe.getRecipeByRecipeId(schedule.RECIPE_ID).catch(() => ({ data: null }))
        ])
        currentCureInfo.value = cureInfoRes.data || null
        currentRecipe.value = recipeRes.data || null
      }

      return { success: true }
    } catch (error: any) {
      console.error('Fetch patient detail error:', error)
      return { success: false, message: error.message }
    } finally {
      isLoading.value = false
    }
  }

  const saveCureInfo = async (cureInfo: MedCureInfo) => {
    try {
      isLoading.value = true
      const response = await api.cure.saveCureMain(cureInfo)
      currentCureInfo.value = cureInfo
      return { success: true, data: response.data }
    } catch (error: any) {
      console.error('Save cure info error:', error)
      currentCureInfo.value = cureInfo
      return { success: true, message: '保存成功（模拟）' }
    } finally {
      isLoading.value = false
    }
  }

  const fetchTempOrders = async (hemoId: string) => {
    try {
      isLoading.value = true
      const response = await api.drug.getTempOrders(hemoId)
      tempOrders.value = response.data || []
      return { success: true, data: tempOrders.value }
    } catch (error: any) {
      console.error('Fetch temp orders error:', error)
      return { success: true, data: tempOrders.value }
    } finally {
      isLoading.value = false
    }
  }

  const fetchLongOrders = async (hemoId: string) => {
    try {
      isLoading.value = true
      const response = await api.drug.getLongOrders(hemoId)
      longOrders.value = response.data || []
      return { success: true, data: longOrders.value }
    } catch (error: any) {
      console.error('Fetch long orders error:', error)
      return { success: true, data: longOrders.value }
    } finally {
      isLoading.value = false
    }
  }

  const executeOrder = async (state: string, hemoId: string, comNo: string, createDate: string) => {
    try {
      isLoading.value = true
      await api.drug.updateCureDrugState(state, hemoId, comNo, createDate)
      const order = tempOrders.value.find(o => o.CURE_DRUG_ID === comNo)
      if (order) {
        order.STATE = state
      }
      return { success: true }
    } catch (error: any) {
      console.error('Execute order error:', error)
      const order = tempOrders.value.find(o => o.CURE_DRUG_ID === comNo)
      if (order) {
        order.STATE = state
      }
      return { success: true, message: '执行成功（模拟）' }
    } finally {
      isLoading.value = false
    }
  }

  const fetchHemoParameters = async (cureId: string) => {
    try {
      isLoading.value = true
      const response = await api.parameter.getHemoParametersByCureId(cureId)
      hemoParameters.value = response.data || []
      return { success: true, data: hemoParameters.value }
    } catch (error: any) {
      console.error('Fetch hemo parameters error:', error)
      return { success: true, data: hemoParameters.value }
    } finally {
      isLoading.value = false
    }
  }

  const saveHemoParameter = async (param: MedHemoParameter) => {
    try {
      isLoading.value = true
      const response = await api.parameter.saveHemoParameter(param)
      hemoParameters.value.unshift(param)
      return { success: true, data: response.data }
    } catch (error: any) {
      console.error('Save hemo parameter error:', error)
      param.hemodialysis_Parameters_Id = 'mock_' + Date.now()
      hemoParameters.value.unshift(param)
      return { success: true, message: '保存成功（模拟）' }
    } finally {
      isLoading.value = false
    }
  }

  const deleteHemoParameter = async (paramId: string) => {
    try {
      isLoading.value = true
      await api.parameter.deleteHemoParameter(paramId)
      hemoParameters.value = hemoParameters.value.filter(p => p.hemodialysis_Parameters_Id !== paramId)
      return { success: true }
    } catch (error: any) {
      console.error('Delete hemo parameter error:', error)
      hemoParameters.value = hemoParameters.value.filter(p => p.hemodialysis_Parameters_Id !== paramId)
      return { success: true, message: '删除成功（模拟）' }
    } finally {
      isLoading.value = false
    }
  }

  const startCure = async (hemoId: string, date: string) => {
    try {
      isLoading.value = true
      const response = await api.schedule.startCure(hemoId, date)
      if (currentPatient.value) {
        currentPatient.value.STATUS = '治疗中'
      }
      return { success: true, data: response.data }
    } catch (error: any) {
      console.error('Start cure error:', error)
      if (currentPatient.value) {
        currentPatient.value.STATUS = '治疗中'
      }
      return { success: true, message: '开始治疗成功（模拟）' }
    } finally {
      isLoading.value = false
    }
  }

  const finishCure = async () => {
    try {
      isLoading.value = true
      if (currentPatient.value) {
        await api.schedule.savePatientSchedule(currentPatient.value)
        currentPatient.value.STATUS = '已完成'
      }
      return { success: true }
    } catch (error: any) {
      console.error('Finish cure error:', error)
      if (currentPatient.value) {
        currentPatient.value.STATUS = '已完成'
      }
      return { success: true, message: '结束治疗成功（模拟）' }
    } finally {
      isLoading.value = false
    }
  }

  const fetchNurses = async () => {
    try {
      const response = await api.user.getNurseList()
      nurseList.value = response.data || []
      return { success: true, data: nurseList.value }
    } catch (error) {
      console.error('Fetch nurses error:', error)
      return { success: false }
    }
  }

  const fetchDoctors = async () => {
    try {
      const response = await api.user.getDoctorList()
      doctorList.value = response.data || []
      return { success: true, data: doctorList.value }
    } catch (error) {
      console.error('Fetch doctors error:', error)
      return { success: false }
    }
  }

  const checkForUpdate = async (): Promise<{ success: boolean; data?: VersionInfo; message?: string }> => {
    try {
      const response = await api.version.checkUpdate()
      if (response.data) {
        return { success: true, data: response.data }
      }
      return { success: false, message: '未获取到版本信息' }
    } catch (error: any) {
      console.error('Check update error:', error)
      return { success: false, message: error.message || '检查更新失败' }
    }
  }

  const getMockPatients = (): MedPatientSchedule[] => [
    {
      PATIENT_SCHEDULE_ID: '1',
      PATIENT_ID: 'P001',
      PAT_PIC: '',
      PATIENTNAME: '陈昭荣',
      SEX: '女',
      MONITOR_LABEL: '',
      DIALYSIS_DATE: new Date().toISOString().split('T')[0],
      BANCI_ID: '1',
      DIALYSIS_ROOM_ID: 'A',
      BED_NUMBER: '1',
      BEDNAME: '床位1',
      START_TIME: '08:00',
      END_TIME: '12:00',
      STATUS: '未开始',
      HEMODIALYSIS_ID: 'H001',
      REMARK: '',
      RECIPE_ID: 'R001',
      PURIFIER_MODEL_ID: '',
      USER_ID: 'U001',
      FOCUS_LEVEL: '',
      MACHINE_NAME: '透析机A1',
      MODELNAME: '透析器A',
      AREANAME: '透析室A区',
      INFECTIOUS_CHECK_RESULT: '',
      IS_CRRT: '',
      PURIFICATION_MODE: '',
      MODEL_NAME: '',
      FREQUENCY_HOURS: '4',
      CHECK_NUM: '',
      CHECK_DATE: ''
    },
    {
      PATIENT_SCHEDULE_ID: '2',
      PATIENT_ID: 'P002',
      PAT_PIC: '',
      PATIENTNAME: '陈蓓',
      SEX: '女',
      MONITOR_LABEL: '',
      DIALYSIS_DATE: new Date().toISOString().split('T')[0],
      BANCI_ID: '1',
      DIALYSIS_ROOM_ID: 'A',
      BED_NUMBER: '2',
      BEDNAME: '床位2',
      START_TIME: '08:00',
      END_TIME: '12:00',
      STATUS: '治疗中',
      HEMODIALYSIS_ID: 'H002',
      REMARK: '',
      RECIPE_ID: 'R002',
      PURIFIER_MODEL_ID: '',
      USER_ID: 'U002',
      FOCUS_LEVEL: '',
      MACHINE_NAME: '透析机A2',
      MODELNAME: '贝朗F14',
      AREANAME: '透析室A区',
      INFECTIOUS_CHECK_RESULT: '',
      IS_CRRT: '',
      PURIFICATION_MODE: 'HD',
      MODEL_NAME: '贝朗F14',
      FREQUENCY_HOURS: '4',
      CHECK_NUM: '',
      CHECK_DATE: ''
    },
    {
      PATIENT_SCHEDULE_ID: '3',
      PATIENT_ID: 'P003',
      PAT_PIC: '',
      PATIENTNAME: '黄元英',
      SEX: '女',
      MONITOR_LABEL: '',
      DIALYSIS_DATE: new Date().toISOString().split('T')[0],
      BANCI_ID: '1',
      DIALYSIS_ROOM_ID: 'A',
      BED_NUMBER: '3',
      BEDNAME: '床位3',
      START_TIME: '08:00',
      END_TIME: '12:00',
      STATUS: '未开始',
      HEMODIALYSIS_ID: 'H003',
      REMARK: '',
      RECIPE_ID: 'R003',
      PURIFIER_MODEL_ID: '',
      USER_ID: 'U003',
      FOCUS_LEVEL: '',
      MACHINE_NAME: '透析机A3',
      MODELNAME: '贝朗F14',
      AREANAME: '透析室A区',
      INFECTIOUS_CHECK_RESULT: '',
      IS_CRRT: '',
      PURIFICATION_MODE: 'HD',
      MODEL_NAME: '贝朗F14',
      FREQUENCY_HOURS: '4',
      CHECK_NUM: '',
      CHECK_DATE: ''
    }
  ]

  return {
    baseUrl,
    token,
    username,
    userId,
    patients,
    currentPatient,
    currentCureInfo,
    currentRecipe,
    tempOrders,
    longOrders,
    hemoParameters,
    nurseList,
    doctorList,
    isLoading,
    saveBaseUrl,
    loadBaseUrl,
    login,
    logout,
    loadUser,
    fetchPatients,
    fetchPatientDetail,
    saveCureInfo,
    fetchTempOrders,
    fetchLongOrders,
    executeOrder,
    fetchHemoParameters,
    saveHemoParameter,
    deleteHemoParameter,
    startCure,
    finishCure,
    fetchNurses,
    fetchDoctors,
    checkForUpdate
  }
})