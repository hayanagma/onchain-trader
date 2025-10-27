import axios from 'axios'
import { useUserAuthStore } from '~/stores/userAuth'
import { navigateTo } from '#app'

export const useApi = () => {
  const config = useRuntimeConfig()
  const userAuth = useUserAuthStore()

  const api = axios.create({
    baseURL: config.public.apiBase,
    withCredentials: true, // Needed for refresh cookies
  })

  // Attach Authorization token for player
  api.interceptors.request.use((req) => {
    if (!req.url) return req

    if (req.url.startsWith('/auth/player') && userAuth.token) {
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
          if (originalRequest.url.startsWith('/auth/player')) {
            await userAuth.refresh()
            if (userAuth.token) {
              originalRequest.headers['Authorization'] = `Bearer ${userAuth.token}`
            }
          }
          return api(originalRequest)
        } catch {
          // Refresh failed → force logout
          await userAuth.logout()
          return navigateTo('/')
        }
      }
      return Promise.reject(error)
    }
  )

  return api
}