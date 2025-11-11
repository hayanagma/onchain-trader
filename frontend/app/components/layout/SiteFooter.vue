<script setup lang="ts">
import NewsletterSubscribe from '~/components/widget/NewsletterSubscribe.vue'
</script>

<template>
    <footer class="bg-white dark:bg-gray-900">
        <div class="mx-auto max-w-7xl px-4 pt-16 pb-8 sm:px-6 lg:px-8 lg:pt-24">
            
            <NewsletterSubscribe />

            <div
                class="mt-16 border-t border-gray-100 pt-8 sm:flex sm:items-center sm:justify-between lg:mt-24 dark:border-gray-800">
                <ul class="flex flex-wrap justify-center gap-4 text-xs lg:justify-end">
                    <li>
                        <NuxtLink to="/terms" class="text-gray-500 transition hover:opacity-75 dark:text-gray-400">
                            Terms &amp; Conditions
                        </NuxtLink>
                    </li>
                    <li>
                        <NuxtLink to="/privacy" class="text-gray-500 transition hover:opacity-75 dark:text-gray-400">
                            Privacy Policy
                        </NuxtLink>
                    </li>
                </ul>

                <ul class="mt-8 flex justify-center gap-6 sm:mt-0 lg:justify-end">
                    <li>
                        <a href="https://discord.gg/7Ad4GGbn" target="_blank" rel="noreferrer"
                            class="text-gray-700 transition hover:opacity-75 dark:text-gray-200">
                            <span class="sr-only">Discord</span>
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 245 240" fill="currentColor"
                                class="size-6">
                                <path
                                    d="M104.4 104.9c-5.7 0-10.2 5-10.2 11.1s4.6 11.1 10.2 11.1c5.7 0 10.2-5 10.2-11.1.1-6.1-4.5-11.1-10.2-11.1zm36.2 0c-5.7 0-10.2 5-10.2 11.1s4.6 11.1 10.2 11.1c5.7 0 10.2-5 10.2-11.1s-4.5-11.1-10.2-11.1z" />
                                <path
                                    d="M189.5 20h-134C44.2 20 36 28.2 36 38.5v163.1c0 10.3 8.2 18.5 18.5 18.5h114.7l-5.4-18.8 13 12 12.3 11.3 21.9 19.4V38.5c0-10.3-8.2-18.5-18.5-18.5zM163 163s-4.3-5.1-7.8-9.6c15.5-4.4 21.4-14.1 21.4-14.1-4.8 3.1-9.3 5.3-13.4 6.8-5.8 2.4-11.3 3.9-16.7 4.8-11 2.1-21 1.5-29.7-.1-6.5-1.2-12.1-2.9-16.8-4.8-2.6-1-5.4-2.2-8.2-3.8-.3-.2-.6-.3-.9-.5-.2-.1-.3-.2-.4-.3-1.8-1-2.8-1.7-2.8-1.7s5.6 9.3 20.4 13.8c-3.4 4.5-7.8 9.8-7.8 9.8-25.8-.8-35.6-17.7-35.6-17.7 0-37.6 16.8-68.1 16.8-68.1 16.8-12.6 32.8-12.2 32.8-12.2l1.2 1.4c-21 6-30.6 15.2-30.6 15.2s2.6-1.4 7-3.3c12.7-5.6 22.8-7.1 27-7.5.7-.1 1.3-.2 2-.2 7.1-.9 15.2-1.2 23.6-.2 11.1 1.3 23 4.6 35.1 11.3 0 0-9.2-8.8-28.9-14.8l1.7-1.9s16-0.4 32.8 12.2c0 0 16.8 30.5 16.8 68.1 0-.1-9.8 16.8-35.7 17.6z" />
                            </svg>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </footer>
</template>