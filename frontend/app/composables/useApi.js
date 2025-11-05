import axios from 'axios'
import { useUserAuthStore } from '~/stores/userAuth'
import { useAdminAuthStore } from '~/stores/adminAuth'
import { navigateTo } from '#app'

export const useApi = () => {
  const config = useRuntimeConfig()
  const userAuth = useUserAuthStore()
  const adminAuth = useAdminAuthStore()

  const api = axios.create({
    baseURL: config.public.apiBase,
    withCredentials: true, // required for refresh cookies
  })

  // Attach Authorization tokens
  api.interceptors.request.use((req) => {
    if (!req.url) return req

    // Admin routes
    if ((req.url.startsWith('/auth/admin') || req.url.startsWith('/admin')) && adminAuth.token) {
      req.headers['Authorization'] = `Bearer ${adminAuth.token}`
    }

    // Trader routes
    else if ((req.url.startsWith('/auth/trader') || req.url.startsWith('/trader')) && userAuth.token) {
      req.headers['Authorization'] = `Bearer ${userAuth.token}`
    }

    return req
  })

  // Auto-refresh on 401
  api.interceptors.response.use(
    (res) => res,
    async (error) => {
      const originalRequest = error.config
      if (error.response?.status === 401 && !originalRequest._retry) {
        originalRequest._retry = true
        try {
          if (originalRequest.url.startsWith('/auth/admin') || originalRequest.url.startsWith('/admin')) {
            await adminAuth.refresh()
            if (adminAuth.token) {
              originalRequest.headers['Authorization'] = `Bearer ${adminAuth.token}`
            }
          } else if (originalRequest.url.startsWith('/auth/trader') || originalRequest.url.startsWith('/trader')) {
            await userAuth.refresh()
            if (userAuth.token) {
              originalRequest.headers['Authorization'] = `Bearer ${userAuth.token}`
            }
          }
          return api(originalRequest)
        } catch {
          // Refresh failed → logout
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