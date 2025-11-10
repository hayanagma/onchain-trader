<script setup lang="ts">
import { ref } from 'vue'
import { useApi } from '~/composables/useApi'
import { useNetworkStore } from '~/stores/network'

const emit = defineEmits(['close', 'success'])
const api = useApi()
const networkStore = useNetworkStore()

const network = ref(networkStore.current)
const contractAddress = ref('')

const submit = async () => {
    if (!network.value || !contractAddress.value) return
    try {
        await api.post('/trader/currency', {
            network: network.value,
            contractAddress: contractAddress.value,
        })
        emit('success')
        emit('close')
    } catch (e) {
        console.error('Failed to add currency:', e)
    }
}
</script>

<template>
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
        <div class="bg-gray-900 border border-gray-800 rounded-2xl w-full max-w-md p-6 space-y-6 shadow-lg">
            <h2 class="text-lg font-semibold text-white">Add Currency</h2>

            <div class="space-y-4">
                <div>
                    <label class="block text-sm text-gray-400 mb-1">Contract Address</label>
                    <input v-model="contractAddress" type="text" placeholder="Enter contract address"
                        class="w-full bg-gray-800 border border-gray-700 rounded px-3 py-2 text-gray-200" />
                </div>
                <div>
                    <label class="block text-sm text-gray-400 mb-1">Network</label>
                    <input type="text" v-model="network" readonly
                        class="w-full bg-gray-800 border border-gray-700 rounded px-3 py-2 text-gray-300" />
                </div>
            </div>

            <div class="flex justify-end gap-2">
                <button @click="$emit('close')" class="px-4 py-2 text-sm text-gray-300 hover:text-white">Cancel</button>
                <button @click="submit" class="px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white text-sm rounded">
                    Add
                </button>
            </div>
        </div>
    </div>
</template>