<script setup>
const config = useRuntimeConfig()
const apiBase = config.public.apiBase
const profile = ref(null)

onMounted(async () => {
  const data = await $fetch(`${apiBase}/player/profile`, {
    credentials: 'include'
  })
  profile.value = data
})
</script>

<template>
  <div class="p-4">
    <h1 class="text-2xl font-bold">Profile Page</h1>
    <pre v-if="profile">{{ profile }}</pre>
    <p v-else>Loading...</p>
  </div>
</template>