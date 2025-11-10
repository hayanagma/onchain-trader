<script setup lang="ts">
import { ref, watch } from 'vue'
import { useApi } from '~/composables/useApi'
import { useNetworkStore } from '~/stores/network'

const api = useApi()
const networkStore = useNetworkStore()

const showNetworks = ref(false)
const networks = ['TRON', 'ETHEREUM', 'SOLANA', 'BITCOIN']
const currencies = ref<any[]>([])

const fetchCurrencies = async () => {
    try {
        const { data } = await api.get('/trader/currency')
        currencies.value = data
    } catch {
        currencies.value = []
    }
}

const selectNetwork = (network: string) => {
    networkStore.setNetwork(network)
    showNetworks.value = false
}

watch(
    () => networkStore.current,
    async () => {
        await fetchCurrencies()
    },
    { immediate: true }
)
</script>

<template>
    <div class="absolute top-4 right-6 z-50">
        <button @click="showNetworks = !showNetworks"
            class="flex items-center gap-3 bg-gray-900/70 hover:bg-gray-800 text-white text-sm px-4 py-2 rounded-md transition">
            <img :src="`/icons/${networkStore.current.toLowerCase()}.svg`" class="w-7 h-7" />
            <span class="text-sm font-medium">{{ networkStore.current }}</span>
            <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 opacity-70" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd"
                    d="M5.23 7.21a.75.75 0 011.06.02L10 10.94l3.71-3.71a.75.75 0 111.06 1.06l-4.24 4.25a.75.75 0 01-1.06 0L5.25 8.27a.75.75 0 01-.02-1.06z"
                    clip-rule="evenodd" />
            </svg>
        </button>

        <div v-if="showNetworks"
            class="absolute right-0 mt-2 bg-gray-900 text-white text-sm border border-gray-700 rounded-md shadow-lg w-40 z-50">
            <button v-for="n in networks" :key="n" @click="selectNetwork(n)"
                class="flex items-center gap-3 w-full text-left px-4 py-2 hover:bg-gray-800 transition">
                <img :src="`/icons/${n.toLowerCase()}.svg`" class="w-7 h-7" />
                <span>{{ n }}</span>
            </button>
        </div>
    </div>
</template>