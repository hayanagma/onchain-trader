<template>
  <div class="flex h-screen">
    <TraderSideMenu />
    <div class="flex-1 p-8 overflow-y-auto">
      <h1 class="text-2xl font-bold">Dashboard</h1>
      <p>Welcome to your dashboard.</p>
      <NewsletterSubscribe class="mt-8" />
    </div>
  </div>
</template>

<script setup lang="ts">
import TraderSideMenu from '~/components/layout/TraderSideMenu.vue'
import NewsletterSubscribe from '~/components/widget/NewsletterSubscribe.vue'
</script>