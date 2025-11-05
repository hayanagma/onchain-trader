<template>
    <footer class="bg-white dark:bg-gray-900">
        <div class="mx-auto max-w-7xl px-4 pt-16 pb-8 sm:px-6 lg:px-8 lg:pt-24">
            <div class="text-center">
                <h2 class="text-3xl font-extrabold text-gray-900 sm:text-5xl dark:text-white">
                    Customise Your Product
                </h2>

                <p class="mx-auto mt-4 max-w-sm text-gray-500 dark:text-gray-400">
                    Lorem ipsum dolor sit amet consectetur adipisicing elit. Cum maiores ipsum eos temporibus ea nihil.
                </p>

                <a href="#"
                    class="mt-8 inline-block rounded-full border border-indigo-600 px-12 py-3 text-sm font-medium text-white hover:bg-indigo-600 hover:text-white focus:ring-2 focus:outline-hidden">
                    Get Started
                </a>
            </div>

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
                    <li>
                        <a href="#" class="text-gray-500 transition hover:opacity-75 dark:text-gray-400">
                            Cookies
                        </a>
                    </li>
                </ul>

                <ul class="mt-8 flex justify-center gap-6 sm:mt-0 lg:justify-end">
                    <li>
                        <a href="#" target="_blank" rel="noreferrer"
                            class="text-gray-700 transition hover:opacity-75 dark:text-gray-200">
                            <span class="sr-only">Facebook</span>
                            <svg class="size-6" fill="currentColor" viewBox="0 0 24 24">
                                <path fill-rule="evenodd"
                                    d="M22 12c0-5.523-4.477-10-10-10S2 6.477 2 12c0 4.991 3.657 9.128 8.438 9.878v-6.987h-2.54V12h2.54V9.797c0-2.506 1.492-3.89 3.777-3.89 1.094 0 2.238.195 2.238.195v2.46h-1.26c-1.243 0-1.63.771-1.63 1.562V12h2.773l-.443 2.89h-2.33v6.988C18.343 21.128 22 16.991 22 12z"
                                    clip-rule="evenodd" />
                            </svg>
                        </a>
                    </li>

                    <li>
                        <a href="#" target="_blank" rel="noreferrer"
                            class="text-gray-700 transition hover:opacity-75 dark:text-gray-200">
                            <span class="sr-only">Instagram</span>
                            <svg class="size-6" fill="currentColor" viewBox="0 0 24 24">
                                <path fill-rule="evenodd" d="M12.315 2c2.43 0 2.784.013 3.808.06..."
                                    clip-rule="evenodd" />
                            </svg>
                        </a>
                    </li>

                    <li>
                        <a href="#" target="_blank" rel="noreferrer"
                            class="text-gray-700 transition hover:opacity-75 dark:text-gray-200">
                            <span class="sr-only">Twitter</span>
                            <svg class="size-6" fill="currentColor" viewBox="0 0 24 24">
                                <path d="M8.29 20.251c7.547 0 11.675-6.253..." />
                            </svg>
                        </a>
                    </li>

                    <li>
                        <a href="#" target="_blank" rel="noreferrer"
                            class="text-gray-700 transition hover:opacity-75 dark:text-gray-200">
                            <span class="sr-only">GitHub</span>
                            <svg class="size-6" fill="currentColor" viewBox="0 0 24 24">
                                <path fill-rule="evenodd" d="M12 2C6.477 2 2 6.484..." clip-rule="evenodd" />
                            </svg>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </footer>
</template>