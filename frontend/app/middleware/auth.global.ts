import { useAdminAuthStore } from '~/stores/adminAuth'
import { useUserAuthStore } from '~/stores/userAuth'
import { useApi } from '~/composables/useApi'

export default defineNuxtRouteMiddleware(async (to) => {
  const adminAuth = useAdminAuthStore()
  const userAuth = useUserAuthStore()
  const api = useApi()

  const publicPages = ['/', '/login', '/admin/login']
  if (publicPages.includes(to.path)) return

  try {
    // Admin routes
    if (to.path.startsWith('/admin')) {
      if (!adminAuth.isLoggedIn) {
        try {
          await adminAuth.refresh()
        } catch (err) {
          console.error('Admin refresh failed', err)
          try {
            await api.get('/trader/subscription') // backend sync attempt
          } catch (syncErr) {
            console.error('Backend sync failed (admin)', syncErr)
          }
          return navigateTo('/admin')
        }
      }
      if (!adminAuth.isLoggedIn) return navigateTo('/admin')
    }

    // Trader routes
    if (to.path.startsWith('/trader')) {
      if (!userAuth.isLoggedIn) {
        try {
          await userAuth.refresh()
        } catch (err) {
          console.error('Trader refresh failed', err)
          try {
            await api.get('/trader/subscription') // backend sync attempt
          } catch (syncErr) {
            console.error('Backend sync failed (trader)', syncErr)
          }
          return navigateTo('/')
        }
      }
      if (!userAuth.isLoggedIn) return navigateTo('/')
    }
  } catch (err) {
    console.error('Middleware error', err)
    try {
      await api.get('/trader/subscription') // final sync fallback
    } catch (syncErr) {
      console.error('Middleware backend sync failed', syncErr)
    }
    return navigateTo('/')
  }
})