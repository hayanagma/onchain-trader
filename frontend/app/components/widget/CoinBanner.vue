<script setup lang="ts">
const coins = [
    { src: '/icons/bitcoin.svg', alt: 'Bitcoin' },
    { src: '/icons/ethereum.svg', alt: 'Ethereum' },
    { src: '/icons/tron.svg', alt: 'Tron' },
    { src: '/icons/solana.svg', alt: 'Solana' },
]
const duration = 20 // seconds per full scroll loop
</script>


<template>
    <div class="bg-gray-900 py-6 flex items-center justify-center overflow-hidden">
        <div class="flex items-center animate-scroll gap-12" :style="{ '--duration': `${duration}s` }">
            <template v-for="n in 2" :key="n">
                <img v-for="(coin, i) in coins" :key="`${n}-${i}`" :src="coin.src" :alt="coin.alt"
                    class="h-12 w-12 object-contain grayscale brightness-200 opacity-80 hover:opacity-100 transition" />
            </template>
        </div>
    </div>
</template>

<style scoped>
@keyframes scroll {
    0% {
        transform: translateX(0);
    }

    100% {
        transform: translateX(-50%);
    }
}

.animate-scroll {
    display: flex;
    width: max-content;
    animation: scroll var(--duration) linear infinite;
}
</style>

<style scoped>
@keyframes scroll {
    0% {
        transform: translateX(0);
    }

    100% {
        transform: translateX(-50%);
    }
}

.animate-scroll {
    display: flex;
    width: max-content;
    animation: scroll var(--duration) linear infinite;
}
</style>