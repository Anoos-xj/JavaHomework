<template>
  <nav class="bg-blue-600 text-white p-4">
    <div class="max-w-6xl mx-auto flex gap-4">
      <RouterLink to="/" class="hover:underline">客户端</RouterLink>
      <RouterLink to="/server" class="hover:underline">服务端</RouterLink>
    </div>
  </nav>
  <main class="max-w-6xl mx-auto p-6">
    <RouterView />
  </main>
</template>

<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>
