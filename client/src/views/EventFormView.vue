<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { eventSchema } from '../helpers/schemas.js'
import { getEventById, createEvent, updateEvent } from '../services/events.js'
import { ROUTE_NAME_CALENDAR, ROUTE_NAME_EVENT_DETAILS } from '../router/index.js'

const route = useRoute();
const router = useRouter();

const eventId = route.params.eventId;
const isEditMode = computed(() => Boolean(eventId));

const title = ref('');
const description = ref('');
const startTime = ref('');
const endTime = ref('');
const locationField = ref('');
const formError = ref('');

const titleError = computed(() => {
  return !title.value.trim() ? 'Title is required' : null;
});
const startTimeError = computed(() => {
  return !startTime.value.trim() ? 'Start time is required' : null;
});
const endTimeError = computed(() => {
  return !endTime.value.trim() ? 'End time is required' : null;
});

const isFormInvalid = computed(() => {
  return !!(titleError.value || startTimeError.value || endTimeError.value);
});

async function loadEventData() {
  if (isEditMode.value) {
    try {
      const existingEvent = await getEventById(eventId);
      title.value = existingEvent.title;
      description.value = existingEvent.description ?? '';
      startTime.value = existingEvent.startTime;
      endTime.value = existingEvent.endTime;
      locationField.value = existingEvent.location ?? '';
    } catch (err) {
      console.error('Failed to load event:', err);
      formError.value = 'Could not load the event for editing.';
    }
  }
}

loadEventData();

async function handleSubmit() {
  formError.value = '';

  const formData = {
    title: title.value.trim(),
    description: description.value.trim(),
    startTime: startTime.value.trim(),
    endTime: endTime.value.trim(),
    location: locationField.value.trim()
  };

  try {
    eventSchema.parse(formData);
  } catch (err) {
    formError.value = err.errors[0].message;
    return;
  }

  try {
    if (isEditMode.value) {
      await updateEvent(eventId, formData);
      router.push({ name: ROUTE_NAME_EVENT_DETAILS, params: { eventId } });
    } else {
      await createEvent(formData);
      router.push({ name: ROUTE_NAME_CALENDAR });
    }
  } catch (err) {
    console.error('Failed to save event:', err);
    formError.value = 'Error saving the event. Please try again.';
  }
}

const headingText = computed(() => (isEditMode.value ? 'Edit Event' : 'Add Event'));
const submitButtonText = computed(() => (isEditMode.value ? 'Update' : 'Create'));
</script>

<template>
  <div class="max-w-xl mx-auto mt-8 bg-white border-2 border-gray-300 rounded p-6 shadow-md">
    <h1 class="text-2xl font-bold mb-4">{{ headingText }}</h1>

    <div v-if="formError" class="text-red-600 mb-4">
      {{ formError }}
    </div>

    <form @submit.prevent="handleSubmit" class="space-y-6">
      <div>
        <label for="title" class="block text-sm font-medium text-gray-700 mb-1">Title</label>
        <input
          id="title"
          type="text"
          v-model="title"
          class="border rounded w-full p-2"
          :class="{
            'border-gray-300': !titleError,
            'border-red-500 bg-red-50': titleError
          }"
          placeholder="Event title"
          required
        />
        <div v-if="titleError" class="text-sm text-red-500 mt-1">
          {{ titleError }}
        </div>
      </div>

      <div>
        <label for="description" class="block text-sm font-medium text-gray-700 mb-1">Description</label>
        <textarea
          id="description"
          v-model="description"
          class="border border-gray-300 rounded w-full p-2"
          placeholder="Event description..."
          rows="4"
        ></textarea>
      </div>

      <div>
        <label for="startTime" class="block text-sm font-medium text-gray-700 mb-1">Start Date & Time</label>
        <input
          id="startTime"
          type="datetime-local"
          v-model="startTime"
          class="border rounded w-full p-2"
          :class="{
            'border-gray-300': !startTimeError,
            'border-red-500 bg-red-50': startTimeError
          }"
          required
        />
        <div v-if="startTimeError" class="text-sm text-red-500 mt-1">
          {{ startTimeError }}
        </div>
      </div>

      <div>
        <label for="endTime" class="block text-sm font-medium text-gray-700 mb-1">End Date & Time</label>
        <input
          id="endTime"
          type="datetime-local"
          v-model="endTime"
          class="border rounded w-full p-2"
          :class="{
            'border-gray-300': !endTimeError,
            'border-red-500 bg-red-50': endTimeError
          }"
          required
        />
        <div v-if="endTimeError" class="text-sm text-red-500 mt-1">
          {{ endTimeError }}
        </div>
      </div>

      <div>
        <label for="location" class="block text-sm font-medium text-gray-700 mb-1">Location</label>
        <input
          id="location"
          type="text"
          v-model="locationField"
          class="border border-gray-300 rounded w-full p-2"
          placeholder="Optional location"
        />
      </div>

      <div class="flex justify-end">
        <button
          type="submit"
          :disabled="isFormInvalid"
          class="px-4 py-2 rounded text-white bg-blue-600 hover:bg-blue-700 cursor-pointer disabled:bg-gray-400 disabled:cursor-not-allowed"
        >
          {{ submitButtonText }}
        </button>
      </div>
    </form>
  </div>
</template>
