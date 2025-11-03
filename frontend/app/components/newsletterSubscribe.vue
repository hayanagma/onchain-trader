<template>
  <section class="max-w-lg mx-auto p-6">
    <h2 class="text-xl font-semibold mb-3">Subscribe to our Newsletter</h2>
    <form @submit.prevent="subscribe" class="space-y-3">
      <input
        v-model="email"
        type="email"
        required
        class="w-full border rounded px-3 py-2"
        placeholder="Enter your email"
      />
      <button
        type="submit"
        class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        :disabled="loading"
      >
        {{ loading ? 'Subscribing...' : 'Subscribe' }}
      </button>
      <p v-if="success" class="text-green-600 text-sm mt-2">
        Subscribed successfully.
      </p>
      <p v-if="error" class="text-red-600 text-sm mt-2">
        Failed to subscribe. Try again later.
      </p>
    </form>
  </section>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const email = ref('')
const loading = ref(false)
const success = ref(false)
const error = ref(false)

const subscribe = async () => {
  loading.value = true
  success.value = false
  error.value = false

  try {
    await $fetch('http://localhost:8080/api/public/newsletter/subscribe', {
      method: 'POST',
      body: { email: email.value }
    })
    success.value = true
    email.value = ''
  } catch {
    error.value = true
  } finally {
    loading.value = false
  }
}
</script>