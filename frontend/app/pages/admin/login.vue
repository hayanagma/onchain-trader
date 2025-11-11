<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAdminAuthStore } from '~/stores/adminAuth'

const adminAuth = useAdminAuthStore()
const router = useRouter()

const step = ref<'login' | 'verify'>('login')
const username = ref('')
const password = ref('')
const code = ref('')
const adminId = ref('')

const handleLogin = async () => {
  await adminAuth.requestCode(username.value, password.value)
  adminId.value = username.value
  step.value = 'verify'
}

const handleVerify = async () => {
  await adminAuth.verifyCode(adminId.value, code.value)
  await router.push('/admin/dashboard')
}
</script>

<template>
  <div class="p-4 flex flex-col gap-2 max-w-sm mx-auto">
    <template v-if="step === 'login'">
      <input v-model="username" placeholder="Username" class="border p-2" />
      <input v-model="password" type="password" placeholder="Password" class="border p-2" />
      <button @click="handleLogin" class="border p-2 bg-indigo-600 text-white">Request Code</button>
    </template>

    <template v-else>
      <input v-model="code" placeholder="Enter verification code" class="border p-2" />
      <button @click="handleVerify" class="border p-2 bg-teal-600 text-white">Verify</button>
    </template>
  </div>
</template>
