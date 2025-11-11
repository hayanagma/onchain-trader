import { defineStore } from 'pinia'
import { useApi } from '~/composables/useApi'

export const useTraderStore = defineStore('trader', {
  state: () => ({
    trader: null as any
  }),

  actions: {
    async fetchTrader() {
      const api = useApi()
      const { data } = await api.get('/trader/profile')
      this.trader = data
    }
  }
})
