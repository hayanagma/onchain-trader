<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useTraderStore } from '~/stores/trader'
import { useNetworkStore } from '~/stores/network'
import TraderSideMenu from '~/components/layout/TraderSideMenu.vue'
import AddWalletModal from '~/components/modal/AddWalletModal.vue'
import AddCurrencyModal from '~/components/modal/AddCurrencyModal.vue'

interface Wallet {
  id: number
  address: string
  network: string
  active: boolean
}

interface Currency {
  code: string
  name: string
  network: string
  kind: string
  contractAddress: string | null
}

interface Subscription {
  plan: string
  active: boolean
  startDate: string
  endDate: string
}

interface Trader {
  id: number
  username: string
  banned: boolean
  wallets: Wallet[]
  currencies: Currency[]
  subscription?: Subscription
}

const traderStore = useTraderStore()
const networkStore = useNetworkStore()

const showAddWalletModal = ref(false)
const showAddCurrencyModal = ref(false)
const showAddress = ref<Record<number, boolean>>({})

const toggleAddress = (id: number) => {
  showAddress.value[id] = !showAddress.value[id]
}

const activeWallets = computed<Wallet[]>(() =>
  traderStore.trader?.wallets?.filter((w) => w.network === networkStore.current) || []
)

const activeCurrencies = computed<Currency[]>(() =>
  traderStore.trader?.currencies?.filter((c) => c.network === networkStore.current) || []
)

onMounted(async () => {
  if (!traderStore.trader) await traderStore.fetchTrader()
})
</script>

