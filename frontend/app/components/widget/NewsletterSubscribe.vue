<template>
  <section class="bg-gray-100 dark:bg-gray-900">
    <div class="mx-auto max-w-7xl px-4 py-8 sm:px-6 lg:px-8">
      <div class="max-w-prose mx-auto text-center">
        <h2 class="text-2xl font-semibold text-gray-900 dark:text-white sm:text-3xl">
          Sign up for our Newsletter
        </h2>
        <p class="mt-4 text-gray-700 dark:text-gray-300">
          Stay up to date with our latest updates, features, and announcements.
        </p>
      </div>

      <form @submit.prevent="subscribe"
        class="mt-6 flex max-w-xl mx-auto flex-col gap-4 sm:flex-row sm:items-center justify-center">
        <label for="Email" class="flex-1">
          <span class="sr-only">Email</span>
          <input v-model="email" type="email" id="Email" required placeholder="Enter your email"
            class="h-12 w-full rounded border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100 px-3 shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-600" />
        </label>

        <button type="submit"
          class="h-12 rounded-sm border border-indigo-600 bg-indigo-600 px-12 py-3 text-sm font-medium text-white hover:bg-transparent hover:text-indigo-600 dark:hover:text-indigo-400 focus:ring-2 focus:outline-hidden transition disabled:opacity-50"
          :disabled="loading">
          {{ loading ? 'Subscribing...' : 'Sign Up' }}
        </button>
      </form>

      <p v-if="success" class="text-green-500 text-center text-sm mt-4">
        Subscribed successfully.
      </p>
      <p v-if="error" class="text-red-500 text-center text-sm mt-4">
        Failed to subscribe. Try again later.
      </p>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useApi } from '~/composables/useApi'

const api = useApi()

const email = ref('')
const loading = ref(false)
const success = ref(false)
const error = ref(false)

const subscribe = async () => {
  loading.value = true
  success.value = false
  error.value = false

  try {
    await api.post('/public/newsletter/subscribe', { email: email.value })
    success.value = true
    email.value = ''
  } catch {
    error.value = true
  } finally {
    loading.value = false
  }
}
</script>