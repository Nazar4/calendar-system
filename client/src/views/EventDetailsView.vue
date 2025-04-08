<script setup>
import { ref, watch } from 'vue'
import { RouterLink, useRoute, useRouter } from 'vue-router'
import { getEventById, removeEvent } from '../services/events.js'
import { ROUTE_NAME_CALENDAR, ROUTE_NAME_EVENT_EDIT } from '../router'
import { formatDate } from '../helpers/utils.js'

const route = useRoute();
const router = useRouter();
const eventId = route.params.eventId;

const event = ref(null);
const error = ref(null);

async function fetchEventData() {
  try {
    const data = await getEventById(eventId);
    event.value = data;
  } catch (err) {
    console.error('Failed to load event:', err);
    error.value = 'Failed to load event details.';
  }
}

watch(() => route.params.eventId, fetchEventData, { immediate: true });

async function deleteEvent() {
  if (!event.value) return;
  try {
    await removeEvent(event.value.id);
    router.push({ name: ROUTE_NAME_CALENDAR });
  } catch (err) {
    console.error('Failed to delete event:', err);
  }
}

function editEvent() {
  router.push({ name: ROUTE_NAME_EVENT_EDIT, params: { eventId } });
}
</script>

<template>
  <div class="mx-auto max-w-7xl mt-8 px-4">
    <div class="mb-6">
      <RouterLink
        :to="{ name: ROUTE_NAME_CALENDAR }"
        class="bg-gray-200 text-gray-800 px-4 py-2 rounded hover:bg-gray-300"
      >
        &larr; Back to Calendar
      </RouterLink>
    </div>

    <div v-if="error" class="text-center text-red-600">
      {{ error }}
    </div>

    <div v-else-if="event" class="bg-white p-6 rounded border-2 border-gray-300 shadow-lg space-y-8">
      <h2 class="text-2xl font-bold">
        {{ event.title }}
      </h2>

      <p class="text-lg">
        <span class="font-semibold">Description:</span>
        {{ event.description || 'N/A' }}
      </p>

      <p class="text-lg">
        <span class="font-semibold">Start time:</span>
        {{ formatDate(new Date(event.startTime)) }}
      </p>

      <p class="text-lg">
        <span class="font-semibold">End time:</span>
        {{ formatDate(new Date(event.endTime)) }}
      </p>

      <p class="text-lg">
        <span class="font-semibold">Location:</span>
        {{ event.location || 'N/A' }}
      </p>

      <div class="flex justify-end space-x-4 pt-4">
        <button
          class="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700 hover:cursor-pointer"
          @click="deleteEvent"
        >
          Delete event
        </button>
        <button
          class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 hover:cursor-pointer"
          @click="editEvent"
        >
          Edit event
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
