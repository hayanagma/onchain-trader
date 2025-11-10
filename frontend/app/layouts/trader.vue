<template>
    <div class="relative min-h-screen bg-gray-950 text-gray-100 flex">
        <TraderSideMenu />
        <main class="flex-1 relative ml-16">
            <NetworkSelector />
            <slot />
        </main>
    </div>
</template>

<script setup lang="ts">
import TraderSideMenu from '~/components/layout/TraderSideMenu.vue'
import NetworkSelector from '~/components/layout/NetworkSelector.vue'
</script>