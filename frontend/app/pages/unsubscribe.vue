<template>
  <section class="flex h-screen items-center justify-center bg-gray-100 dark:bg-gray-900">
    <div class="text-center px-6 py-10 bg-white dark:bg-gray-800 rounded-lg shadow max-w-md">
      <h1 class="text-2xl font-bold text-gray-900 dark:text-white mb-4">Newsletter Unsubscribe</h1>
      <p class="text-gray-700 dark:text-gray-300 text-lg">{{ message }}</p>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useApi } from '~/composables/useApi'

const route = useRoute()
const message = ref('Processing...')
const api = useApi()

onMounted(async () => {
  const token = route.query.token
  if (!token) {
    message.value = 'Invalid link.'
    return
  }

  try {
    await api.post(
      '/public/newsletter/unsubscribe',
      {},
      { params: { token }, headers: { 'Content-Type': 'application/json' } }
    )
    message.value = 'You have been unsubscribed.'
  } catch {
    message.value = 'Unsubscribe failed or already processed.'
  }
})
</script>