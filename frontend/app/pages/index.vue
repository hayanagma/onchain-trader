<script setup lang="ts">
import { ref } from 'vue'
import SiteHeader from '~/components/layout/SiteHeader.vue'
import MailForm from '~/components/form/MailForm.vue'
import ConnectWalletModal from '~/components/modal/ConnectWalletModal.vue'
import SiteFooter from '~/components/layout/SiteFooter.vue'

const showModal = ref(false)
</script>

<template>
  <SiteHeader @open-connect="showModal = true" />

  <ConnectWalletModal v-if="showModal" mode="create" @close="showModal = false" />

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

  <MailForm endpoint="/public/mail/contact" title="Contact Us" />
  <SiteFooter />
</template>