import Vue from 'vue'
import Router from 'vue-router'
import home from '@/views/home'
import BillClassify from "../components/BillClassify";
import BillUpload from "../components/BillUpload";
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: home
    },
    {
      path: '/billClassify',
      name: 'BillClassify',
      component: BillClassify
    },
    {
      path: '/uploadBill',
      name: 'uploadBill',
      component: BillUpload
    }
  ]
})
