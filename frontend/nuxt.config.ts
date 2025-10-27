export default defineNuxtConfig({
  compatibilityDate: '2025-10-24',
  devtools: { enabled: false },

   modules: ['@pinia/nuxt'],
   
  runtimeConfig: {
    public: {
      apiBase: process.env.NUXT_PUBLIC_API_BASE
    }
  }
})