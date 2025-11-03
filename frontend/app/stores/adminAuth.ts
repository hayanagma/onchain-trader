import { defineStore } from 'pinia'
import { useApi } from '~/composables/useApi'

export const useAdminAuthStore = defineStore('adminAuth', () => {
  const token = ref<string | null>(null)
  const isLoggedIn = computed(() => !!token.value)

  function setToken(jwt: string | null) {
    token.value = jwt
  }

  async function login(username: string, password: string) {
    const api = useApi()
    const res = await api.post('/auth/admin/login', { username, password })
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

  return { token, isLoggedIn, setToken, login, refresh, logout }
})