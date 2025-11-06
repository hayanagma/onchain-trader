import axios from 'axios'
import { navigateTo } from '#app'
import { useAdminAuthStore } from '~/stores/adminAuth'
import { useUserAuthStore } from '~/stores/userAuth'

export const useApi = () => {
  const config = useRuntimeConfig()

  const api = axios.create({
    baseURL: config.public.apiBase,
    withCredentials: true,
  })

  // use shared Pinia instances
  const adminAuth = useAdminAuthStore()
  const userAuth = useUserAuthStore()

  // attach Authorization headers
  api.interceptors.request.use((req) => {
    if (!req.url) return req

    if ((req.url.startsWith('/auth/admin') || req.url.startsWith('/admin')) && adminAuth.token) {
      req.headers['Authorization'] = `Bearer ${adminAuth.token}`
    } else if ((req.url.startsWith('/auth/trader') || req.url.startsWith('/trader')) && userAuth.token) {
      req.headers['Authorization'] = `Bearer ${userAuth.token}`
    }

    return req
  })

  // auto-refresh on 401
  api.interceptors.response.use(
    (res) => res,
    async (error) => {
      const originalRequest = error.config
      if (error.response?.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true
        try {
          if (originalRequest.url.startsWith('/auth/admin') || originalRequest.url.startsWith('/admin')) {
            if (adminAuth.token) {
              await adminAuth.refresh()
              originalRequest.headers['Authorization'] = `Bearer ${adminAuth.token}`
            }
          } else if (originalRequest.url.startsWith('/auth/trader') || originalRequest.url.startsWith('/trader')) {
            if (userAuth.token) {
              await userAuth.refresh()
              originalRequest.headers['Authorization'] = `Bearer ${userAuth.token}`
            }
          }
          return api(originalRequest)
        } catch {
          if (originalRequest.url.startsWith('/auth/admin') || originalRequest.url.startsWith('/admin')) {
            await adminAuth.logout()
            return navigateTo('/admin')
          } else if (originalRequest.url.startsWith('/auth/trader') || originalRequest.url.startsWith('/trader')) {
            await userAuth.logout()
            return navigateTo('/')
          }
          return Promise.reject(error)
        }
      }
      return Promise.reject(error)
    }
  )

  return api
}