import { createApp } from 'vue'
import App from './App.vue'
import './style.css'
import 'element-plus/dist/index.css'
// import 'element-plus/theme-chalk/dark/css-vars.css'
import router from './routers'

createApp(App).use(router).mount('#app')
