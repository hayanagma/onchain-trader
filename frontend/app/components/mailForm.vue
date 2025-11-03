
<template>
  <section class="max-w-lg mx-auto p-6">
    <h2 class="text-2xl font-semibold mb-4">{{ title }}</h2>

    <form @submit.prevent="submitForm" class="space-y-4">
      <div>
        <label class="block text-sm mb-1">Your Email</label>
        <input
          v-model="form.from"
          type="email"
          required
          class="w-full border rounded px-3 py-2"
          placeholder="trader@example.com"
        />
      </div>

      <div>
        <label class="block text-sm mb-1">Subject</label>
        <input
          v-model="form.subject"
          type="text"
          required
          class="w-full border rounded px-3 py-2"
          placeholder="Subject"
        />
      </div>

      <div>
        <label class="block text-sm mb-1">Message</label>
        <textarea
          v-model="form.message"
          required
          rows="4"
          class="w-full border rounded px-3 py-2"
          placeholder="Describe your message..."
        ></textarea>
      </div>

      <button
        type="submit"
        class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        :disabled="loading"
      >
        {{ loading ? 'Sending...' : 'Send Message' }}
      </button>

      <p v-if="success" class="text-green-600 text-sm mt-2">
        Message sent successfully.
      </p>
      <p v-if="error" class="text-red-600 text-sm mt-2">
        Failed to send message. Try again later.
      </p>
    </form>
  </section>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const props = defineProps<{
  endpoint: string
  title?: string
}>()

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
    await $fetch(props.endpoint, {
      method: 'POST',
      body: form.value
    })
    success.value = true
    form.value = { from: '', subject: '', message: '' }
  } catch {
    error.value = true
  } finally {
    loading.value = false
  }
}
</script>