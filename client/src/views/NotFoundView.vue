<script setup>
import { ROUTE_NAME_CALENDAR } from '../router'
import { RouterLink } from 'vue-router'
</script>

<template>
  <div class="flex flex-col items-center justify-center min-h-screen bg-white text-gray-800">
    <h1 class="text-6xl font-bold">404</h1>
    <p class="text-xl mt-2">Oops! The page you're looking for doesn't exist.</p>
    <RouterLink
      :to="{ name: ROUTE_NAME_CALENDAR }"
      class="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
    >
      Back to Calendar
    </RouterLink>
  </div>
</template>

<style scoped></style>
