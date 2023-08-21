import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'weclome',
    component: () => import('../views/WeclomeView.vue'),
    children: [
      {
        path: '',
        name: 'login',
        component: () => import('../views/weclome/LoginPage.vue'),
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
