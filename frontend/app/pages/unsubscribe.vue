<template>
  <div class="unsubscribe">
    <h2>{{ message }}</h2>
  </div>
</template>

<script setup>
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
      {
        params: { token },
        headers: { 'Content-Type': 'application/json' }
      }
    )
    message.value = 'You have been unsubscribed.'
  } catch {
    message.value = 'Unsubscribe failed or already processed.'
  }
})
</script>