<template>
  <div class="flex h-screen bg-gray-950 text-gray-100">
    <TraderSideMenu />

    <main class="flex-1 flex flex-col items-center p-8 overflow-y-auto">
      <div class="w-full max-w-3xl space-y-8">

        <!-- Header -->
        <section class="bg-gray-900 border border-gray-800 rounded-2xl p-8 shadow">
          <div class="flex items-center justify-between">
            <div class="flex items-center gap-4">
              <div class="h-14 w-14 flex items-center justify-center rounded-full bg-indigo-600 text-xl font-bold">
                {{ traderStore.trader?.username?.[0]?.toUpperCase() || 'T' }}
              </div>
              <div>
                <h2 class="text-2xl font-semibold">{{ traderStore.trader?.username || 'Unknown' }}</h2>
                <p class="text-gray-400 text-sm">Trader ID: {{ traderStore.trader?.id || '—' }}</p>
              </div>
            </div>
            <span v-if="traderStore.trader"
              :class="traderStore.trader.banned ? 'bg-red-600/20 text-red-400' : 'bg-green-600/20 text-green-400'"
              class="px-3 py-1 text-xs font-medium rounded-full">
              {{ traderStore.trader.banned ? 'Banned' : 'Active' }}
            </span>
          </div>
        </section>

        <!-- Wallets -->
        <section class="bg-gray-900 border border-gray-800 rounded-2xl p-6 shadow">
          <div class="flex items-center justify-between mb-4">
            <h3 class="text-lg font-semibold">{{ networkStore.current }} Wallets</h3>
            <button @click="showAddWalletModal = true"
              class="rounded-sm bg-indigo-600 px-4 py-2 text-sm font-medium text-white hover:bg-indigo-700 transition">
              Connect Wallet
            </button>
          </div>

          <div v-if="activeWallets.length" class="space-y-3">
            <div v-for="wallet in activeWallets" :key="wallet.id"
              class="bg-gray-800 border border-gray-700 rounded-lg p-4 flex items-center justify-between">
              <div>
                <p class="font-semibold text-gray-200">{{ wallet.network }}</p>
                <p class="text-sm text-gray-400 mt-1">
                  <span v-if="showAddress[wallet.id]">
                    {{ wallet.address }}
                  </span>
                  <span v-else>
                    {{ wallet.address?.slice(0, 6) }}••••••{{ wallet.address?.slice(-4) }}
                  </span>
                </p>
                <button @click="toggleAddress(wallet.id)" class="text-xs text-indigo-400 hover:underline mt-1">
                  {{ showAddress[wallet.id] ? 'Hide' : 'View' }} address
                </button>
              </div>
              <span :class="wallet.active ? 'bg-green-600/20 text-green-400' : 'bg-gray-600/20 text-gray-400'"
                class="text-xs px-2 py-1 rounded-full">
                {{ wallet.active ? 'Active' : 'Inactive' }}
              </span>
            </div>
          </div>

          <p v-else class="text-gray-500 text-sm text-center py-4">
            No wallets connected for this network.
          </p>
        </section>

        <!-- Currencies -->
        <section class="bg-gray-900 border border-gray-800 rounded-2xl p-6 shadow">
          <div class="flex items-center justify-between mb-4">
            <h3 class="text-lg font-semibold">{{ networkStore.current }} Currencies</h3>
            <button @click="showAddCurrencyModal = true"
              class="rounded-sm bg-indigo-600 px-4 py-2 text-sm font-medium text-white hover:bg-indigo-700 transition">
              Add Currency
            </button>
          </div>

          <div v-if="activeCurrencies.length">
            <div v-for="currency in activeCurrencies" :key="currency.code"
              class="bg-gray-800 border border-gray-700 rounded-lg p-4 mb-2">
              <div class="flex justify-between items-center">
                <div>
                  <p class="font-semibold text-gray-200">{{ currency.code }}</p>
                  <p class="text-sm text-gray-400">{{ currency.name }}</p>
                </div>
                <span class="text-xs text-gray-500">{{ currency.kind }}</span>
              </div>
              <p v-if="currency.contractAddress" class="text-xs text-gray-500 mt-1">
                {{ currency.contractAddress }}
              </p>
            </div>
          </div>

          <p v-else class="text-gray-500 text-sm text-center py-4">
            No currencies for this network.
          </p>
        </section>

        <!-- Subscription -->
        <section v-if="traderStore.trader?.subscription"
          class="bg-gray-900 border border-gray-800 rounded-2xl p-6 shadow">
          <h3 class="text-lg font-semibold mb-4 border-b border-gray-800 pb-2">Subscription</h3>
          <dl class="grid grid-cols-1 sm:grid-cols-2 gap-y-3 text-sm">
            <div>
              <dt class="text-gray-400">Plan</dt>
              <dd class="text-gray-200">{{ traderStore.trader.subscription.plan }}</dd>
            </div>
            <div>
              <dt class="text-gray-400">Status</dt>
              <dd :class="traderStore.trader.subscription.active ? 'text-green-400' : 'text-red-400'">
                {{ traderStore.trader.subscription.active ? 'Active' : 'Inactive' }}
              </dd>
            </div>
            <div>
              <dt class="text-gray-400">Start</dt>
              <dd class="text-gray-200">{{ new Date(traderStore.trader.subscription.startDate).toLocaleString() }}</dd>
            </div>
            <div>
              <dt class="text-gray-400">End</dt>
              <dd class="text-gray-200">{{ new Date(traderStore.trader.subscription.endDate).toLocaleString() }}</dd>
            </div>
          </dl>
        </section>

        <p v-else class="text-gray-500 text-center mt-10">No active subscription.</p>
        <nuxt-link to="/trader/subscription"
          class="mt-4 inline-block rounded-sm bg-indigo-600 px-4 py-2 text-sm font-medium text-white hover:bg-indigo-700 transition">
          Manage Subscription
        </nuxt-link>
      </div>
    </main>

    <AddWalletModal v-if="showAddWalletModal" @close="showAddWalletModal = false; traderStore.fetchTrader()" />
    <AddCurrencyModal v-if="showAddCurrencyModal" @close="showAddCurrencyModal = false"
      @success="traderStore.fetchTrader()" />
  </div>
</template>