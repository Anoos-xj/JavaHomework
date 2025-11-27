import { createRouter, createWebHistory } from 'vue-router'
import ClientForm from '@/views/ClientForm.vue'
import ServerRecords from '@/views/ServerRecords.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', name: 'client', component: ClientForm },
    { path: '/server', name: 'server', component: ServerRecords }
  ],
})

export default router
