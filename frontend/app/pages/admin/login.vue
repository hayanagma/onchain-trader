<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAdminAuthStore } from '~/stores/adminAuth'

const adminAuth = useAdminAuthStore()
const router = useRouter()

const username = ref('')
const password = ref('')

const handleLogin = async () => {
  try {
    await adminAuth.login(username.value, password.value)
    await router.push('/admin/dashboard')
  } catch (err) {
    console.error(err)
  }
}
</script>

<template>
  <div class="p-4 flex flex-col gap-2 max-w-sm mx-auto">
    <input
      v-model="username"
      placeholder="Username"
      class="border p-2"
    />
    <input
      v-model="password"
      type="password"
      placeholder="Password"
      class="border p-2"
    />
    <button
      @click="handleLogin"
      class="border p-2"
    >
      Login
    </button>
  </div>
</template>