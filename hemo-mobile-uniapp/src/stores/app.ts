import { defineStore } from 'pinia'
import { ref } from 'vue'

export interface Patient {
  id: string
  name: string
  gender: string
  dialysisNo: string
  treatmentProject: string
  infectiousDisease: string
  purifier: string
  scheduledTime: number
  checkInTime: string
  checkInOrder: string
  status: '未开始' | '治疗中' | '已完成'
}

export interface DialysisParam {
  id: string
  recordTime: string
  systolicPressure: string
  diastolicPressure: string
  venousPressure: string
  transmembranePressure: string
  respiration: string
}

export interface MedicalOrder {
  id: string
  type: 'long' | 'temp'
  medicineName: string
  dosage: string
  method: string
  operator: string
  createTime: string
  status: 'pending' | 'executed'
}

export const useAppStore = defineStore('app', () => {
  const baseUrl = ref('http://192.168.100.66:8014')
  const token = ref('')
  const username = ref('')
  
  const patients = ref<Patient[]>([
    {
      id: '1',
      name: '陈昭荣',
      gender: '女',
      dialysisNo: '26040900001',
      treatmentProject: '',
      infectiousDisease: '',
      purifier: '',
      scheduledTime: 0,
      checkInTime: '',
      checkInOrder: '',
      status: '未开始'
    },
    {
      id: '2',
      name: '陈蓓',
      gender: '女',
      dialysisNo: '23010400005',
      treatmentProject: 'HD',
      infectiousDisease: '',
      purifier: '贝朗F14',
      scheduledTime: 4,
      checkInTime: '',
      checkInOrder: '',
      status: '治疗中'
    },
    {
      id: '3',
      name: '黄元英',
      gender: '女',
      dialysisNo: '25010600060',
      treatmentProject: 'HD',
      infectiousDisease: '',
      purifier: '贝朗F14',
      scheduledTime: 4,
      checkInTime: '',
      checkInOrder: '',
      status: '未开始'
    }
  ])
  
  const longOrders = ref<MedicalOrder[]>([
    {
      id: '1',
      type: 'long',
      medicineName: '促红素',
      dosage: '每周2,4,6 3000单位',
      method: '静脉注射',
      operator: 'admin',
      createTime: '2026/4/14 14:48:22',
      status: 'executed'
    },
    {
      id: '2',
      type: 'long',
      medicineName: '左卡尼汀',
      dosage: '每天 1剂',
      method: '静脉注射',
      operator: 'admin',
      createTime: '2026/4/14 14:48:42',
      status: 'executed'
    }
  ])
  
  const tempOrders = ref<MedicalOrder[]>([
    {
      id: '3',
      type: 'temp',
      medicineName: '左卡尼汀',
      dosage: '1剂',
      method: '静脉注射',
      operator: 'admin',
      createTime: '2026/5/13 14:02:48',
      status: 'pending'
    },
    {
      id: '4',
      type: 'temp',
      medicineName: '地西泮注射液',
      dosage: '单位',
      method: '皮下注射',
      operator: 'admin',
      createTime: '2026/5/13 14:02:48',
      status: 'pending'
    }
  ])
  
  const dialysisParams = ref<DialysisParam[]>([])
  
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
  
  const login = (user: string, pass: string) => {
    token.value = 'mock_token_' + Date.now()
    username.value = user
    uni.setStorageSync('token', token.value)
    uni.setStorageSync('username', username.value)
  }
  
  const logout = () => {
    token.value = ''
    username.value = ''
    uni.removeStorageSync('token')
    uni.removeStorageSync('username')
  }
  
  const loadUser = () => {
    token.value = uni.getStorageSync('token') || ''
    username.value = uni.getStorageSync('username') || ''
  }
  
  const executeOrder = (orderId: string) => {
    const order = tempOrders.value.find(o => o.id === orderId)
    if (order) {
      order.status = 'executed'
    }
  }
  
  const addDialysisParam = (param: Omit<DialysisParam, 'id'>) => {
    dialysisParams.value.unshift({
      ...param,
      id: Date.now().toString()
    })
  }
  
  return {
    baseUrl,
    token,
    username,
    patients,
    longOrders,
    tempOrders,
    dialysisParams,
    saveBaseUrl,
    loadBaseUrl,
    login,
    logout,
    loadUser,
    executeOrder,
    addDialysisParam
  }
})