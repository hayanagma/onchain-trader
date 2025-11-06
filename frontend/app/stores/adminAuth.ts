import { defineStore } from 'pinia'
import { useApi } from '~/composables/useApi'

export const useAdminAuthStore = defineStore('adminAuth', () => {
  const token = ref<string | null>(null)
  const isLoggedIn = computed(() => !!token.value)

  function setToken(jwt: string | null) {
    token.value = jwt
  }

  // Step 1: request code (email with 2FA code)
  async function requestCode(username: string, password: string) {
    const api = useApi()
    await api.post('/auth/admin/login', { username, password })
    // no token yet — only triggers email
  }

  // Step 2: verify code and receive JWT
  async function verifyCode(adminId: string, code: string) {
    const api = useApi()
    const res = await api.post('/auth/admin/verify', { adminId, code })
    setToken(res.data.accessToken)
  }

  async function refresh() {
    const api = useApi()
    const res = await api.post('/auth/admin/refresh')
    setToken(res.data.accessToken)
  }

  async function logout() {
    token.value = null
    const api = useApi()
    await api.post('/auth/admin/logout')
  }

  return {
    token,
    isLoggedIn,
    setToken,
    requestCode,
    verifyCode,
    refresh,
    logout
  }
})