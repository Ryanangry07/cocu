import { createApp } from 'vue'
import router from './router/index'
import App from './App.vue'
//element plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//vue3's pinia (similiar to vue2's vuex)
import piniaPersist from 'pinia-plugin-persist'
import { createPinia } from 'pinia'
import myConfirm from './utils/myConfirm'

//create pinia
const pinia = createPinia()
pinia.use(piniaPersist)
const app = createApp(App);
app.use(ElementPlus).use(router).use(pinia).mount('#app')

//register icon global
for (const [key, component] of Object.entries(ElementPlusIconsVue)){
    app.component(key, component)
}

// global registration
app.config.globalProperties.$myConfirm = myConfirm