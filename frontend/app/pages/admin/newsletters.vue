<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useApi } from '~/composables/useApi'

interface Newsletter {
  id: number
  subject: string
  content: string
  recipientCount: number
  sentAt: string
}

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
const newsletters = ref<Newsletter[]>([])
const subject = ref('')
const content = ref('')
const loading = ref(false)
const message = ref('')

const loadData = async () => {
  loading.value = true
  message.value = 'Loading data...'
  try {
    const [subsRes, newslettersRes] = await Promise.all([
      api.get<NewsletterSubscriberResponse[]>('/admin/newsletters/subscribers'),
      api.get<Newsletter[]>('/admin/newsletters')
    ])
    subscribers.value = subsRes.data[0] || { count: 0, emails: [] }
    newsletters.value = newslettersRes.data || []
    message.value = ''
  } catch (err: any) {
    message.value = err.response?.data?.message || 'Failed to load newsletters or subscribers.'
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
      content: content.value
    } as NewsletterSendRequest)
    subject.value = ''
    content.value = ''
    message.value = 'Newsletter sent successfully.'
    await loadData()
  } catch (err: any) {
    message.value = err.response?.data?.message || 'Failed to send newsletter.'
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>

<template>
  <div class="p-6 space-y-10">
    <h1 class="text-2xl font-bold">Newsletter Management</h1>

    <!-- Subscribers -->
    <section class="bg-gray-50 dark:bg-gray-900 border border-gray-200 dark:border-gray-700 rounded-xl p-6">
      <h2 class="text-xl font-semibold mb-3">Subscribers</h2>
      <div v-if="subscribers && !message.includes('Fail')">
        <p><strong>Total Subscribers:</strong> {{ subscribers.count }}</p>
        <ul class="list-disc ml-6 mt-2">
          <li v-for="email in subscribers.emails" :key="email">{{ email }}</li>
        </ul>
      </div>
      <div v-else-if="loading">Loading subscribers...</div>
      <div v-else>No subscriber data available.</div>
    </section>

    <!-- Send Newsletter -->
    <section class="bg-gray-50 dark:bg-gray-900 border border-gray-200 dark:border-gray-700 rounded-xl p-6">
      <h2 class="text-xl font-semibold mb-4">Send Newsletter</h2>
      <div class="flex flex-col gap-2 max-w-xl">
        <input v-model="subject" placeholder="Subject"
          class="border border-gray-300 dark:border-gray-700 rounded-md p-2 dark:bg-gray-800 dark:text-gray-100" />
        <textarea v-model="content" placeholder="Content" rows="6"
          class="border border-gray-300 dark:border-gray-700 rounded-md p-2 dark:bg-gray-800 dark:text-gray-100"></textarea>
        <button @click="sendNewsletter" :disabled="loading"
          class="rounded-md border px-4 py-2 bg-blue-600 text-white hover:bg-blue-700 transition disabled:opacity-50">
          {{ loading ? 'Sending...' : 'Send Newsletter' }}
        </button>

        <p v-if="message" class="text-sm mt-2 text-center"
          :class="message.includes('success') ? 'text-green-500' : 'text-red-500'">
          {{ message }}
        </p>
      </div>
    </section>

    <!-- Sent Newsletters -->
    <section class="bg-gray-50 dark:bg-gray-900 border border-gray-200 dark:border-gray-700 rounded-xl p-6">
      <h2 class="text-xl font-semibold mb-3">Sent Newsletters</h2>
      <div v-if="newsletters.length">
        <table class="w-full text-sm border-collapse">
          <thead class="border-b border-gray-300 dark:border-gray-700">
            <tr class="text-left">
              <th class="py-2 px-2">Subject</th>
              <th class="py-2 px-2">Recipients</th>
              <th class="py-2 px-2">Sent At</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="n in newsletters" :key="n.id"
              class="border-b border-gray-200 dark:border-gray-800 hover:bg-gray-100 dark:hover:bg-gray-800/50">
              <td class="py-2 px-2 font-medium">{{ n.subject }}</td>
              <td class="py-2 px-2">{{ n.recipientCount }}</td>
              <td class="py-2 px-2">{{ new Date(n.sentAt).toLocaleString() }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div v-else class="text-gray-500 text-sm">No newsletters have been sent yet.</div>
    </section>
  </div>
</template>
