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

const subscribers = ref<NewsletterSubscriberResponse | null>(null)
const subject = ref('')
const content = ref('')
const api = useApi()

const loadSubscribers = async () => {
  const res = await api.get<NewsletterSubscriberResponse[]>('/admin/newsletters/subscribers')
  subscribers.value = res.data[0] as NewsletterSubscriberResponse
}

const sendNewsletter = async () => {
  if (!subject.value || !content.value) return
  await api.post('/admin/newsletters/send', {
    subject: subject.value,
    content: content.value
  } as NewsletterSendRequest)
  subject.value = ''
  content.value = ''
  alert('Newsletter sent successfully.')
  await loadSubscribers()
}

onMounted(loadSubscribers)
</script>

<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-6">Newsletter Management</h1>

    <section class="mb-8">
      <h2 class="text-xl font-semibold mb-3">Send Newsletter</h2>
      <div class="flex flex-col gap-2 max-w-xl">
        <input
          v-model="subject"
          placeholder="Subject"
          class="border p-2"
        />
        <textarea
          v-model="content"
          placeholder="Content"
          rows="6"
          class="border p-2"
        />
        <button
          @click="sendNewsletter"
          class="border p-2 bg-blue-100 hover:bg-blue-200"
        >
          Send Newsletter
        </button>
      </div>
    </section>

    <section>
      <h2 class="text-xl font-semibold mb-3">Subscribers</h2>
      <div v-if="subscribers">
        <p><strong>Total Subscribers:</strong> {{ subscribers.count }}</p>
        <ul class="list-disc ml-6 mt-2">
          <li v-for="email in subscribers.emails" :key="email">{{ email }}</li>
        </ul>
      </div>
      <div v-else>Loading subscribers...</div>
    </section>
  </div>
</template>