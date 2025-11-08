<template>
    <div class="fixed inset-0 bg-black/60 backdrop-blur-md flex items-center justify-center z-50">
        <div
            class="backdrop-blur-xl bg-white/30 dark:bg-gray-900/30 border border-white/30 dark:border-gray-700/40 rounded-2xl shadow-2xl p-8 w-full max-w-sm relative">
            <h2 class="text-xl font-bold mb-6 text-gray-900 dark:text-white text-center">
                Add New Wallet
            </h2>

            <form @submit.prevent="handleAddWallet" class="space-y-4">
                <div>
                    <label class="block text-sm font-medium mb-2 text-gray-800 dark:text-gray-200">
                        Wallet Address
                    </label>
                    <input v-model="walletAddress" type="text" required
                        class="w-full rounded-md bg-white/70 dark:bg-gray-800/50 border border-gray-300 dark:border-gray-700 px-4 py-2 text-gray-900 dark:text-gray-100 placeholder-gray-400 dark:placeholder-gray-500 focus:ring-2 focus:ring-teal-500 focus:outline-none"
                        placeholder="Enter wallet address" />
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
                    <button type="submit"
                        class="px-5 py-2 bg-teal-600/80 hover:bg-teal-700 text-white rounded-md shadow-lg hover:shadow-teal-500/30 transition focus:ring-2 focus:ring-teal-400 focus:outline-none">
                        Add Wallet
                    </button>
                </div>
            </form>

            <p v-if="message" class="text-sm text-center mt-4 text-gray-300">{{ message }}</p>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useApi } from '~/composables/useApi'

const emit = defineEmits(['close'])
const api = useApi()
const walletAddress = ref('')
const network = ref('')
const message = ref('')

const handleAddWallet = async () => {
    try {
        message.value = 'Requesting challenge...'
        const { data: challenge } = await api.post('/trader/wallet/challenge', {
            address: walletAddress.value,
            network: network.value
        })

        // Simulated signing; replace later with actual wallet signature
        const signature = '0xDUMMY_SIGNATURE'

        message.value = 'Verifying wallet...'
        await api.post('/trader/wallet/verify', {
            address: walletAddress.value,
            network: network.value,
            nonce: challenge.nonce,
            signature
        })

        message.value = 'Wallet successfully added.'
        setTimeout(() => {
            message.value = ''
            walletAddress.value = ''
            network.value = ''
            emit('close')
        }, 1200)
    } catch (err) {
        console.error(err)
        message.value = 'Failed to add wallet.'
    }
}
</script>