<template>
  <div class="p-4 max-w-2xl mx-auto">
    <h1 class="text-2xl font-bold mb-4">Trader Support</h1>

    <mailForm
      endpoint="http://localhost:8080/api/public/mail/support"
      title="Contact Support"
    />
  </div>
</template>