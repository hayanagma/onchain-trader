//import { useAdminAuthStore } from '~/stores/adminAuth'
import { useUserAuthStore } from '~/stores/userAuth'

export default defineNuxtRouteMiddleware(async (to) => {
  //const adminAuth = useAdminAuthStore()
  const userAuth = useUserAuthStore()

  // Public pages
  const publicPages = [
    '/',
    '/login',
  ]
  if (publicPages.includes(to.path)) return
/* 
  // Admin protected routes
  if (to.path.startsWith('/admin')) {
    if (!adminAuth.isLoggedIn) {
      try {
        await adminAuth.refresh()
      } catch {
        return navigateTo('/admin')
      }
    }
    if (!adminAuth.isLoggedIn) {
      return navigateTo('/admin')
    }
  }
 */
  // Player protected routes
  if (to.path.startsWith('/dashboard') || to.path.startsWith('/player')) {
    if (!userAuth.isLoggedIn) {
      try {
        await userAuth.refresh()
      } catch {
        return navigateTo('/')
      }
    }
    if (!userAuth.isLoggedIn) {
      return navigateTo('/')
    }
  }
})