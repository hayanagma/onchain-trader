<script setup>
import { ref, onMounted } from "vue";
import { useApi } from "~/composables/useApi";

const profile = ref(null);

onMounted(async () => {
  const api = useApi();
  const { data } = await api.get("/trader/profile");
  profile.value = data;
});
</script>

<template>
  <div class="p-4">
    <h1 class="text-2xl font-bold">Profile Page</h1>
    <pre v-if="profile">{{ profile }}</pre>
    <p v-else>Loading...</p>
  </div>
</template>
