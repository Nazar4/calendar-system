import api from './api.js';

const EVENTS_API = 'events';

export async function getAllEvents() {
  return (await api.get(`${EVENTS_API}`)).data;
}

export async function getEventById(eventId) {
  return (await api.get(`${EVENTS_API}/${eventId}`)).data;
}

export async function createEvent(eventData) {
  return (await api.post(`${EVENTS_API}`, eventData)).data;
}

export async function updateEvent(eventId, eventData) {
  return (await api.put(`${EVENTS_API}/${eventId}`, eventData)).data;
}

export async function removeEvent(eventId) {
  return (await api.delete(`${EVENTS_API}/${eventId}`)).data;
}
