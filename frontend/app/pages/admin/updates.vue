<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useApi } from '~/composables/useApi'

interface UpdateResponse {
  id: number
  title: string
  description: string
  status: string
  plannedReleaseDate: string | null
  releasedAt: string | null
}

const updates = ref<UpdateResponse[]>([])
const editingId = ref<number | null>(null)
const title = ref('')
const description = ref('')
const status = ref('PLANNED')
const plannedReleaseDate = ref<string | null>(null)
const releasedAt = ref<string | null>(null)
const api = useApi()

const loadUpdates = async () => {
  const res = await api.get<UpdateResponse[]>('/public/updates')
  updates.value = res.data
}

const saveUpdate = async () => {
  if (!title.value || !description.value || !status.value) return

  if (editingId.value) {
    await api.put('/admin/updates/edit', {
      id: editingId.value,
      title: title.value,
      description: description.value,
      status: status.value,
      plannedReleaseDate: plannedReleaseDate.value,
      releasedAt: releasedAt.value
    })
    alert('Update edited.')
  } else {
    await api.post('/admin/updates/create', {
      title: title.value,
      description: description.value,
      status: status.value,
      plannedReleaseDate: plannedReleaseDate.value,
      releasedAt: releasedAt.value
    })
    alert('Update created.')
  }

  title.value = ''
  description.value = ''
  status.value = 'PLANNED'
  plannedReleaseDate.value = null
  releasedAt.value = null
  editingId.value = null
  await loadUpdates()
}

const editUpdate = (u: UpdateResponse) => {
  editingId.value = u.id
  title.value = u.title
  description.value = u.description
  status.value = u.status
  plannedReleaseDate.value = u.plannedReleaseDate
  releasedAt.value = u.releasedAt
}

onMounted(loadUpdates)
</script>

<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-6">Admin Updates Management</h1>

    <section class="mb-8">
      <h2 class="text-xl font-semibold mb-3">
        {{ editingId ? 'Edit Update' : 'Create Update' }}
      </h2>
      <div class="flex flex-col gap-2 max-w-xl">
        <input v-model="title" placeholder="Title" class="border p-2" />
        <textarea v-model="description" placeholder="Description" rows="6" class="border p-2" />
        <select v-model="status" class="border p-2">
          <option value="PLANNED">PLANNED</option>
          <option value="RELEASED">RELEASED</option>
          <option value="CANCELLED">CANCELLED</option>
        </select>
        <label>Planned Release Date</label>
        <input type="date" v-model="plannedReleaseDate" class="border p-2" />
        <label>Released At</label>
        <input type="date" v-model="releasedAt" class="border p-2" />
        <button @click="saveUpdate" class="border p-2 bg-blue-100 hover:bg-blue-200">
          {{ editingId ? 'Save Changes' : 'Create Update' }}
        </button>
      </div>
    </section>

    <section>
      <h2 class="text-xl font-semibold mb-3">Existing Updates</h2>
      <div v-if="updates.length" class="space-y-4">
        <div v-for="u in updates" :key="u.id" class="border rounded-lg p-4">
          <h3 class="text-lg font-bold">{{ u.title }}</h3>
          <p>{{ u.description }}</p>
          <p>Status: {{ u.status }}</p>
          <p v-if="u.plannedReleaseDate">Planned: {{ u.plannedReleaseDate }}</p>
          <p v-if="u.releasedAt">Released: {{ u.releasedAt }}</p>
          <button
            @click="editUpdate(u)"
            class="border p-1 mt-2 bg-yellow-100 hover:bg-yellow-200"
          >
            Edit
          </button>
        </div>
      </div>
      <div v-else>No updates found.</div>
    </section>
  </div>
</template>