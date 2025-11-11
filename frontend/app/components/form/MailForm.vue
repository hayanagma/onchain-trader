<template>
  <section class="bg-gray-100 dark:bg-gray-900 py-20 px-6 relative overflow-hidden">
    <!-- glowing gradient background effect -->
    <div
      class="absolute inset-0 bg-linear-to-br from-indigo-500/10 via-transparent to-pink-500/10 blur-3xl opacity-40">
    </div>

    <div class="relative max-w-4xl mx-auto text-center">
      <h2 class="text-3xl font-semibold mb-6 text-gray-900 dark:text-white">
        {{ title }}
      </h2>
      <p class="text-gray-700 dark:text-gray-300 mb-10 max-w-2xl mx-auto">
        Reach out to us with your message or question — we’ll get back to you shortly.
      </p>

      <div
        class="backdrop-blur-xl bg-white/40 dark:bg-gray-800/40 border border-white/30 dark:border-gray-700/40 rounded-2xl shadow-2xl max-w-lg mx-auto p-8 text-left">
        <form @submit.prevent="submitForm" class="space-y-6">
          <div>
            <label class="block text-sm mb-2 text-gray-800 dark:text-gray-200">Your Email</label>
            <input v-model="form.from" type="email" required
              class="w-full rounded-md bg-white/70 dark:bg-gray-900/40 border border-gray-300 dark:border-gray-700 px-4 py-2 text-gray-900 dark:text-gray-100 placeholder-gray-400 dark:placeholder-gray-500 focus:ring-2 focus:ring-indigo-500 focus:outline-none"
              placeholder="trader@example.com" />
          </div>

          <div>
            <label class="block text-sm mb-2 text-gray-800 dark:text-gray-200">Subject</label>
            <input v-model="form.subject" type="text" required
              class="w-full rounded-md bg-white/70 dark:bg-gray-900/40 border border-gray-300 dark:border-gray-700 px-4 py-2 text-gray-900 dark:text-gray-100 placeholder-gray-400 dark:placeholder-gray-500 focus:ring-2 focus:ring-indigo-500 focus:outline-none"
              placeholder="Subject" />
          </div>

          <div>
            <label class="block text-sm mb-2 text-gray-800 dark:text-gray-200">Message</label>
            <textarea v-model="form.message" required rows="4"
              class="w-full rounded-md bg-white/70 dark:bg-gray-900/40 border border-gray-300 dark:border-gray-700 px-4 py-2 text-gray-900 dark:text-gray-100 placeholder-gray-400 dark:placeholder-gray-500 focus:ring-2 focus:ring-indigo-500 focus:outline-none"
              placeholder="Describe your message..."></textarea>
          </div>

          <button type="submit"
            class="w-full bg-indigo-600/80 hover:bg-indigo-700 text-white font-medium py-3 rounded-md transition-all duration-200 shadow-lg hover:shadow-indigo-500/30 focus:ring-2 focus:ring-indigo-400 focus:outline-none disabled:opacity-50"
            :disabled="loading">
            {{ loading ? 'Sending...' : 'Send Message' }}
          </button>

          <p v-if="success" class="text-green-500 text-center text-sm mt-4">
            Message sent successfully.
          </p>
          <p v-if="error" class="text-red-500 text-center text-sm mt-4">
            Couldn’t send your message. Please try again.
          </p>
        </form>
      </div>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useApi } from '~/composables/useApi'

const props = defineProps<{
  endpoint: string
  title?: string
}>()

const api = useApi()

const form = ref({
  from: '',
  subject: '',
  message: ''
})

const loading = ref(false)
const success = ref(false)
const error = ref(false)

const submitForm = async () => {
  loading.value = true
  success.value = false
  error.value = false

  try {
    await api.post(props.endpoint, form.value)
    success.value = true
    form.value = { from: '', subject: '', message: '' }
  } catch (err: any) {
    error.value = true
    console.error(err.response?.data || err)
    try {
      await api.get('/trader/subscription') // backend sync if needed
    } catch (syncErr) {
      console.error('Failed to refresh after error', syncErr)
    }
  } finally {
    loading.value = false
  }
}
</script>