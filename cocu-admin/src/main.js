import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from '../node_modules/element-ui/lib/locale/lang/en'
import  './assets/global.css';
// import axios from 'axios';
import request from "@/utils/request";
import VueRouter from "vue-router";
import router from './router';
import store from './store'
import { saveTokenFromUrl, getToken } from './utils/auth';
import { fetchUserData } from './utils/request';

// Vue.prototype.request = request;

Vue.prototype.$axios = request;
Vue.prototype.$httpUrl = 'http://localhost:8082';
Vue.use(ElementUI, {locale});
Vue.use(VueRouter);

Vue.config.productionTip = false


saveTokenFromUrl();

async function initialize() {
  const token = getToken();
  if (token) {
    try {
      const response = await fetchUserData();
      const userData = response.data;

      // Store user data in sessionStorage
      sessionStorage.setItem("curUser", JSON.stringify(userData.user));

      // Commit user data to Vuex store
      store.commit("setMenu", userData.menuList);

      new Vue({
        router,
        store,
        render: h => h(App)
      }).$mount('#app');
    } catch (error) {
      console.error('Failed to fetch user data:', error);
      // Redirect to login if fetching user data fails
      router.push('/index');
    }
  } else {
    // Redirect to login if no token is found
    // router.push('/login');
  }
}

initialize();


new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
