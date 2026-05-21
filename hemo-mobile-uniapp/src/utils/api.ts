import { useAppStore } from '@/stores/app'
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
  MedVasularAccess,
  PatientSearchParam
} from './types'

interface RequestOptions {
  url: string
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE'
  data?: Record<string, any>
  header?: Record<string, string>
  loading?: boolean
}

interface VersionInfo {
  versionCode: number
  versionName: string
  apkUrl: string
  updateContent: string
  forceUpdate: boolean
}

const API_CONFIG = {
  USER_API: '/api/MedUser',
  GET_STAFF_DICT_LIST: '/Api/MedUser/GetStaffDictList',
  GET_DOCTOR_LIST: '/Api/MedUser/GetDoctorList',
  GET_NEW_VERSION: '/Api/MedUser/GetNewVersion',
  CONFIG_API: '/api/Config/GetConfigList',
  PATIENTSCHEDULE_API: '/api/Schedule/GetPatientScheduleList',
  GETPATIENTSCHEDULEBYDATEANDHEMOID_API: '/api/Schedule/GetPatientScheduleByDateAndHemoId',
  START_CURE: '/api/Schedule/StartCure',
  SAVEPATIENTSCHEDULE_API: '/api/Schedule/SavePatientSchedule',
  GETNEWCUREID_API: '/Api/Cure/GetNewCureID',
  RECIPE_API: '/Api/Recipe/GetRecipeByHemodialysisIDAndDate',
  GETRECIPEBYRECIPEID_API: '/Api/Recipe/GetRecipeByRecipeId',
  SAVECUREMAIN_API: '/Api/Cure/SaveCureMain',
  GETCLEANUPTIMES_API: '/Api/Cure/GetCleanUpTimes',
  GETLASTTIMEDRYWEIGHTBYID_API: '/Api/Cure/GetLastTimeDryWeightById',
  GETMAINCUREBYCUREID_API: '/Api/Cure/GetMainCureByCureId',
  GETMAINCUREBYRECIPEID_API: '/Api/Cure/GetMainCureByRecipeId',
  GETMAINCURECOUNT_API: '/Api/Cure/GetMainCureCount',
  GETHEMOPARAMETERSBYCUREID_API: '/Api/MedHemodialysisParameters/GetHemoParametersByCureID',
  DELETE_HEMO_PARAMETERS_BY_ID: '/Api/MedHemodialysisParameters/DeleteHemoParametersById',
  SAVE_HEMO_PARAMETERS: '/Api/MedHemodialysisParameters/SaveHemoParameters',
  GET_CURE_DRUG_BY_HEMOID: '/Api/CureDrug/GetValidCureDrugByHemoId',
  UPDATE_CURE_DRUG_STATE_BY_PARMA: '/Api/CureDrug/UpdateCureDrugStateByParma',
  SAVECUREDRUG_API: '/Api/CureDrug/SaveCureDrug',
  GET_LONG_CURE_DRUG_BY_HEMOID: '/Api/CureDrug/GetLongCureDrugByHemoID',
  GETPATIENTVASULARACCESSBYHEMOID_API: '/Api/VascularAccess/GetPatientVasularAccessByHemoId',
  UPLOAD_PATIENT_PHOTO: '/Api/MedPatient/UploadPatientPhoto'
}

const request = async <T = any>(options: RequestOptions): Promise<T> => {
  const store = useAppStore()
  const baseUrl = store.baseUrl || 'http://192.168.100.66:8014'

  if (options.loading !== false) {
    uni.showLoading({ title: '加载中...', mask: true })
  }

  const header: Record<string, string> = {
    'Content-Type': 'application/json',
    ...options.header
  }

  if (store.token) {
    header['Authorization'] = `Bearer ${store.token}`
  }

  return new Promise((resolve, reject) => {
    uni.request({
      url: baseUrl + options.url,
      method: options.method || 'GET',
      data: options.data,
      header,
      success: (res: any) => {
        uni.hideLoading()
        if (res.statusCode === 200) {
          const responseData = res.data
          if (responseData.success || responseData.code === 200 || responseData.status === 200) {
            resolve(responseData as T)
          } else {
            uni.showToast({
              title: responseData.message || '请求失败',
              icon: 'none'
            })
            reject(new Error(responseData.message || '请求失败'))
          }
        } else if (res.statusCode === 401) {
          uni.showToast({ title: '未授权，请重新登录', icon: 'none' })
          uni.navigateTo({ url: '/pages/login/index' })
          reject(new Error('未授权'))
        } else {
          uni.showToast({
            title: `网络异常 (${res.statusCode})`,
            icon: 'none'
          })
          reject(new Error(`网络异常: ${res.statusCode}`))
        }
      },
      fail: (err: any) => {
        uni.hideLoading()
        const errorMsg = err.errMsg || '网络连接失败'
        uni.showToast({
          title: errorMsg,
          icon: 'none'
        })
        reject(err)
      }
    })
  })
}

