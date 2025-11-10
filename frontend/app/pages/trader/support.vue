<script setup lang="ts">
import MailForm from '~/components/form/MailForm.vue'
import TraderSideMenu from '@/components/layout/TraderSideMenu.vue'

definePageMeta({ layout: 'trader' })
</script>

<template>
  <div class="flex h-screen">
    <div class="flex-1 p-8 overflow-y-auto">
      <h1 class="text-2xl font-bold mb-4">Trader Support</h1>
      <MailForm endpoint="/public/mail/support" title="Contact Support" />
    </div>
  </div>
</template>