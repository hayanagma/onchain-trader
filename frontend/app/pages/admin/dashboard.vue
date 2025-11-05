<template>
  <div class="p-4">
    <h1 class="text-2xl font-bold">Dashboard</h1>
    <p>Welcome to your dashboard.</p>

    <nuxt-link to="/admin/traders" class="text-blue-500 underline block mb-1">
      Go to Traders
    </nuxt-link>
    <br />

    <nuxt-link
      to="/admin/currencies"
      class="text-blue-500 underline block mb-1"
    >
      Go to Currencies
    </nuxt-link>
    <br />
    <nuxt-link
      to="/admin/newsletters"
      class="text-blue-500 underline block mb-1"
    >
      Go to Newsletters
    </nuxt-link>
    <br />
    <nuxt-link to="/admin/updates" class="text-blue-500 underline block mb-1">
      Go to Updates
    </nuxt-link>
  </div>
</template>
