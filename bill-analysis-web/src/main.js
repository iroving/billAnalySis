import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import '@/request/http'
import 'babel-polyfill'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/iconfont/iconfont.css';

// 阻止vue的一些消息
Vue.config.productionTip = false;

Vue.use(ElementUI);

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
