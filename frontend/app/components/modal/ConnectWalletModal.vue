<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useApi } from '~/composables/useApi'
import { useUserAuthStore } from '~/stores/userAuth'

const walletAddress = ref('')
const selectedNetwork = ref('')
const showNetworks = ref(false)

const networks = ['BITCOIN', 'TRON', 'SOLANA', 'ETHEREUM']

const userAuth = useUserAuthStore()
const router = useRouter()
const api = useApi()

const loading = ref(false)
const message = ref('')

const handleConnect = async () => {
  loading.value = true
  message.value = 'Requesting challenge...'

  try {
    const { nonce } = await userAuth.challenge(walletAddress.value, selectedNetwork.value)
    const signature = '0xDUMMY_SIGNATURE'

    message.value = 'Verifying wallet...'
    await userAuth.login(walletAddress.value, selectedNetwork.value, nonce, signature)

    message.value = 'Wallet connected successfully.'
    setTimeout(async () => {
      message.value = ''
      await router.push('/trader/dashboard')
    }, 1000)
  } catch {
    message.value = 'Failed to connect wallet. Make sure the network is correct and try again.'
    try {
      await api.get('/trader/subscription')
    } catch { }
  } finally {
    loading.value = false
  }
}

const selectNetwork = (n: string) => {
  selectedNetwork.value = n
  showNetworks.value = false
}
</script>

<template>
  <div class="fixed inset-0 bg-black/60 backdrop-blur-md flex items-center justify-center z-50">
    <div
      class="backdrop-blur-xl bg-white/30 dark:bg-gray-900/30 border border-white/30 dark:border-gray-700/40 rounded-2xl shadow-2xl p-8 w-full max-w-sm relative">
      <h2 class="text-xl font-bold mb-6 text-gray-900 dark:text-white text-center">
        Connect Your Wallet
      </h2>

      <form @submit.prevent="handleConnect" class="space-y-4">
        <div>
          <label class="block text-sm font-medium mb-2 text-gray-800 dark:text-gray-200">
            Wallet Address
          </label>
          <input v-model="walletAddress" type="text" required
            class="w-full rounded-md bg-white/70 dark:bg-gray-800/50 border border-gray-300 dark:border-gray-700 px-4 py-2 text-gray-900 dark:text-gray-100 placeholder-gray-400 dark:placeholder-gray-500 focus:ring-2 focus:ring-teal-500 focus:outline-none"
            placeholder="Enter your wallet address" />
        </div>

        <div class="relative">
          <label class="block text-sm font-medium mb-2 text-gray-800 dark:text-gray-200">
            Network
          </label>

          <button type="button" @click="showNetworks = !showNetworks"
            class="w-full flex items-center justify-between rounded-md bg-white/70 dark:bg-gray-800/50 border border-gray-300 dark:border-gray-700 px-4 py-2 text-gray-900 dark:text-gray-100 focus:ring-2 focus:ring-teal-500 focus:outline-none">
            <div class="flex items-center gap-3">
              <img v-if="selectedNetwork" :src="`/icons/${selectedNetwork.toLowerCase()}.svg`" class="w-6 h-6" />
              <span>{{ selectedNetwork || 'Select network' }}</span>
            </div>
            <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 opacity-70" viewBox="0 0 20 20" fill="currentColor">
              <path fill-rule="evenodd"
                d="M5.23 7.21a.75.75 0 011.06.02L10 10.94l3.71-3.71a.75.75 0 111.06 1.06l-4.24 4.25a.75.75 0 01-1.06 0L5.25 8.27a.75.75 0 01-.02-1.06z"
                clip-rule="evenodd" />
            </svg>
          </button>

          <div v-if="showNetworks"
            class="absolute left-0 right-0 mt-2 bg-gray-900 text-white border border-gray-700 rounded-md shadow-lg z-50">
            <button v-for="n in networks" :key="n" type="button" @click="selectNetwork(n)"
              class="flex items-center gap-3 w-full text-left px-4 py-2 hover:bg-gray-800 transition">
              <img :src="`/icons/${n.toLowerCase()}.svg`" class="w-7 h-7" />
              <span>{{ n }}</span>
            </button>
          </div>
        </div>

        <div class="flex justify-end gap-3 pt-2">
          <button type="button" @click="$emit('close')"
            class="px-4 py-2 bg-gray-300/70 dark:bg-gray-700/60 text-gray-900 dark:text-gray-100 rounded-md hover:bg-gray-400/70 dark:hover:bg-gray-600 transition">
            Cancel
          </button>
          <button type="submit" :disabled="loading || !walletAddress || !selectedNetwork"
            class="px-5 py-2 bg-teal-600/80 hover:bg-teal-700 text-white rounded-md shadow-lg hover:shadow-teal-500/30 transition focus:ring-2 focus:ring-teal-400 focus:outline-none disabled:opacity-50">
            {{ loading ? 'Connecting...' : 'Connect' }}
          </button>
        </div>

        <p v-if="message" class="text-sm text-center mt-3 text-gray-300">
          {{ message }}
        </p>
      </form>
    </div>
  </div>
</template>