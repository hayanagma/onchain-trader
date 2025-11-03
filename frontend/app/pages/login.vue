<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserAuthStore } from '~/stores/userAuth'

const userAuth = useUserAuthStore()
const router = useRouter()

const walletAddress = ref('')
const network = ref('TRON')

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

<template>
  <div class="p-4">
    <input
      v-model="walletAddress"
      placeholder="Wallet address"
      class="border p-2"
    />
    <input
      v-model="network"
      placeholder="Network"
      class="border p-2 ml-2"
    />
    <button
      @click="connectAndLogin"
      class="ml-2 border p-2"
    >
      Login with Wallet
    </button>
  </div>
</template>