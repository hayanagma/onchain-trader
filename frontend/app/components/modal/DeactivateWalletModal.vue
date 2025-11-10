<script setup lang="ts">
import { ref } from 'vue'
import { useApi } from '~/composables/useApi'
import { useTraderStore } from '~/stores/trader'

const props = defineProps<{ walletId: number | null }>()
const emit = defineEmits(['close', 'updated'])

const api = useApi()
const traderStore = useTraderStore()
const loading = ref(false)
const error = ref('')

const confirmDeactivate = async () => {
    if (!props.walletId) return
    loading.value = true
    error.value = ''
    try {
        await api.post(`/trader/wallet/remove?walletId=${props.walletId}`)
        await traderStore.fetchTrader()
        emit('updated')
        emit('close')
    } catch {
        error.value = 'Failed to deactivate wallet.'
    } finally {
        loading.value = false
    }
}
</script>

<template>
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
        <div class="bg-gray-900 border border-gray-700 rounded-2xl p-6 w-full max-w-sm">
            <h3 class="text-lg font-semibold text-white text-center mb-4">Deactivate Wallet</h3>
            <p class="text-sm text-gray-400 text-center mb-6">
                Are you sure you want to deactivate this wallet?
            </p>
            <div class="flex justify-center gap-3">
                <button @click="$emit('close')"
                    class="px-4 py-2 rounded bg-gray-700 text-gray-200 hover:bg-gray-600 transition">
                    Cancel
                </button>
                <button @click="confirmDeactivate" :disabled="loading"
                    class="px-4 py-2 rounded bg-yellow-600 text-white hover:bg-yellow-700 transition disabled:opacity-50">
                    {{ loading ? 'Processing...' : 'Confirm' }}
                </button>
            </div>
            <p v-if="error" class="text-sm text-red-400 text-center mt-4">{{ error }}</p>
        </div>
    </div>
</template>
