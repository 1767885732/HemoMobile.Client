import { defineConfig } from 'vite'
import uni from '@dcloudio/vite-plugin-uni'

export default defineConfig({
  plugins: [uni()],
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@import "@/styles/variables.scss";`
      }
    }
  },
  resolve: {
    alias: {
      'vue-router': 'vue-router/dist/vue-router.esm-bundler.js'
    }
  },
  optimizeDeps: {
    include: ['vue-router']
  }
})