const get = <T = any>(url: string, data?: Record<string, any>, loading = true) => {
  return request<T>({ url, method: 'GET', data, loading })
}

const post = <T = any>(url: string, data?: Record<string, any>, loading = true) => {
  return request<T>({ url, method: 'POST', data, loading })
}

const put = <T = any>(url: string, data?: Record<string, any>, loading = true) => {
  return request<T>({ url, method: 'PUT', data, loading })
}

const del = <T = any>(url: string, data?: Record<string, any>, loading = true) => {
  return request<T>({ url, method: 'DELETE', data, loading })
}

export const api = {
  config: API_CONFIG,

  version: {
    checkUpdate: () => {
      return get<{ data: VersionInfo }>(API_CONFIG.GET_NEW_VERSION, {}, false)
    }
  },

  user: {
    login: (userName: string, password: string) => {
      return get<{ data: MedUser }>(`${API_CONFIG.USER_API}?userName=${encodeURIComponent(userName)}&password=${encodeURIComponent(password)}`)
    },

    getNurseList: () => {
      return get<{ data: MedNurse[] }>(API_CONFIG.GET_STAFF_DICT_LIST)
    },

    getDoctorList: () => {
      return get<{ data: MedDoctor[] }>(API_CONFIG.GET_DOCTOR_LIST)
    }
  },

  schedule: {
    getPatientScheduleList: (searchParam: PatientSearchParam) => {
      const params: Record<string, string> = {
        treamentDate: searchParam.date,
        userId: searchParam.userId || '',
        classes: searchParam.timeRangeType,
        area: searchParam.roomId
      }
      const queryString = Object.entries(params)
        .filter(([_, v]) => v)
        .map(([k, v]) => `${k}=${encodeURIComponent(v)}`)
        .join('&')
      return get<{ data: MedPatientSchedule[] }>(`${API_CONFIG.PATIENTSCHEDULE_API}?${queryString}`)
    },

    getPatientScheduleByDateAndHemoId: (date: string, hemoId: string) => {
      return get<{ data: MedPatientSchedule }>(
        `${API_CONFIG.GETPATIENTSCHEDULEBYDATEANDHEMOID_API}?date=${encodeURIComponent(date)}&hemoId=${encodeURIComponent(hemoId)}`
      )
    },

    startCure: (hemoId: string, date: string) => {
      return post<{ data: string }>(
        `${API_CONFIG.START_CURE}?hemoId=${encodeURIComponent(hemoId)}&date=${encodeURIComponent(date)}`
      )
    },

    savePatientSchedule: (patientSchedule: MedPatientSchedule) => {
      return post<{ data: string }>(API_CONFIG.SAVEPATIENTSCHEDULE_API, patientSchedule)
    }
  },

  cure: {
    getMainCureByRecipeId: (recipeId: string) => {
      return get<{ data: MedCureInfo }>(
        `${API_CONFIG.GETMAINCUREBYRECIPEID_API}?recipeId=${encodeURIComponent(recipeId)}`
      )
    },

    getMainCureByCureId: (cureId: string) => {
      return get<{ data: MedCureInfo }>(
        `${API_CONFIG.GETMAINCUREBYCUREID_API}?cureId=${encodeURIComponent(cureId)}`
      )
    },

    saveCureMain: (cureInfo: MedCureInfo) => {
      return post<{ data: string }>(API_CONFIG.SAVECUREMAIN_API, cureInfo)
    },

    getLastTimeDryWeightById: (hemoId: string, createDate: string) => {
      return get<{ data: string }>(
        `${API_CONFIG.GETLASTTIMEDRYWEIGHTBYID_API}?hemoId=${encodeURIComponent(hemoId)}&createDate=${encodeURIComponent(createDate)}`
      )
    },

    getCleanUpTimes: (hemoId: string) => {
      return get<{ data: string }>(
        `${API_CONFIG.GETCLEANUPTIMES_API}?hemoId=${encodeURIComponent(hemoId)}`
      )
    }
  },

  recipe: {
    getRecipeByRecipeId: (recipeId: string) => {
      return get<{ data: MedRecipeInfo }>(
        `${API_CONFIG.GETRECIPEBYRECIPEID_API}?recipeId=${encodeURIComponent(recipeId)}`
      )
    },

    getRecipeByHemodialysisIDAndDate: (hemoId: string, date: string) => {
      return get<{ data: MedRecipeInfo }>(
        `${API_CONFIG.RECIPE_API}?hemodialysisId=${encodeURIComponent(hemoId)}&date=${encodeURIComponent(date)}`
      )
    }
  },

  drug: {
    getTempOrders: (hemoId: string) => {
      return get<{ data: MedCureDrug[] }>(
        `${API_CONFIG.GET_CURE_DRUG_BY_HEMOID}?hemoId=${encodeURIComponent(hemoId)}`
      )
    },

    getLongOrders: (hemoId: string) => {
      return get<{ data: MedCureLongDrug[] }>(
        `${API_CONFIG.GET_LONG_CURE_DRUG_BY_HEMOID}?hemoId=${encodeURIComponent(hemoId)}`
      )
    },

    updateCureDrugState: (state: string, hemoId: string, comNo: string, createDate: string) => {
      return post<{ data: string }>(
        `${API_CONFIG.UPDATE_CURE_DRUG_STATE_BY_PARMA}?state=${encodeURIComponent(state)}&hemoId=${encodeURIComponent(hemoId)}&comNo=${encodeURIComponent(comNo)}&createDate=${encodeURIComponent(createDate)}`
      )
    },

    saveCureDrug: (
      cureDrug: Partial<MedCureDrug>,
      cureId: string,
      recipeId: string,
      execNurse: string,
      state: string
    ) => {
      return post<{ data: string }>(
        `${API_CONFIG.SAVECUREDRUG_API}?cureId=${encodeURIComponent(cureId)}&recipeId=${encodeURIComponent(recipeId)}&execNurse=${encodeURIComponent(execNurse)}&state=${encodeURIComponent(state)}`,
        cureDrug
      )
    }
  },

  parameter: {
    getHemoParametersByCureId: (cureId: string) => {
      return get<{ data: MedHemoParameter[] }>(
        `${API_CONFIG.GETHEMOPARAMETERSBYCUREID_API}?cureId=${encodeURIComponent(cureId)}`
      )
    },

    deleteHemoParameter: (paramId: string) => {
      return post<{ data: string }>(
        `${API_CONFIG.DELETE_HEMO_PARAMETERS_BY_ID}?paramId=${encodeURIComponent(paramId)}`
      )
    },

    saveHemoParameter: (hemoParameter: MedHemoParameter) => {
      return post<{ data: string }>(API_CONFIG.SAVE_HEMO_PARAMETERS, hemoParameter)
    }
  },

  vascularAccess: {
    getPatientVasularAccessByHemoId: (hemoId: string) => {
      return get<{ data: MedVasularAccess[] }>(
        `${API_CONFIG.GETPATIENTVASULARACCESSBYHEMOID_API}?hemoId=${encodeURIComponent(hemoId)}`
      )
    }
  },

  patient: {
    uploadPhoto: (hemoId: string, base64Image: string) => {
      return post<{ data: string }>(
        `${API_CONFIG.UPLOAD_PATIENT_PHOTO}?hemoId=${encodeURIComponent(hemoId)}`,
        { photo: base64Image }
      )
    }
  }
}

export default api