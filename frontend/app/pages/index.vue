<template>
  <div class="p-4">
    <h1 class="text-2xl font-bold mb-2">On-chain Trader</h1>
    <p class="mb-4">Welcome to your On-chain Trader.</p>

    <nuxt-link to="/login" class="text-blue-500 underline mb-6 block">
      Go to Login
    </nuxt-link>

    <mailForm
      endpoint="/public/mail/contact"
      title="Contact Us"
    />

    <newsletterSubscribe />
  </div>
</template>