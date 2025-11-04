<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useApi } from '~/composables/useApi'

interface CurrencyResponse {
  id: number
  code: string
  name: string
  network: string
  decimals: number
  kind: string
  contractAddress: string | null
}

interface PaymentCurrencyResponse {
  id: number
  code: string
  name: string
  network: string
  decimals: number
  kind: string
  contractAddress: string | null
  enabled: boolean
}

const tradingCurrencies = ref<CurrencyResponse[]>([])
const paymentCurrencies = ref<PaymentCurrencyResponse[]>([])
const api = useApi()

const loadCurrencies = async () => {
  const tradingRes = await api.get<CurrencyResponse[]>('/admin/currencies')
  tradingCurrencies.value = tradingRes.data

  const paymentRes = await api.get<PaymentCurrencyResponse[]>('/admin/payment-currencies')
  paymentCurrencies.value = paymentRes.data
}

onMounted(loadCurrencies)
</script>

<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-6">Currencies</h1>

    <section class="mb-8">
      <h2 class="text-xl font-semibold mb-3">Trading Currencies</h2>
      <table class="border-collapse border w-full">
        <thead>
          <tr class="border-b">
            <th class="border p-2">Code</th>
            <th class="border p-2">Name</th>
            <th class="border p-2">Network</th>
            <th class="border p-2">Kind</th>
            <th class="border p-2">Decimals</th>
            <th class="border p-2">Contract Address</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="c in tradingCurrencies" :key="c.id" class="border-b">
            <td class="border p-2">{{ c.code }}</td>
            <td class="border p-2">{{ c.name }}</td>
            <td class="border p-2">{{ c.network }}</td>
            <td class="border p-2">{{ c.kind }}</td>
            <td class="border p-2">{{ c.decimals }}</td>
            <td class="border p-2">{{ c.contractAddress || 'native' }}</td>
          </tr>
        </tbody>
      </table>
    </section>

    <section>
      <h2 class="text-xl font-semibold mb-3">Payment Currencies</h2>
      <table class="border-collapse border w-full">
        <thead>
          <tr class="border-b">
            <th class="border p-2">Code</th>
            <th class="border p-2">Name</th>
            <th class="border p-2">Network</th>
            <th class="border p-2">Kind</th>
            <th class="border p-2">Decimals</th>
            <th class="border p-2">Contract Address</th>
            <th class="border p-2">Enabled</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="p in paymentCurrencies" :key="p.id" class="border-b">
            <td class="border p-2">{{ p.code }}</td>
            <td class="border p-2">{{ p.name }}</td>
            <td class="border p-2">{{ p.network }}</td>
            <td class="border p-2">{{ p.kind }}</td>
            <td class="border p-2">{{ p.decimals }}</td>
            <td class="border p-2">{{ p.contractAddress || 'native' }}</td>
            <td class="border p-2">{{ p.enabled }}</td>
          </tr>
        </tbody>
      </table>
    </section>
  </div>
</template>