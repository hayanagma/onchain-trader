<script setup lang="ts">
import { useUserAuthStore } from '~/stores/userAuth'

const userAuth = useUserAuthStore()

async function handleLogout() {
    await userAuth.logout()
    window.location.href = '/'
}
</script>

<template>
    <div
        class="fixed top-0 left-0 h-screen w-16 flex flex-col justify-between border-e border-gray-100 bg-white shadow-lg z-50">
        <div>
            <div class="inline-flex size-16 items-center justify-center">
                <span class="grid size-10 place-content-center rounded-lg bg-gray-100 text-xs text-gray-600">L</span>
            </div>

            <div class="border-t border-gray-100">
                <div class="px-2">
                    <div class="py-4">
                        <nuxt-link to="/trader/dashboard"
                            class="group relative flex justify-center rounded-sm bg-blue-50 px-2 py-1.5 text-blue-700">
                            <svg xmlns="http://www.w3.org/2000/svg" class="size-5 opacity-75" fill="none"
                                viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                <path stroke-linecap="round" stroke-linejoin="round"
                                    d="M3 12l2-2m0 0l7-7 7 7m-9 2v8m4-8v8m4-8h4m-4 0H5" />
                            </svg>
                            <span
                                class="invisible absolute start-full top-1/2 ms-4 -translate-y-1/2 rounded-sm bg-gray-900 px-2 py-1.5 text-xs font-medium text-white group-hover:visible">
                                Dashboard
                            </span>
                        </nuxt-link>
                    </div>

                    <ul class="space-y-1 border-t border-gray-100 pt-4">
                        <li>
                            <nuxt-link to="/trader/profile"
                                class="group relative flex justify-center rounded-sm px-2 py-1.5 text-gray-500 hover:bg-gray-50 hover:text-gray-700 transition">
                                <svg xmlns="http://www.w3.org/2000/svg" class="size-5 opacity-75" fill="none"
                                    viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                    <path stroke-linecap="round" stroke-linejoin="round"
                                        d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                                </svg>
                                <span
                                    class="invisible absolute start-full top-1/2 ms-4 -translate-y-1/2 rounded-sm bg-gray-900 px-2 py-1.5 text-xs font-medium text-white group-hover:visible">
                                    Profile
                                </span>
                            </nuxt-link>
                        </li>

                        <li>
                            <nuxt-link to="/trader/subscription"
                                class="group relative flex justify-center rounded-sm px-2 py-1.5 text-gray-500 hover:bg-gray-50 hover:text-gray-700 transition">
                                <svg xmlns="http://www.w3.org/2000/svg" class="size-5 opacity-75" fill="none"
                                    viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                    <path stroke-linecap="round" stroke-linejoin="round"
                                        d="M3 10h18M5 6h14a2 2 0 012 2v8a2 2 0 01-2 2H5a2 2 0 01-2-2V8a2 2 0 012-2z" />
                                </svg>
                                <span
                                    class="invisible absolute start-full top-1/2 ms-4 -translate-y-1/2 rounded-sm bg-gray-900 px-2 py-1.5 text-xs font-medium text-white group-hover:visible">
                                    Subscriptions
                                </span>
                            </nuxt-link>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="border-t border-gray-100 p-4 flex flex-col gap-2">
            <nuxt-link to="/trader/support"
                class="group relative flex justify-center rounded-sm px-2 py-1.5 text-gray-500 hover:bg-gray-50 hover:text-gray-700 transition">
                <svg xmlns="http://www.w3.org/2000/svg" class="size-5 opacity-75" fill="none" viewBox="0 0 24 24"
                    stroke="currentColor" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round"
                        d="M8 10h8m-8 4h5m-9 4v-9a3 3 0 013-3h10a3 3 0 013 3v6a3 3 0 01-3 3H7l-4 3z" />
                </svg>
                <span
                    class="invisible absolute start-full top-1/2 ms-4 -translate-y-1/2 rounded-sm bg-gray-900 px-2 py-1.5 text-xs font-medium text-white group-hover:visible">
                    Support
                </span>
            </nuxt-link>

            <button @click="handleLogout"
                class="group relative flex justify-center rounded-sm px-2 py-1.5 text-gray-500 hover:bg-red-50 hover:text-red-600 transition">
                <svg xmlns="http://www.w3.org/2000/svg" class="size-5 opacity-75" fill="none" viewBox="0 0 24 24"
                    stroke="currentColor" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round"
                        d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a2 2 0 01-2 2H7a2 2 0 01-2-2V7a2 2 0 012-2h4a2 2 0 012 2v1" />
                </svg>
                <span
                    class="invisible absolute start-full top-1/2 ms-4 -translate-y-1/2 rounded-sm bg-gray-900 px-2 py-1.5 text-xs font-medium text-white group-hover:visible">
                    Logout
                </span>
            </button>
        </div>
    </div>
</template>