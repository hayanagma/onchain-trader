<script setup lang="ts">
import { ref, onUnmounted } from 'vue'
import { useRouter } from '#app'
import { useApi } from '~/composables/useApi'
import TraderSideMenu from '~/components/layout/TraderSideMenu.vue'

const api = useApi()
const router = useRouter()

const step = ref<'plan' | 'currency' | 'summary'>('plan')
const selectedPlan = ref('')
const selectedCurrency = ref('')
const selectedNetwork = ref('')
const autoRenewal = ref(true)
const payment = ref<Record<string, any> | null>(null)
const loading = ref(false)
const statusInterval = ref<number | null>(null)

const plans = [
    {
        name: 'FREE',
        desc: 'Basic access to the trading bot with limited automation.',
        features: [
            '1 connected wallet',
            'Manual trade execution only',
            'No multi-network support',
            'Limited trading signals'
        ],
        selectable: false
    },
    {
        name: 'PRO',
        desc: 'Full bot automation and cross-network trading support.',
        features: [
            'Up to 5 wallets connected',
            'Automated trades on supported DEXs',
            'Multi-network support (TRON, ETH, SOL, etc.)',
            'Basic analytics dashboard'
        ],
        selectable: true
    },
    {
        name: 'PREMIUM',
        desc: 'Unlimited automation, priority execution, and private strategies.',
        features: [
            'Unlimited wallets and networks',
            'Advanced trading algorithms',
            'Private strategy creation and backtesting',
            'Priority API access and alerts'
        ],
        selectable: true
    }
]

const currencies = [
    { code: 'BTC', network: 'BITCOIN' },
    { code: 'ETH', network: 'ETHEREUM' },
    { code: 'SOL', network: 'SOLANA' },
    { code: 'XMR', network: 'MONERO' },
    { code: 'TRX', network: 'TRON' },
    { code: 'LTC', network: 'LITECOIN' }
]

const selectPlan = (plan: string) => {
    selectedPlan.value = plan
    step.value = 'currency'
}

const chooseCurrency = (currency: string, network: string) => {
    selectedCurrency.value = currency
    selectedNetwork.value = network
}

const confirmPlan = async () => {
    if (!selectedPlan.value || !selectedCurrency.value) return
    loading.value = true
    try {
        const { data } = await api.post('/trader/subscription/payment', {
            plan: selectedPlan.value,
            paymentCurrencyCode: selectedCurrency.value,
            network: selectedNetwork.value,
            autoRenewal: autoRenewal.value
        })
        payment.value = data
        step.value = 'summary'
        startPollingStatus(data.id)
    } catch (err) {
        console.error(err)
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
                await router.push('/trader/profile')
            }

            if (data.status === 'EXPIRED') {
                stopPollingStatus()
                await safeRefreshSubscription()
            }
        } catch (err) {
            console.error(err)
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

const safeRefreshSubscription = async () => {
    try {
        const { data } = await api.get('/trader/subscription')
        if (data) payment.value = data
    } catch (e) {
        console.error('Failed to refresh subscription', e)
    }
}

onUnmounted(stopPollingStatus)
</script>

<template>
    <div class="flex h-screen bg-gray-950 text-gray-100">
        <TraderSideMenu />

        <main class="flex-1 p-10 overflow-y-auto flex justify-center">
            <div class="w-full max-w-5xl space-y-6">
                <h1 class="text-3xl font-bold text-center">Subscription Plans</h1>

                <!-- Step 1: Plan Selection -->
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
                            <button :disabled="!plan.selectable" @click="plan.selectable && selectPlan(plan.name)"
                                class="mt-6 w-full rounded-sm px-4 py-2 text-sm font-medium transition" :class="plan.selectable
                                    ? 'bg-indigo-600 text-white hover:bg-indigo-700'
                                    : 'bg-gray-700 text-gray-400 cursor-not-allowed'">
                                {{ plan.selectable ? 'Choose Plan' : 'Unavailable' }}
                            </button>
                        </div>
                    </div>
                </section>

                <!-- Step 2: Currency Selection -->
                <section v-if="step === 'currency'" class="space-y-4">
                    <h2 class="text-xl font-semibold">Choose Payment Currency</h2>
                    <div class="space-y-3">
                        <div v-for="c in currencies" :key="c.code" @click="chooseCurrency(c.code, c.network)"
                            class="cursor-pointer bg-gray-900 border border-gray-800 rounded-lg p-4 hover:border-indigo-600 transition"
                            :class="selectedCurrency === c.code ? 'border-indigo-600' : ''">
                            <h3 class="text-lg font-bold text-indigo-400">{{ c.code }}</h3>
                            <p class="text-gray-400 text-sm">Network: {{ c.network }}</p>
                        </div>
                    </div>

                    <button @click="confirmPlan" :disabled="!selectedCurrency || loading"
                        class="mt-6 w-full rounded-sm bg-indigo-600 px-4 py-2 text-sm font-medium text-white hover:bg-indigo-700 transition disabled:opacity-50">
                        {{ loading ? 'Processing...' : 'Confirm Subscription' }}
                    </button>
                </section>

                <!-- Step 3: Summary -->
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
            </div>
        </main>
    </div>
</template>