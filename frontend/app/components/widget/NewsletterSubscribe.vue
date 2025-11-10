<script setup lang="ts">
import { ref } from 'vue'
import { useApi } from '~/composables/useApi'

const api = useApi()
const email = ref('')
const loading = ref(false)
const message = ref('')

const subscribe = async () => {
  loading.value = true
  message.value = 'Subscribing...'

  try {
    await api.post('/public/newsletter/subscribe', { email: email.value })
    message.value = 'Subscribed successfully.'
    email.value = ''
  } catch {
    message.value = 'Failed to subscribe. Please try again.'
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <section class="flex items-center justify-center py-16 bg-gray-100 dark:bg-gray-900">
    <div
      class="w-full max-w-md px-6 py-8 bg-white/10 dark:bg-gray-800/50 border border-gray-200 dark:border-gray-700 rounded-2xl shadow-lg text-center">
      <h2 class="text-xl font-semibold text-gray-900 dark:text-white">
        Sign up for our Newsletter
      </h2>
      <p class="mt-2 text-gray-700 dark:text-gray-300 text-sm">
        Stay up to date with our latest updates, features, and announcements.
      </p>

      <form @submit.prevent="subscribe" class="mt-6 flex flex-col sm:flex-row items-center justify-center gap-3">
        <input v-model="email" type="email" required placeholder="Enter your email"
          class="h-10 w-full sm:w-2/3 rounded-md border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100 px-3 text-sm focus:outline-none focus:ring-2 focus:ring-indigo-600" />

        <button type="submit"
          class="h-10 w-full sm:w-auto rounded-md border border-indigo-600 bg-indigo-600 px-6 text-xs font-medium text-white hover:bg-transparent hover:text-indigo-600 dark:hover:text-indigo-400 focus:ring-2 focus:ring-indigo-400 transition disabled:opacity-50"
          :disabled="loading">
          {{ loading ? 'Subscribing...' : 'Sign Up' }}
        </button>
      </form>

      <p v-if="message" class="mt-4 text-xs" :class="message.includes('success') ? 'text-green-500' : 'text-red-500'">
        {{ message }}
      </p>
    </div>
  </section>
</template>