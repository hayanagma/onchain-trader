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

  const adminAuth = useAdminAuthStore()
  const userAuth = useUserAuthStore()

  // Attach Authorization dynamically
  api.interceptors.request.use((req) => {
    if (!req.url) return req

    if ((req.url.startsWith('/auth/admin') || req.url.startsWith('/admin')) && adminAuth.token) {
      req.headers['Authorization'] = `Bearer ${adminAuth.token}`
    } else if ((req.url.startsWith('/auth/trader') || req.url.startsWith('/trader')) && userAuth.token) {
      req.headers['Authorization'] = `Bearer ${userAuth.token}`
    }

    return req
  })

  // Global response handler with token refresh
  api.interceptors.response.use(
    (res) => res,
    async (error) => {
      const originalRequest = error.config

      // Refresh logic on expired session
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

      // Log errors internally, not to UI
      console.error('API Error:', error.response?.data || error)
      return Promise.reject(error)
    }
  )

  return api
}