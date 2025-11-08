<template>
    <div class="w-16 flex flex-col justify-between border-e border-gray-100 bg-white">
        <div>
            <div class="inline-flex size-16 items-center justify-center">
                <span class="grid size-10 place-content-center rounded-lg bg-gray-100 text-xs text-gray-600">
                    L
                </span>
            </div>

            <div class="border-t border-gray-100">
                <div class="px-2">
                    <div class="py-4">
                        <nuxt-link to="/trader/dashboard"
                            class="group relative flex justify-center rounded-sm bg-blue-50 px-2 py-1.5 text-blue-700">
                            <svg xmlns="http://www.w3.org/2000/svg" class="size-5 opacity-75" fill="none"
                                viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                <path stroke-linecap="round" stroke-linejoin="round"
                                    d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
                                <path stroke-linecap="round" stroke-linejoin="round"
                                    d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
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
                                class="group relative flex justify-center rounded-sm px-2 py-1.5 text-gray-500 hover:bg-gray-50 hover:text-gray-700">
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
                            <nuxt-link to="/trader/support"
                                class="group relative flex justify-center rounded-sm px-2 py-1.5 text-gray-500 hover:bg-gray-50 hover:text-gray-700">
                                <svg xmlns="http://www.w3.org/2000/svg" class="size-5 opacity-75" fill="none"
                                    viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                                    <path stroke-linecap="round" stroke-linejoin="round"
                                        d="M9 7h6m0 10v-3m-3 3h.01M9 17h.01M9 14h.01M12 14h.01M15 11h.01M12 11h.01M9 11h.01M7 21h10a2 2 0 002-2V5a2 2 0 00-2-2H7a2 2 0 00-2 2v14a2 2 0 002 2z" />
                                </svg>
                                <span
                                    class="invisible absolute start-full top-1/2 ms-4 -translate-y-1/2 rounded-sm bg-gray-900 px-2 py-1.5 text-xs font-medium text-white group-hover:visible">
                                    Support
                                </span>
                            </nuxt-link>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- Logout Button -->
        <div class="border-t border-gray-100 p-4 mt-auto">
            <button @click="logout"
                class="group relative flex justify-center w-full rounded-sm px-2 py-1.5 text-gray-500 hover:bg-red-50 hover:text-red-600 transition">
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

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useApi } from '~/composables/useApi'

const api = useApi()
const router = useRouter()

const logout = async () => {
    try {
        await api.post('/auth/trader/logout')
        await router.push('/')
    } catch (err) {
        console.error('Logout failed', err)
    }
}
</script>