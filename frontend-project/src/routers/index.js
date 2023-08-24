import { createRouter, createWebHistory } from 'vue-router'
import { unauthorized } from '../net'

const routes = [
  {
    path: '/',
    name: 'weclome',
    component: () => import('../views/WeclomeView.vue'),
    children: [
      {
        path: '',
        name: 'weclome-login',
        component: () => import('../views/weclome/LoginPage.vue'),
      },
      {
        path: 'register',
        name: 'weclome-register',
        component: () => import('../views/weclome/RegisterPage.vue'),
      },
      {
        path: 'forget',
        name: 'weclome-forget',
        component: () => import('../views/weclome/ForgetPwd.vue'),
      },
    ],
  },
  {
    path: '/center',
    name: 'PersonalCenter',
    component: () => import('../views/PersonalCenter.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

router.beforeEach((to, from, next) => {
  const isUnAuth = unauthorized()
  if (to.name.startsWith('weclome-') && !isUnAuth) {
    next('/center')
  } else if (to.name === 'PersonalCenter' && isUnAuth) {
    next('/')
  } else {
    next()
  }
})

export default router
