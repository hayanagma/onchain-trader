<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useApi } from '~/composables/useApi'
import TraderSideMenu from '~/components/layout/TraderSideMenu.vue'
import AddWalletModal from '~/components/modal/AddWalletModal.vue'

const api = useApi()
const profile = ref<Record<string, any> | null>(null)
const showAddWalletModal = ref(false)
const showAddress = ref<{ [key: number]: boolean }>({})

const toggleAddress = (id: number) => {
  showAddress.value[id] = !showAddress.value[id]
}

const loadProfile = async () => {
  const { data } = await api.get('/trader/profile')
  profile.value = data
}

onMounted(loadProfile)
</script>

<template>
  <div class="flex h-screen bg-gray-950 text-gray-100">
    <TraderSideMenu />

    <main class="flex-1 flex items-center justify-center p-8 overflow-y-auto">
      <div class="w-full max-w-3xl space-y-8">
        <!-- Header -->
        <div class="bg-gray-900 border border-gray-800 rounded-2xl p-8 shadow">
          <div class="flex items-center justify-between">
            <div class="flex items-center gap-4">
              <div class="h-14 w-14 flex items-center justify-center rounded-full bg-indigo-600 text-xl font-bold">
                {{ profile?.username?.[0]?.toUpperCase() || 'T' }}
              </div>
              <div>
                <h2 class="text-2xl font-semibold">{{ profile?.username || 'Unknown' }}</h2>
                <p class="text-gray-400 text-sm">Trader ID: {{ profile?.id || '—' }}</p>
              </div>
            </div>
            <span v-if="profile" class="px-3 py-1 text-xs font-medium rounded-full"
              :class="profile.banned ? 'bg-red-600/20 text-red-400' : 'bg-green-600/20 text-green-400'">
              {{ profile.banned ? 'Banned' : 'Active' }}
            </span>
          </div>
        </div>

        <!-- Wallets -->
        <div class="bg-gray-900 border border-gray-800 rounded-2xl p-6 shadow">
          <div class="flex items-center justify-between mb-4">
            <h3 class="text-lg font-semibold">Connected Wallets</h3>
            <button @click="showAddWalletModal = true"
              class="rounded-sm bg-indigo-600 px-4 py-2 text-sm font-medium text-white hover:bg-indigo-700 transition">
              Add Wallet
            </button>
          </div>

          <div v-if="profile?.wallets?.length" class="space-y-3">
            <div v-for="wallet in profile.wallets" :key="wallet.id"
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
                <button class="text-xs text-indigo-400 hover:underline mt-1" @click="toggleAddress(wallet.id)">
                  {{ showAddress[wallet.id] ? 'Hide' : 'View' }} address
                </button>
              </div>
              <span class="text-xs px-2 py-1 rounded-full"
                :class="wallet.active ? 'bg-green-600/20 text-green-400' : 'bg-gray-600/20 text-gray-400'">
                {{ wallet.active ? 'Active' : 'Inactive' }}
              </span>
            </div>
          </div>

          <p v-else class="text-gray-500 text-sm text-center py-4">No wallets connected.</p>
        </div>

        <!-- Subscription -->
        <div v-if="profile?.subscription" class="bg-gray-900 border border-gray-800 rounded-2xl p-6 shadow">
          <h3 class="text-lg font-semibold mb-4 border-b border-gray-800 pb-2">Subscription</h3>
          <dl class="grid grid-cols-1 sm:grid-cols-2 gap-y-3 text-sm">
            <div>
              <dt class="text-gray-400">Plan</dt>
              <dd class="text-gray-200">{{ profile.subscription.plan }}</dd>
            </div>
            <div>
              <dt class="text-gray-400">Status</dt>
              <dd :class="profile.subscription.active ? 'text-green-400' : 'text-red-400'">
                {{ profile.subscription.active ? 'Active' : 'Inactive' }}
              </dd>
            </div>
            <div>
              <dt class="text-gray-400">Start</dt>
              <dd class="text-gray-200">{{ new Date(profile.subscription.startDate).toLocaleString() }}</dd>
            </div>
            <div>
              <dt class="text-gray-400">End</dt>
              <dd class="text-gray-200">{{ new Date(profile.subscription.endDate).toLocaleString() }}</dd>
            </div>
          </dl>
        </div>

        <p v-else class="text-gray-500 text-center mt-10">No active subscription.</p>
        <nuxt-link to="/trader/subscription"
          class="mt-4 inline-block rounded-sm bg-indigo-600 px-4 py-2 text-sm font-medium text-white hover:bg-indigo-700 transition">
          Manage Subscription
        </nuxt-link>
      </div>
    </main>

    <!-- Add Wallet Modal -->
    <AddWalletModal v-if="showAddWalletModal" @close="showAddWalletModal = false; loadProfile()" />
  </div>
</template>