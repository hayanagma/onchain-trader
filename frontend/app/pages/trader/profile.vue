<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useApi } from '~/composables/useApi'
import TraderSideMenu from '~/components/layout/TraderSideMenu.vue'

const profile = ref(null)

onMounted(async () => {
  const api = useApi()
  const { data } = await api.get('/trader/profile')
  profile.value = data
})
</script>

<template>
  <div class="flex h-screen">
    <TraderSideMenu />

    <div class="flex-1 p-8 overflow-y-auto">
      <h1 class="text-2xl font-bold mb-4">Profile Page</h1>

      <pre v-if="profile" class="bg-gray-100 p-4 rounded text-sm">{{ profile }}</pre>
      <p v-else>Loading...</p>
    </div>
  </div>
</template>