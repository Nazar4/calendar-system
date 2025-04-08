<template>
  <div class="p-4 border rounded shadow bg-white hover:shadow-lg hover:bg-gray-100 cursor-pointer">
    <h3 class="font-semibold text-lg mb-2">{{ title }}</h3>

    <p class="text-sm text-gray-600">{{ formatDate(startTime) }} &mdash; {{ formatDate(endTime) }}</p>

    <p
      class="mt-2 inline-block px-2 py-1 text-xs rounded"
      :class="{
        'bg-gray-300 text-gray-800': status === 'PAST',
        'bg-green-300 text-green-800': status === 'CURRENT',
        'bg-blue-300 text-blue-800': status === 'UPCOMING'
      }"
    >
      {{ status }}
    </p>
  </div>
</template>

<script setup>
import { formatDate } from '../helpers/utils.js'

defineProps({
  title: { type: String, required: true },
  startTime: { type: Date, required: true },
  endTime: { type: Date, required: true },
  status: { type: String, required: true }
});
</script>

<style scoped></style>
