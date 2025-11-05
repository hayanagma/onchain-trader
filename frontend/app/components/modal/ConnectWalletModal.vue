<template>
  <div class="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
    <div class="bg-white dark:bg-gray-800 rounded-lg p-6 w-full max-w-sm">
      <h2 class="text-lg font-bold mb-4 text-gray-900 dark:text-white">Connect Wallet</h2>

      <form @submit.prevent="connect">
        <label class="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">Wallet Address</label>
        <input
          v-model="walletAddress"
          type="text"
          required
          class="w-full border rounded-md p-2 mb-3 dark:bg-gray-700 dark:text-white"
        />

        <label class="block text-sm font-medium text-gray-700 dark:text-gray-200 mb-1">Network</label>
        <select
          v-model="network"
          class="w-full border rounded-md p-2 mb-4 dark:bg-gray-700 dark:text-white"
        >
        <option value="BITCOIN">Bitcoin</option>
          <option value="TRON">TRON</option>
          <option value="SOLANA">Solana</option>
          <option value="ETHEREUM">Ethereum</option>
        </select>

        <div class="flex justify-end gap-2">
          <button
            type="button"
            @click="$emit('close')"
            class="px-4 py-2 bg-gray-200 rounded-md text-gray-800"
          >
            Cancel
          </button>
          <button
            type="submit"
            @click="connectAndLogin"
            class="px-4 py-2 bg-teal-600 text-white rounded-md"
          >
            Connect
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserAuthStore } from '~/stores/userAuth'

const userAuth = useUserAuthStore()
const router = useRouter()

const walletAddress = ref('')
const network = ref('BITCOIN')

const connectAndLogin = async () => {
  try {
    const { nonce } = await userAuth.challenge(walletAddress.value, network.value)
    const signature = '0xDUMMY_SIGNATURE' // replace with real wallet signature
    await userAuth.login(walletAddress.value, network.value, nonce, signature)
    await router.push('/trader/dashboard')
  } catch (err) {
    console.error(err)
  }
}
</script>