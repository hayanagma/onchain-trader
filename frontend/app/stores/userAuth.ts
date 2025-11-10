import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { useApi } from '~/composables/useApi'
import { useNetworkStore } from '~/stores/network'

export const useUserAuthStore = defineStore('userAuth', () => {
  const token = ref<string | null>(null)
  const isLoggedIn = computed(() => !!token.value)
  const networkStore = useNetworkStore()

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
    networkStore.setNetwork(network)
  }

  async function refresh() {
    const api = useApi()
    const res = await api.post('/auth/trader/refresh')
    setToken(res.data.accessToken)
  }

  async function logout() {
    const api = useApi()
    try {
      await api.post('/auth/trader/logout')
    } finally {
      token.value = null
      networkStore.clear()
    }
  }

  return { token, isLoggedIn, setToken, challenge, login, refresh, logout }
})