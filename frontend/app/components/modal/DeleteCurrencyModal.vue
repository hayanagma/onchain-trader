<script setup lang="ts">
import { ref } from 'vue'
import { useApi } from '~/composables/useApi'
import { useTraderStore } from '~/stores/trader'

const props = defineProps<{ currencyId: number | null }>()
const emit = defineEmits(['close', 'updated'])

const api = useApi()
const traderStore = useTraderStore()
const loading = ref(false)
const error = ref('')

const confirmDelete = async () => {
    if (!props.currencyId) return
    loading.value = true
    error.value = ''
    try {
        await api.post(`/trader/currency/remove?currencyId=${props.currencyId}`)
        await traderStore.fetchTrader()
        emit('updated')
        emit('close')
    } catch {
        error.value = 'Failed to delete currency.'
    } finally {
        loading.value = false
    }
}
</script>

<template>
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
        <div class="bg-gray-900 border border-gray-700 rounded-2xl p-6 w-full max-w-sm">
            <h3 class="text-lg font-semibold text-white text-center mb-4">Delete Currency</h3>
            <p class="text-sm text-gray-400 text-center mb-6">
                Are you sure you want to remove this currency?
            </p>
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
