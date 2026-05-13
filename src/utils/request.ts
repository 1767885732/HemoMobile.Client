import { useAppStore } from '@/stores/app'

export interface RequestOptions {
  url: string
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE'
  data?: Record<string, any>
  header?: Record<string, string>
}

export const request = async <T = any>(options: RequestOptions): Promise<T> => {
  const store = useAppStore()
  const baseUrl = store.baseUrl || 'http://192.168.100.66:8014'
  
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
      success: (res) => {
        if (res.statusCode === 200) {
          resolve(res.data as T)
        } else {
          reject(new Error(`Request failed with status ${res.statusCode}`))
        }
      },
      fail: (err) => {
        reject(err)
      }
    })
  })
}

export const get = <T = any>(url: string, data?: Record<string, any>) => {
  return request<T>({ url, method: 'GET', data })
}

export const post = <T = any>(url: string, data?: Record<string, any>) => {
  return request<T>({ url, method: 'POST', data })
}

export const put = <T = any>(url: string, data?: Record<string, any>) => {
  return request<T>({ url, method: 'PUT', data })
}

export const del = <T = any>(url: string, data?: Record<string, any>) => {
  return request<T>({ url, method: 'DELETE', data })
}