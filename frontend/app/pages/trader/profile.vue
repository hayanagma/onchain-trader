<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useTraderStore } from '~/stores/trader'
import { useNetworkStore } from '~/stores/network'
import { useApi } from '~/composables/useApi'
import AddWalletModal from '~/components/modal/AddWalletModal.vue'
import AddCurrencyModal from '~/components/modal/AddCurrencyModal.vue'
import UpdateUsernameModal from '~/components/modal/UpdateUsernameModal.vue'
import DeactivateWalletModal from '~/components/modal/DeactivateWalletModal.vue'
import DeleteCurrencyModal from '~/components/modal/DeleteCurrencyModal.vue'

definePageMeta({ layout: 'trader' })

interface Wallet {
  id: number
  address: string
  network: string
  active: boolean
}

interface Currency {
  id: number
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
const api = useApi()

const showAddWalletModal = ref(false)
const showAddCurrencyModal = ref(false)
const showUpdateUsernameModal = ref(false)
const showDeactivateModal = ref(false)
const showDeleteCurrencyModal = ref(false)

const walletToDeactivate = ref<number | null>(null)
const currencyToDelete = ref<number | null>(null)
const showAddress = ref<Record<number, boolean>>({})

const toggleAddress = (id: number) => {
  showAddress.value[id] = !showAddress.value[id]
}

const activeWallets = computed<Wallet[]>(() =>
  traderStore.trader?.wallets?.filter((w: Wallet) => w.network === networkStore.current) || []
)

const activeCurrencies = computed<Currency[]>(() =>
  traderStore.trader?.currencies?.filter((c: Currency) => c.network === networkStore.current) || []
)

const openDeactivateModal = (id: number) => {
  walletToDeactivate.value = id
  showDeactivateModal.value = true
}

const openDeleteCurrencyModal = (currency: Currency) => {
  currencyToDelete.value = currency.id
  showDeleteCurrencyModal.value = true
}

onMounted(async () => {
  if (!traderStore.trader) await traderStore.fetchTrader()
})
</script>

<template>
  <div class="flex h-screen bg-gray-950 text-gray-100">
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
                <h2 class="text-2xl font-semibold flex items-center gap-3">
                  {{ traderStore.trader?.username || 'Unknown' }}
                  <button @click="showUpdateUsernameModal = true"
                    class="text-xs px-2 py-1 border border-gray-700 rounded hover:bg-gray-800 text-indigo-400">
                    Edit
                  </button>
                </h2>
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

              <div class="flex items-center gap-2">
                <span :class="wallet.active ? 'bg-green-600/20 text-green-400' : 'bg-gray-600/20 text-gray-400'"
                  class="text-xs px-2 py-1 rounded-full">
                  {{ wallet.active ? 'Active' : 'Inactive' }}
                </span>

                <button v-if="wallet.active" @click="openDeactivateModal(wallet.id)"
                  :disabled="activeWallets.filter(w => w.active).length <= 1"
                  class="text-xs border rounded px-2 py-1 transition" :class="[activeWallets.filter(w => w.active).length <= 1
                    ? 'text-gray-500 border-gray-700 cursor-not-allowed opacity-50'
                    : 'text-yellow-400 border-yellow-500/30 hover:text-yellow-500']">
                  Deactivate
                </button>
              </div>
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
            <div v-for="currency in activeCurrencies" :key="currency.id"
              class="bg-gray-800 border border-gray-700 rounded-lg p-4 mb-2">
              <div class="flex justify-between items-center">
                <div>
                  <p class="font-semibold text-gray-200">{{ currency.code }}</p>
                  <p class="text-sm text-gray-400">{{ currency.name }}</p>
                </div>
                <div class="flex items-center gap-2">
                  <span class="text-xs text-gray-500">{{ currency.kind }}</span>
                  <button v-if="currency.kind !== 'NATIVE'" @click="openDeleteCurrencyModal(currency)"
                    class="text-xs border border-red-500/30 text-red-400 hover:text-red-500 rounded px-2 py-1 transition">
                    Delete
                  </button>
                </div>
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
    <UpdateUsernameModal v-if="showUpdateUsernameModal" @close="showUpdateUsernameModal = false"
      @updated="traderStore.fetchTrader()" />
    <DeactivateWalletModal v-if="showDeactivateModal" :walletId="walletToDeactivate"
      @close="showDeactivateModal = false" @updated="traderStore.fetchTrader()" />
    <DeleteCurrencyModal v-if="showDeleteCurrencyModal" :currency-id="currencyToDelete"
      @close="showDeleteCurrencyModal = false" @updated="traderStore.fetchTrader()" />
  </div>
</template>
