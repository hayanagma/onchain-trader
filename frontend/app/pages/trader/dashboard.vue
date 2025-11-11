<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useTraderStore } from '~/stores/trader'
import { useApi } from '~/composables/useApi'
import NewsletterSubscribe from '~/components/widget/NewsletterSubscribe.vue'

definePageMeta({ layout: 'trader' })

interface Update {
  id: number
  title: string
  description: string
  status: string
  plannedReleaseDate: string
  releasedAt: string | null
}

const traderStore = useTraderStore()
const api = useApi()
const updates = ref<Update[]>([])
const loading = ref(false)
const error = ref('')

onMounted(async () => {
  try {
    if (!traderStore.trader) await traderStore.fetchTrader()
    loading.value = true
    const res = await api.get('/public/updates')
    updates.value = Array.isArray(res.data)
      ? res.data.sort((a, b) => {
        if (a.status === b.status) return 0
        return a.status === 'PLANNED' ? -1 : 1
      })
      : []
  } catch {
    error.value = 'Failed to fetch updates.'
  } finally {
    loading.value = false
  }
})

const formatDate = (date: string | null) => {
  if (!date) return '—'
  const d = new Date(date)
  return isNaN(d.getTime()) ? '—' : d.toLocaleDateString()
}
</script>

<template>
  <main class="flex justify-center p-8 bg-gray-950 text-gray-100 min-h-screen overflow-y-auto">
    <div class="w-full max-w-3xl space-y-8">
      <!-- Header -->
      <section class="bg-gray-900 border border-gray-800 rounded-2xl p-8 shadow text-center">
        <h1 class="text-2xl font-bold mb-2">Dashboard</h1>
        <p>Welcome to your dashboard.</p>
      </section>

      <!-- Newsletter -->
      <section>
        <NewsletterSubscribe />
      </section>

      <!-- Updates -->
      <section class="bg-gray-900 border border-gray-800 rounded-2xl p-6 shadow">
        <h2 class="text-xl font-semibold mb-4 text-center">Latest Updates</h2>
        <div v-if="loading" class="text-gray-400 text-sm text-center">Loading updates...</div>
        <div v-else-if="error" class="text-red-400 text-sm text-center">{{ error }}</div>

        <ul v-else-if="updates.length" class="space-y-4">
          <li v-for="u in updates" :key="u.id" class="bg-gray-800 border border-gray-700 rounded-lg p-4">
            <div class="flex justify-between items-start">
              <h3 class="text-lg font-semibold text-white">{{ u.title }}</h3>
              <span class="text-xs px-2 py-1 rounded-full" :class="{
                'bg-yellow-500/20 text-yellow-400': u.status === 'PLANNED',
                'bg-green-500/20 text-green-400': u.status === 'RELEASED',
                'bg-gray-600/20 text-gray-400': !u.status
              }">
                {{ u.status }}
              </span>
            </div>
            <p class="text-sm text-gray-400 mt-2">{{ u.description }}</p>
            <p class="text-xs text-gray-500 mt-3">
              {{ u.releasedAt ? 'Released:' : 'Planned release:' }}
              {{ formatDate(u.releasedAt || u.plannedReleaseDate) }}
            </p>
          </li>
        </ul>

        <p v-else class="text-gray-500 text-sm text-center">No updates available.</p>
      </section>
    </div>
  </main>
</template>
