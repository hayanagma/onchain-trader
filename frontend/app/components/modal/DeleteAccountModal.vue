<script setup lang="ts">
import { ref } from 'vue'
import { useApi } from '~/composables/useApi'
import { useTraderStore } from '~/stores/trader'

const emit = defineEmits(['close', 'deleted'])
const api = useApi()
const traderStore = useTraderStore()
const confirmation = ref('')
const loading = ref(false)
const error = ref('')

const confirmDelete = async () => {
    if (confirmation.value.trim() !== 'I agree') {
        error.value = 'You must type "I agree" to confirm.'
        return
    }
    loading.value = true
    error.value = ''
    try {
        await api.post('/trader/account/delete', { confirmation: confirmation.value })

        // clear local session and cookie data
        traderStore.$reset()
        localStorage.clear()
        sessionStorage.clear()
        document.cookie.split(';').forEach(c => {
            document.cookie = c
                .replace(/^ +/, '')
                .replace(/=.*/, '=;expires=' + new Date(0).toUTCString() + ';path=/')
        })

        // force Nuxt to fully reload after redirect
        emit('deleted')
        emit('close')
        window.location.href = '/'
    } catch {
        error.value = 'Failed to delete account.'
    } finally {
        loading.value = false
    }
}
</script>

<template>
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
        <div class="bg-gray-900 border border-gray-700 rounded-2xl p-6 w-full max-w-sm">
            <h3 class="text-lg font-semibold text-white text-center mb-4">Delete Account</h3>
            <p class="text-sm text-gray-400 text-center mb-6">
                This action is <span class="text-red-400 font-semibold">irreversible</span>.
                All trades and data linked to your wallet addresses will be deleted forever.
            </p>
            <input v-model="confirmation" type="text" placeholder='Type "I agree" to confirm'
                class="w-full bg-gray-800 border border-gray-700 rounded px-3 py-2 text-sm text-gray-200 mb-4 focus:outline-none focus:border-red-500" />
            <div class="flex justify-center gap-3">
                <button @click="$emit('close')"
                    class="px-4 py-2 rounded bg-gray-700 text-gray-200 hover:bg-gray-600 transition">
                    Cancel
                </button>
                <button @click="confirmDelete" :disabled="loading"
                    class="px-4 py-2 rounded bg-red-600 text-white hover:bg-red-700 transition disabled:opacity-50">
                    {{ loading ? 'Processing...' : 'Delete' }}
                </button>
            </div>
            <p v-if="error" class="text-sm text-red-400 text-center mt-4">{{ error }}</p>
        </div>
    </div>
</template>