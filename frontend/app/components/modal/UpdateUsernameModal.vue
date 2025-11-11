<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useApi } from '~/composables/useApi'

const emit = defineEmits(['close', 'updated'])
const api = useApi()

const currentUsername = ref('')
const newUsername = ref('')
const secondsUntilChangeAllowed = ref(0)
const loading = ref(false)
const message = ref('')
const timerInterval = ref<number | null>(null)

const formatTime = (seconds: number) => {
    const h = Math.floor(seconds / 3600)
    const m = Math.floor((seconds % 3600) / 60)
    const s = seconds % 60
    return `${h > 0 ? `${h}h ` : ''}${m > 0 ? `${m}m ` : ''}${s}s`
}

const fetchTraderProfile = async () => {
    const { data } = await api.get('/trader/profile')
    currentUsername.value = data.username
    secondsUntilChangeAllowed.value = data.usernameChangeStatus.secondsUntilChangeAllowed

    if (secondsUntilChangeAllowed.value > 0) startTimer()
}

const startTimer = () => {
    if (timerInterval.value) clearInterval(timerInterval.value)
    timerInterval.value = window.setInterval(() => {
        if (secondsUntilChangeAllowed.value > 0) {
            secondsUntilChangeAllowed.value--
        } else {
            clearInterval(timerInterval.value!)
        }
    }, 1000)
}

const randomizeUsername = async () => {
    loading.value = true
    try {
        const { data } = await api.get('/trader/profile/random-username')
        newUsername.value = data.username
    } finally {
        loading.value = false
    }
}

const submitUsername = async () => {
    if (!newUsername.value) return
    loading.value = true
    try {
        await api.put('/trader/profile/username', { username: newUsername.value })
        const { data } = await api.get('/trader/profile/username')
        currentUsername.value = data.username
        emit('updated')
        emit('close')
    } finally {
        loading.value = false
    }
}

onMounted(fetchTraderProfile)
</script>

<template>
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
        <div class="bg-gray-900 border border-gray-800 rounded-2xl w-full max-w-md p-6 space-y-6 shadow-lg text-center">
            <h2 class="text-lg font-semibold text-white">Update Username</h2>

            <div class="space-y-3">
                <p class="text-sm text-gray-400">Current: <span class="text-white">{{ currentUsername }}</span></p>

                <div v-if="secondsUntilChangeAllowed > 0" class="text-xs text-red-400">
                    You can change your username again in {{ formatTime(secondsUntilChangeAllowed) }}.
                </div>

                <div class="flex items-center justify-center gap-2 mt-2">
                    <input type="text" v-model="newUsername" readonly placeholder="Randomized username"
                        class="w-2/3 bg-gray-800 border border-gray-700 rounded px-3 py-2 text-gray-300 text-sm" />
                    <button @click="randomizeUsername" :disabled="loading || secondsUntilChangeAllowed > 0"
                        class="px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded text-xs disabled:opacity-50">
                        Randomize
                    </button>
                </div>
            </div>

            <div class="flex justify-end gap-2 pt-4">
                <button @click="$emit('close')" class="px-4 py-2 text-sm text-gray-300 hover:text-white">
                    Cancel
                </button>
                <button @click="submitUsername" :disabled="!newUsername || loading || secondsUntilChangeAllowed > 0"
                    class="px-4 py-2 bg-teal-600 hover:bg-teal-700 text-white text-sm rounded disabled:opacity-50">
                    Save
                </button>
            </div>

            <p v-if="message" class="text-xs text-gray-400">{{ message }}</p>
        </div>
    </div>
</template>
