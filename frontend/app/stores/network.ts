import { defineStore } from 'pinia'

export const useNetworkStore = defineStore('network', {
  state: () => ({
    current: '' as string
  }),
  actions: {
    setNetwork(network: string) {
      this.current = network
    },
    clear() {
      this.current = ''
    }
  }
})