<script setup lang="ts">
import { ref, onMounted, onActivated } from 'vue'
import { useApi } from '~/composables/useApi'

interface WalletTraderResponse {
  id: number
  address: string
  network: string
  active: boolean
}
interface CurrencyResponse {
  id: number
  code: string
  name: string
  network: string
  decimals: number
  kind: string
  contractAddress: string | null
}
interface NetworkAccountResponse {
  id: number
  traderId: number
  network: string
}
interface SubscriptionResponse {
  plan: string
  active: boolean
  startDate: string
  endDate: string
}
interface AdminTraderResponse {
  id: number
  username: string
  banned: boolean
  bannedReason: string | null
  active: boolean
  wallets: WalletTraderResponse[]
  currencies: CurrencyResponse[]
  networkAccounts: NetworkAccountResponse[]
  subscriptionPlan: string | null
  subscription: SubscriptionResponse | null
}

const traders = ref<AdminTraderResponse[]>([])
const walletAddress = ref('')
const api = useApi()

const loadTraders = async () => {
  const res = await api.get<AdminTraderResponse[]>('/admin/traders', {
    params: { walletAddress: walletAddress.value || undefined }
  })
  traders.value = res.data
}

const updateBanStatus = async (trader: AdminTraderResponse, banned: boolean) => {
  try {
    const reason = banned ? prompt('Enter ban reason:') || '' : ''
    await api.put('/admin/trader/ban-status', {
      traderId: trader.id,
      banned,
      reason
    })
    await loadTraders()
  } catch (err) {
    console.error(err)
  }
}

onMounted(loadTraders)
onActivated(loadTraders)
</script>

<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-4">Traders</h1>

    <div class="flex mb-4">
      <input
        v-model="walletAddress"
        placeholder="Filter by wallet"
        class="border p-2 mr-2"
      />
      <button @click="loadTraders" class="border p-2">Search</button>
    </div>

    <div v-for="t in traders" :key="t.id" class="border rounded-lg p-4 mb-4">
      <div class="flex justify-between items-center mb-2">
        <h2 class="text-xl font-semibold">
          {{ t.username }}
          <span v-if="t.banned" class="text-red-600">(Banned)</span>
        </h2>
        <button
          @click="updateBanStatus(t, !t.banned)"
          class="px-3 py-1 border rounded"
          :class="t.banned ? 'bg-green-200' : 'bg-red-200'"
        >
          {{ t.banned ? 'Unban' : 'Ban' }}
        </button>
      </div>

      <p><strong>ID:</strong> {{ t.id }}</p>
      <p><strong>Active:</strong> {{ t.active }}</p>

      <p v-if="t.banned" class="text-red-600">
        <strong>Banned:</strong> Yes
      </p>
      <p v-else>
        <strong>Banned:</strong> No
      </p>

      <p v-if="t.bannedReason"><strong>Banned Reason:</strong> {{ t.bannedReason }}</p>

      <p><strong>Subscription Plan:</strong> {{ t.subscriptionPlan || 'None' }}</p>

      <div v-if="t.subscription" class="mb-2">
        <p>
          <strong>Subscription:</strong> {{ t.subscription.plan }}
          (Active: {{ t.subscription.active }})
        </p>
        <p class="text-sm text-gray-600">
          Start: {{ t.subscription.startDate }} | End: {{ t.subscription.endDate }}
        </p>
      </div>
      <p v-else><strong>Subscription:</strong> N/A</p>

      <div class="mt-4">
        <h3 class="font-bold">Wallets</h3>
        <ul class="ml-4 list-disc">
          <li v-for="w in t.wallets" :key="w.id">
            {{ w.network }} - {{ w.address }} (Active: {{ w.active }})
          </li>
        </ul>
      </div>

      <div class="mt-4">
        <h3 class="font-bold">Currencies</h3>
        <ul class="ml-4 list-disc">
          <li v-for="c in t.currencies" :key="c.id">
            {{ c.code }} ({{ c.network }}) - {{ c.kind }} - {{ c.contractAddress || 'native' }}
          </li>
        </ul>
      </div>

      <div class="mt-4">
        <h3 class="font-bold">Network Accounts</h3>
        <ul class="ml-4 list-disc">
          <li v-for="n in t.networkAccounts" :key="n.id">
            {{ n.network }} (Trader ID: {{ n.traderId }})
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>
