<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useApi } from '~/composables/useApi'

interface NewsletterSubscriberResponse {
  count: number
  emails: string[]
}

interface NewsletterSendRequest {
  subject: string
  content: string
}

const api = useApi()

const subscribers = ref<NewsletterSubscriberResponse | null>(null)
const subject = ref('')
const content = ref('')
const loading = ref(false)
const message = ref('')

const loadSubscribers = async () => {
  loading.value = true
  message.value = 'Loading subscribers...'
  try {
    const res = await api.get<NewsletterSubscriberResponse[]>('/admin/newsletters/subscribers')
    subscribers.value = res.data[0] as NewsletterSubscriberResponse
    message.value = ''
  } catch (err: any) {
    message.value = err.response?.data?.message || 'Failed to load subscribers. Try again later.'
  } finally {
    loading.value = false
  }
}

const sendNewsletter = async () => {
  if (!subject.value || !content.value) {
    message.value = 'Subject and content are required.'
    return
  }

  loading.value = true
  message.value = 'Sending newsletter...'

  try {
    await api.post('/admin/newsletters/send', {
      subject: subject.value,
      content: content.value,
    } as NewsletterSendRequest)
    subject.value = ''
    content.value = ''
    message.value = 'Newsletter sent successfully.'
    await loadSubscribers()
  } catch (err: any) {
    message.value = err.response?.data?.message || 'Failed to send newsletter. Try again later.'
  } finally {
    loading.value = false
  }
}

onMounted(loadSubscribers)
</script>

<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-6">Newsletter Management</h1>

    <section class="mb-8">
      <h2 class="text-xl font-semibold mb-3">Send Newsletter</h2>
      <div class="flex flex-col gap-2 max-w-xl">
        <input v-model="subject" placeholder="Subject" class="border p-2" />
        <textarea v-model="content" placeholder="Content" rows="6" class="border p-2" />
        <button @click="sendNewsletter" :disabled="loading"
          class="border p-2 bg-blue-100 hover:bg-blue-200 disabled:opacity-50">
          {{ loading ? 'Sending...' : 'Send Newsletter' }}
        </button>

        <p v-if="message" class="text-sm mt-2 text-center"
          :class="message.includes('success') ? 'text-green-500' : 'text-red-500'">
          {{ message }}
        </p>
      </div>
    </section>

    <section>
      <h2 class="text-xl font-semibold mb-3">Subscribers</h2>
      <div v-if="subscribers && !message.includes('Fail')">
        <p><strong>Total Subscribers:</strong> {{ subscribers.count }}</p>
        <ul class="list-disc ml-6 mt-2">
          <li v-for="email in subscribers.emails" :key="email">{{ email }}</li>
        </ul>
      </div>
      <div v-else-if="loading">Loading subscribers...</div>
    </section>
  </div>
</template>