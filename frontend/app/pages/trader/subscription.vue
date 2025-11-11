<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from '#app'
import { useApi } from '~/composables/useApi'

definePageMeta({ layout: 'trader' })

const api = useApi()
const router = useRouter()

const step = ref<'plan' | 'currency' | 'summary'>('plan')
const selectedPlan = ref('')
const selectedCurrency = ref('')
const selectedNetwork = ref('')
const autoRenewal = ref(true)
const payment = ref<Record<string, any> | null>(null)
const loading = ref(false)
const message = ref('')
const currentPlan = ref<string | null>(null)
const statusInterval = ref<number | null>(null)

const plans = [
    { name: 'FREE', desc: 'Basic access to the trading bot with limited automation.', level: 0, features: ['1 connected wallet', 'Manual trade execution only', 'No multi-network support', 'Limited trading signals'], selectable: false },
    { name: 'PRO', desc: 'Full bot automation and cross-network trading support.', level: 1, features: ['Up to 5 wallets connected', 'Automated trades on supported DEXs', 'Multi-network support (TRON, ETH, SOL, etc.)', 'Basic analytics dashboard'], selectable: true },
    { name: 'PREMIUM', desc: 'Unlimited automation, priority execution, and private strategies.', level: 2, features: ['Unlimited wallets and networks', 'Advanced trading algorithms', 'Private strategy creation and backtesting', 'Priority API access and alerts'], selectable: true }
]

const currencies = [
    { code: 'BTC', network: 'BITCOIN' },
    { code: 'ETH', network: 'ETHEREUM' },
    { code: 'SOL', network: 'SOLANA' },
    { code: 'XMR', network: 'MONERO' },
    { code: 'TRX', network: 'TRON' },
    { code: 'LTC', network: 'LITECOIN' }
]

onMounted(async () => {
    try {
        const { data } = await api.get('/trader/subscription')
        currentPlan.value = data?.plan || null
    } catch (err) {
        console.error('Failed to load current plan', err)
    }
})

const planLevel = (plan: string | null) => {
    const found = plans.find(p => p.name === plan)
    return found ? found.level : -1
}

const selectPlan = (plan: string) => {
    const selected = plans.find(p => p.name === plan)
    if (!selected) return
    if (planLevel(plan) <= planLevel(currentPlan.value)) return
    selectedPlan.value = plan
    step.value = 'currency'
}

const chooseCurrency = (currency: string, network: string) => {
    selectedCurrency.value = currency
    selectedNetwork.value = network
}

const confirmPlan = async () => {
    if (!selectedPlan.value || !selectedCurrency.value) {
        message.value = 'Please select both plan and currency.'
        return
    }
    loading.value = true
    message.value = 'Creating payment request...'
    try {
        const { data } = await api.post('/trader/subscription/payment', {
            plan: selectedPlan.value,
            paymentCurrencyCode: selectedCurrency.value,
            network: selectedNetwork.value,
            autoRenewal: autoRenewal.value
        })
        payment.value = data
        step.value = 'summary'
        message.value = ''
        startPollingStatus(data.id)
    } catch (err: any) {
        message.value = err.response?.data?.message || 'Failed to create payment. Try again later.'
        await safeRefreshSubscription()
    } finally {
        loading.value = false
    }
}

const startPollingStatus = (paymentId: number) => {
    stopPollingStatus()
    statusInterval.value = window.setInterval(async () => {
        try {
            const { data } = await api.get(`/trader/subscription/status/${paymentId}`)
            payment.value = data

            if (data.status === 'CONFIRMED') {
                stopPollingStatus()
                await safeRefreshSubscription()

                // ADD THIS BLOCK ↓↓↓
                try {
                    const { useTraderStore } = await import('~/stores/trader')
                    const traderStore = useTraderStore()
                    await traderStore.fetchTrader()
                } catch (e) {
                    console.error('Failed to refresh trader store', e)
                }

                message.value = 'Payment confirmed. Redirecting...'
                await router.push('/trader/profile')
            }

            if (data.status === 'EXPIRED') {
                stopPollingStatus()
                await safeRefreshSubscription()
                message.value = 'Payment expired. Please try again.'
            }
        } catch {
            message.value = 'Failed to check payment status.'
            await safeRefreshSubscription()
        }
    }, 25000)
}

