<template>
  <div class="p-4">
    <h1 class="text-2xl font-bold">Dashboard</h1>
    <p>Welcome to your dashboard.</p>

    <nuxt-link to="/trader/profile" class="text-blue-500 underline">
      Go to Profile
    </nuxt-link>
    <br />
    <nuxt-link to="/trader/support" class="text-blue-500 underline">
      Go to Support
    </nuxt-link>
    <newsletterSubscribe />
  </div>
</template>
