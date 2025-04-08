<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ROUTE_NAME_EVENT_ADD, ROUTE_NAME_EVENT_DETAILS } from '../router/index.js'
import EventCard from '../components/EventCard.vue'
import { FIRST_MONTH_ID, LAST_MONTH_ID, MONTH_NAMES } from '../helpers/constants.js'
import { getAllEvents } from '../services/events.js'

const router = useRouter();

const allEvents = ref([]);
const selectedMonth = ref(new Date().getMonth());
const selectedYear = ref(new Date().getFullYear());

async function fetchEvents() {
  try {
    const data = await getAllEvents();
    allEvents.value = data.map((event) => ({
      ...event,
      startTime: new Date(event.startTime),
      endTime: new Date(event.endTime)
    }));
  } catch (err) {
    console.error('Failed to load events:', err);
  }
}

fetchEvents();

const filteredEvents = computed(() => {
  return allEvents.value.filter((event) => {
    const start = event.startTime;
    return start.getMonth() === selectedMonth.value && start.getFullYear() === selectedYear.value;
  });
});

function getMonthLabel(monthIndex) {
  return `${MONTH_NAMES[monthIndex]} ${selectedYear.value}`;
}

function goPrevMonth() {
  if (selectedMonth.value === FIRST_MONTH_ID) {
    selectedMonth.value = LAST_MONTH_ID;
    selectedYear.value--;
  } else {
    selectedMonth.value--;
  }
}
function goNextMonth() {
  if (selectedMonth.value === LAST_MONTH_ID) {
    selectedMonth.value = FIRST_MONTH_ID;
    selectedYear.value++;
  } else {
    selectedMonth.value++;
  }
}

function getEventState(event) {
  const now = new Date();
  const { startTime, endTime } = event;
  if (endTime < now) return 'PAST';
  if (startTime <= now && now <= endTime) return 'CURRENT';
  return 'UPCOMING';
}

function goToEventDetails(eventId) {
  router.push({ name: ROUTE_NAME_EVENT_DETAILS, params: { eventId } });
}

function goAddEvent() {
  router.push({ name: ROUTE_NAME_EVENT_ADD });
}
</script>

<template>
  <div class="flex flex-col gap-4">
    <div class="flex items-center justify-between">
      <button @click="goPrevMonth" class="text-xl px-4 py-2 bg-gray-200 rounded hover:bg-gray-300 hover:cursor-pointer">
        &larr; Previous
      </button>

      <h2 class="text-2xl font-bold">
        {{ getMonthLabel(selectedMonth) }}
      </h2>

      <button @click="goNextMonth" class="text-xl px-4 py-2 bg-gray-200 rounded hover:bg-gray-300 hover:cursor-pointer">Next &rarr;</button>
    </div>

    <div v-if="filteredEvents.length === 0" class="text-center mt-8">
      <p class="text-gray-500 text-lg">There are no events for this month.</p>
    </div>

    <div v-else class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 mt-4">
      <EventCard
        v-for="event in filteredEvents"
        :key="event.id"
        :status="getEventState(event)"
        :title="event.title"
        :startTime="event.startTime"
        :endTime="event.endTime"
        @click="goToEventDetails(event.id)"
      />
    </div>

    <div class="flex justify-end mt-6">
      <button class="text-xl px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 hover:cursor-pointer" @click="goAddEvent">
        + Add Event
      </button>
    </div>
  </div>
</template>

<style scoped></style>