const stopPollingStatus = () => {
    if (statusInterval.value) {
        clearInterval(statusInterval.value)
        statusInterval.value = null
    }
}

// FIXED: update currentPlan after fulfillment
const safeRefreshSubscription = async () => {
    try {
        const { data } = await api.get('/trader/subscription')
        if (data) {
            payment.value = data
            currentPlan.value = data.plan || null
        }
    } catch (e) {
        console.error('Failed to refresh subscription', e)
    }
}

onUnmounted(stopPollingStatus)
</script>

<template>
    <div class="flex h-screen bg-gray-950 text-gray-100">
        <main class="flex-1 p-10 overflow-y-auto flex justify-center">
            <div class="w-full max-w-5xl space-y-10">
                <h1 class="text-3xl font-bold text-center">Subscription Plans</h1>

                <!-- Steps -->
                <div>
                    <h2 class="sr-only">Steps</h2>
                    <div
                        class="after:mt-4 after:block after:h-1 after:w-full after:rounded-lg after:bg-gray-200 dark:after:bg-gray-700">
                        <ol class="grid grid-cols-3 text-sm font-medium text-gray-600 dark:text-gray-300">
                            <li class="relative flex justify-start"
                                :class="step === 'plan' || step === 'currency' || step === 'summary' ? 'text-blue-500' : 'text-gray-400'">
                                <span class="absolute start-0 -bottom-7 rounded-full"
                                    :class="step === 'plan' || step === 'currency' || step === 'summary' ? 'bg-blue-500 text-white' : 'bg-gray-500 text-white'">
                                    <svg class="size-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
                                        fill="currentColor">
                                        <path fill-rule="evenodd"
                                            d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                                            clip-rule="evenodd" />
                                    </svg>
                                </span>
                                <span class="hidden sm:block"> Plan </span>
                            </li>

                            <li class="relative flex justify-center"
                                :class="step === 'currency' || step === 'summary' ? 'text-blue-500' : 'text-gray-400'">
                                <span class="absolute -bottom-7 left-1/2 -translate-x-1/2 rounded-full"
                                    :class="step === 'currency' || step === 'summary' ? 'bg-blue-500 text-white' : 'bg-gray-500 text-white'">
                                    <svg class="size-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
                                        fill="currentColor">
                                        <path fill-rule="evenodd"
                                            d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                                            clip-rule="evenodd" />
                                    </svg>
                                </span>
                                <span class="hidden sm:block"> Currency </span>
                            </li>

                            <li class="relative flex justify-end"
                                :class="step === 'summary' ? 'text-blue-500' : 'text-gray-400'">
                                <span class="absolute end-0 -bottom-7 rounded-full"
                                    :class="step === 'summary' ? 'bg-blue-500 text-white' : 'bg-gray-500 text-white'">
                                    <svg class="size-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"
                                        fill="currentColor">
                                        <path fill-rule="evenodd"
                                            d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z"
                                            clip-rule="evenodd" />
                                    </svg>
                                </span>
                                <span class="hidden sm:block"> Payment </span>
                            </li>
                        </ol>
                    </div>
                </div>

                <!-- Rest unchanged -->
                <section v-if="step === 'plan'">
                    <h2 class="text-xl font-semibold text-center mb-6">Select a Plan</h2>
                    <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
                        <div v-for="plan in plans" :key="plan.name"
                            class="bg-gray-900 border border-gray-800 rounded-lg p-6 flex flex-col justify-between hover:border-indigo-600 transition">
                            <div>
                                <h3 class="text-2xl font-bold text-indigo-400 mb-2 text-center">{{ plan.name }}</h3>
                                <p class="text-gray-400 text-sm text-center mb-4">{{ plan.desc }}</p>
                                <ul class="text-sm text-gray-300 space-y-1">
                                    <li v-for="feature in plan.features" :key="feature">• {{ feature }}</li>
                                </ul>
                            </div>

                            <button :disabled="!plan.selectable || planLevel(plan.name) <= planLevel(currentPlan)"
                                @click="plan.selectable && selectPlan(plan.name)"
                                class="mt-6 w-full rounded-sm px-4 py-2 text-sm font-medium transition" :class="[
                                    plan.name === currentPlan
                                        ? 'bg-gray-700 text-gray-400 cursor-not-allowed'
                                        : planLevel(plan.name) <= planLevel(currentPlan)
                                            ? 'bg-gray-700 text-gray-400 cursor-not-allowed'
                                            : plan.selectable
                                                ? 'bg-indigo-600 text-white hover:bg-indigo-700'
                                                : 'bg-gray-700 text-gray-400 cursor-not-allowed'
                                ]">
                                <template v-if="plan.name === currentPlan">Current Plan</template>
                                <template v-else-if="planLevel(plan.name) > planLevel(currentPlan)">Upgrade</template>
                                <template v-else>Unavailable</template>
                            </button>
                        </div>
                    </div>
                </section>

                <section v-if="step === 'currency'" class="space-y-4">
                    <h2 class="text-xl font-semibold mb-4">Choose Payment Currency</h2>
                    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
                        <div v-for="c in currencies" :key="c.code" @click="chooseCurrency(c.code, c.network)"
                            class="cursor-pointer bg-gray-900 border border-gray-800 rounded-lg p-4 flex items-center gap-4 hover:border-indigo-600 transition"
                            :class="selectedCurrency === c.code ? 'border-indigo-600' : ''">
                            <img :src="`/icons/${c.network.toLowerCase()}.svg`" class="w-8 h-8" />
                            <div>
                                <h3 class="text-lg font-bold text-indigo-400">{{ c.code }}</h3>
                                <p class="text-gray-400 text-xs">Network: {{ c.network }}</p>
                            </div>
                        </div>
                    </div>

                    <button @click="confirmPlan" :disabled="!selectedCurrency || loading"
                        class="mt-6 w-full rounded-sm bg-indigo-600 px-4 py-2 text-sm font-medium text-white hover:bg-indigo-700 transition disabled:opacity-50">
                        {{ loading ? 'Processing...' : 'Confirm Currency' }}
                    </button>
                </section>

                <section v-if="step === 'summary' && payment"
                    class="bg-gray-900 border border-gray-800 rounded-2xl p-6 space-y-4">
                    <h2 class="text-xl font-semibold text-indigo-400">Payment Summary</h2>
                    <div class="grid grid-cols-1 sm:grid-cols-2 gap-y-2 text-sm">
                        <p><strong>Plan:</strong> {{ payment.plan || selectedPlan }}</p>
                        <p><strong>Currency:</strong> {{ payment.paymentCurrencyCode }}</p>
                        <p><strong>Network:</strong> {{ payment.network }}</p>
                        <p><strong>Status:</strong> {{ payment.status }}</p>
                        <p><strong>Amount:</strong> {{ payment.amount }}</p>
                        <p><strong>Deposit Address:</strong> {{ payment.depositAddress }}</p>
                        <p><strong>Created:</strong> {{ new Date(payment.createdAt).toLocaleString() }}</p>
                        <p><strong>Expires:</strong> {{ new Date(payment.expiresAt).toLocaleString() }}</p>
                    </div>

                    <div class="flex justify-center mt-6">
                        <img v-if="payment.qrCodeUrl" :src="payment.qrCodeUrl" alt="QR Code"
                            class="h-40 w-40 border border-gray-700 rounded-lg" />
                    </div>

                    <div class="mt-6 text-center">
                        <p class="text-gray-400 text-sm">
                            Send the exact amount to the deposit address to activate your subscription.
                        </p>
                    </div>
                </section>

                <p v-if="message" class="text-center text-sm mt-4 text-gray-300">{{ message }}</p>
            </div>
        </main>
    </div>
</template>
