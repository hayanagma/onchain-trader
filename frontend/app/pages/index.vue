<script setup lang="ts">
import { ref } from 'vue'
import SiteHeader from '~/components/layout/SiteHeader.vue'
import MailForm from '~/components/form/MailForm.vue'
import ConnectWalletModal from '~/components/modal/ConnectWalletModal.vue'
import SiteFooter from '~/components/layout/SiteFooter.vue'
import CoinBanner from '~/components/widget/CoinBanner.vue'

const showModal = ref(false)
</script>

<template>
  <SiteHeader @open-connect="showModal = true" />

  <ConnectWalletModal v-if="showModal" mode="create" @close="showModal = false" />

  <!-- Hero Section -->
  <section class="bg-white lg:grid lg:h-screen lg:place-content-center dark:bg-gray-900">
    <div class="mx-auto w-screen max-w-7xl px-4 py-16 sm:px-6 sm:py-24 lg:px-8 lg:py-32">
      <div class="mx-auto max-w-prose text-center">
        <h1 class="text-4xl font-bold text-gray-900 sm:text-5xl dark:text-white">
          Understand user flow and <strong class="text-indigo-600">increase</strong> conversions
        </h1>

        <p class="mt-4 text-base text-pretty text-gray-700 sm:text-lg/relaxed dark:text-gray-200">
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eaque, nisi.
          Natus, provident accusamus impedit minima harum corporis iusto.
        </p>

        <div class="mt-4 flex justify-center gap-4 sm:mt-6">
          <button @click="showModal = true"
            class="inline-block rounded border border-indigo-600 bg-indigo-600 px-5 py-3 font-medium text-white shadow-sm transition-colors hover:bg-indigo-700">
            Get Started
          </button>

          <a href="#"
            class="inline-block rounded border border-gray-200 px-5 py-3 font-medium text-gray-700 shadow-sm transition-colors hover:bg-gray-50 hover:text-gray-900 dark:border-gray-700 dark:text-gray-200 dark:hover:bg-gray-800 dark:hover:text-white">
            Learn More
          </a>
        </div>
      </div>
    </div>
  </section>

  <!-- Coin Banner -->
  <section>
    <CoinBanner />
  </section>

  <section class="bg-gray-950 border-t border-gray-800 py-16">
    <div class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
      <div class="mx-auto max-w-2xl text-center">
        <h2 class="text-4xl font-bold text-white sm:text-5xl">Features for growth</h2>
        <p class="mt-4 text-lg text-gray-400">
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Veritatis tenetur, nemo quam
          voluptas sunt impedit dolorem asperiores aliquid doloribus fugit.
        </p>
      </div>

      <div class="mt-12 grid grid-cols-1 gap-8 sm:grid-cols-2 lg:grid-cols-3">
        <div class="rounded-2xl border border-gray-800 bg-gray-900/70 p-6 hover:bg-gray-900 transition">
          <div class="inline-flex rounded-lg bg-gray-800 p-3 text-indigo-400">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
              stroke="currentColor" class="size-6">
              <path stroke-linecap="round" stroke-linejoin="round"
                d="m3.75 13.5 10.5-11.25L12 10.5h8.25L9.75 21.75 12 13.5H3.75Z" />
            </svg>
          </div>
          <h3 class="mt-4 text-lg font-semibold text-white">High performance</h3>
          <p class="mt-2 text-sm text-gray-400">
            Lightning-quick load times optimized for every device.
          </p>
        </div>

        <div class="rounded-2xl border border-gray-800 bg-gray-900/70 p-6 hover:bg-gray-900 transition">
          <div class="inline-flex rounded-lg bg-gray-800 p-3 text-indigo-400">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
              stroke="currentColor" class="size-6">
              <path stroke-linecap="round" stroke-linejoin="round"
                d="M16.5 10.5V6.75a4.5 4.5 0 1 0-9 0v3.75m-.75 11.25h10.5a2.25 2.25 0 0 0 2.25-2.25v-6.75a2.25 2.25 0 0 0-2.25-2.25H6.75a2.25 2.25 0 0 0-2.25 2.25v6.75a2.25 2.25 0 0 0 2.25 2.25Z" />
            </svg>
          </div>
          <h3 class="mt-4 text-lg font-semibold text-white">Enterprise security</h3>
          <p class="mt-2 text-sm text-gray-400">
            Enterprise-grade security built into every layer.
          </p>
        </div>

        <div class="rounded-2xl border border-gray-800 bg-gray-900/70 p-6 hover:bg-gray-900 transition">
          <div class="inline-flex rounded-lg bg-gray-800 p-3 text-indigo-400">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
              stroke="currentColor" class="size-6">
              <path stroke-linecap="round" stroke-linejoin="round"
                d="M6 13.5V3.75m0 9.75a1.5 1.5 0 0 1 0 3m0-3a1.5 1.5 0 0 0 0 3m0 3.75V16.5m12-3V3.75m0 9.75a1.5 1.5 0 0 1 0 3m0-3a1.5 1.5 0 0 0 0 3m0 3.75V16.5m-6-9V3.75m0 3.75a1.5 1.5 0 0 1 0 3m0-3a1.5 1.5 0 0 0 0 3m0 9.75V10.5" />
            </svg>
          </div>
          <h3 class="mt-4 text-lg font-semibold text-white">Highly configurable</h3>
          <p class="mt-2 text-sm text-gray-400">
            Adapt every aspect to match your brand and needs.
          </p>
        </div>
      </div>
    </div>
  </section>


  <!-- Contact Form -->
  <section>
    <MailForm endpoint="/public/mail/contact" title="Contact Us" />
  </section>

  <!-- Footer -->
  <SiteFooter />
</template>