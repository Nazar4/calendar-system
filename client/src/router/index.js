import { createRouter, createWebHistory } from 'vue-router';

export const ROUTE_NAME_CALENDAR = 'Calendar';
export const ROUTE_NAME_EVENT_DETAILS = 'Event Details';
export const ROUTE_NAME_EVENT_ADD = 'Event Add';
export const ROUTE_NAME_EVENT_EDIT = 'Event Edit';

const routes = [
  {
    path: '/',
    name: ROUTE_NAME_CALENDAR,
    component: () => import('../views/CalendarView.vue'),
    alias: ['/calendar', '/home']
  },
  {
    path: '/events',
    children: [
      {
        path: '',
        redirect: { name: ROUTE_NAME_EVENT_ADD }
      },
      {
        path: ':eventId',
        name: ROUTE_NAME_EVENT_DETAILS,
        component: () => import('../views/EventDetailsView.vue')
      },
      {
        path: 'new',
        name: ROUTE_NAME_EVENT_ADD,
        component: () => import('../views/EventFormView.vue')
      },
      {
        path: ':eventId/edit',
        name: ROUTE_NAME_EVENT_EDIT,
        component: () => import('../views/EventFormView.vue')
      }
    ]
  },
  // Catch every other unsupported route
  {
    path: '/:pathMatch(.*)*',
    name: 'Not Found',
    component: () => import('../views/NotFoundView.vue')
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, _from, savedPosition) {
    if (to.hash) {
      return { el: to.hash, behavior: 'smooth' };
    } else if (savedPosition) {
      return savedPosition;
    }
    return { top: 0 };
  }
});

export default router;
