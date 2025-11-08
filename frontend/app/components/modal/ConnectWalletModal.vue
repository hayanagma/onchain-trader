<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useApi } from '~/composables/useApi'
import { useUserAuthStore } from '~/stores/userAuth'

const walletAddress = ref('')
const network = ref('')
const userAuth = useUserAuthStore()
const router = useRouter()
const api = useApi()

const loading = ref(false)
const message = ref('')

const handleConnect = async () => {
  loading.value = true
  message.value = 'Requesting challenge...'

  try {
    const { nonce } = await userAuth.challenge(walletAddress.value, network.value)
    const signature = '0xDUMMY_SIGNATURE'

    message.value = 'Verifying wallet...'
    await userAuth.login(walletAddress.value, network.value, nonce, signature)

    message.value = 'Wallet connected successfully.'
    setTimeout(async () => {
      message.value = ''
      await router.push('/trader/dashboard')
    }, 1000)
  } catch (err: any) {
    message.value = 'Failed to connect wallet. Make sure the network is correct and try again.'
    try {
      await api.get('/trader/subscription')
    } catch (syncErr) {
      console.error('Sync failed after error', syncErr)
    }
  } finally {
    loading.value = false
  }
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

        <div>
          <label class="block text-sm font-medium mb-2 text-gray-800 dark:text-gray-200">
            Network
          </label>
          <select v-model="network" required
            class="w-full rounded-md bg-white/70 dark:bg-gray-800/50 border border-gray-300 dark:border-gray-700 px-4 py-2 text-gray-900 dark:text-gray-100 focus:ring-2 focus:ring-teal-500 focus:outline-none">
            <option value="">Select network</option>
            <option value="BITCOIN">Bitcoin</option>
            <option value="TRON">TRON</option>
            <option value="SOLANA">Solana</option>
            <option value="ETHEREUM">Ethereum</option>
          </select>
        </div>

        <div class="flex justify-end gap-3 pt-2">
          <button type="button" @click="$emit('close')"
            class="px-4 py-2 bg-gray-300/70 dark:bg-gray-700/60 text-gray-900 dark:text-gray-100 rounded-md hover:bg-gray-400/70 dark:hover:bg-gray-600 transition">
            Cancel
          </button>
          <button type="submit" :disabled="loading"
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