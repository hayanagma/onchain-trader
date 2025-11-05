import { defineStore } from 'pinia'
import { useApi } from '~/composables/useApi'

export const useUserAuthStore = defineStore('userAuth', () => {
  const token = ref<string | null>(null)
  const isLoggedIn = computed(() => !!token.value)

  function setToken(jwt: string | null) {
    token.value = jwt
  }

  async function challenge(walletAddress: string, network: string) {
    const api = useApi()
    const res = await api.post('/auth/trader/challenge', { walletAddress, network })
    return res.data
  }

  async function login(walletAddress: string, network: string, nonce: string, signature: string) {
    const api = useApi()
    const res = await api.post('/auth/trader/login', { walletAddress, network, nonce, signature })
    setToken(res.data.accessToken)
  }

  async function refresh() {
    const api = useApi()
    const res = await api.post('/auth/trader/refresh')
    setToken(res.data.accessToken)
  }

  async function logout() {
    token.value = null
    const api = useApi()
    await api.post('/auth/trader/logout')
  }

  return { token, isLoggedIn, setToken, challenge, login, refresh, logout }
})