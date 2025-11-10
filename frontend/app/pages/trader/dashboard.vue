<script setup lang="ts">
import { onMounted } from 'vue'
import { useTraderStore } from '~/stores/trader'
import NewsletterSubscribe from '~/components/widget/NewsletterSubscribe.vue'

definePageMeta({ layout: 'trader' })

const traderStore = useTraderStore()

onMounted(async () => {
  if (!traderStore.trader) await traderStore.fetchTrader()
})
</script>

<template>
  <section class="p-8">
    <h1 class="text-2xl font-bold">Dashboard</h1>
    <p>Welcome to your dashboard.</p>
    <NewsletterSubscribe class="mt-8" />
  </section>
</template>