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
  const baseUrl = ref('http://localhost:8014')
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
      return { success: false, message: '登录失败，用户名或密码错误' }
    } catch (error: any) {
      console.error('Login error:', error)
      return { success: false, message: error.message || '网络请求失败，请检查网络连接' }
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
      return { success: false, message: error.message || '获取患者列表失败' }
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
          api.cure.getMainCureByRecipeId(schedule.RECIPE_ID),
          api.recipe.getRecipeByRecipeId(schedule.RECIPE_ID)
        ])
        currentCureInfo.value = cureInfoRes.data || null
        currentRecipe.value = recipeRes.data || null
      }

      return { success: true }
    } catch (error: any) {
      console.error('Fetch patient detail error:', error)
      return { success: false, message: error.message || '获取患者详情失败' }
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
      return { success: false, message: error.message || '保存治疗信息失败' }
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
      return { success: false, message: error.message || '获取临时医嘱失败' }
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
      return { success: false, message: error.message || '获取长期医嘱失败' }
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
      return { success: false, message: error.message || '执行医嘱失败' }
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
      return { success: false, message: error.message || '获取透析参数失败' }
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
      return { success: false, message: error.message || '保存透析参数失败' }
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
      return { success: false, message: error.message || '删除透析参数失败' }
    } finally {
      isLoading.value = false
    }
  }

  const startCure = async (hemoId: string, date: string) => {
    try {
      isLoading.value = true
      const response = await api.schedule.startCure(hemoId, date)
      if (currentPatient.value) {
        currentPatient.value.STATUS = '1'
      }
      return { success: true, data: response.data }
    } catch (error: any) {
      console.error('Start cure error:', error)
      return { success: false, message: error.message || '开始治疗失败' }
    } finally {
      isLoading.value = false
    }
  }

  const finishCure = async () => {
    try {
      isLoading.value = true
      if (currentPatient.value) {
        await api.schedule.savePatientSchedule(currentPatient.value)
        currentPatient.value.STATUS = '2'
      }
      return { success: true }
    } catch (error: any) {
      console.error('Finish cure error:', error)
      return { success: false, message: error.message || '结束治疗失败' }
    } finally {
      isLoading.value = false
    }
  }

  const fetchNurses = async () => {
    try {
      const response = await api.user.getNurseList()
      nurseList.value = response.data || []
      return { success: true, data: nurseList.value }
    } catch (error: any) {
      console.error('Fetch nurses error:', error)
      return { success: false, message: error.message || '获取护士列表失败' }
    }
  }

  const fetchDoctors = async () => {
    try {
      const response = await api.user.getDoctorList()
      doctorList.value = response.data || []
      return { success: true, data: doctorList.value }
    } catch (error: any) {
      console.error('Fetch doctors error:', error)
      return { success: false, message: error.message || '获取医生列表失败' }
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
      return { success: false, message: '检查更新失败' }
    }
  }

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
