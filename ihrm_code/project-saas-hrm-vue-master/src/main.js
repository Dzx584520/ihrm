import Vue from 'vue'
import 'normalize.css/normalize.css'// A modern alternative to CSS resets
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/styles/index.scss' // global css
import App from './App'
import router from './router'
import store from './store'
import i18n from './lang' // Internationalization
import './icons' // icon
import './errorLog'// error log
import * as filters from './filters' // global filters
// font-awesome
import 'font-awesome/css/font-awesome.css'
import './mock' // simulation data
/*
* 注册 - 业务模块
*/
import dashboard from '@/module-dashboard/' // 面板
import demo from '@/module-demo/' // 面板
import saasClient from '@/module-saas-client/' // 面板
import departments from '@/module-departments/' // 部门管理
import employees from '@/module-employees' // 员工管理
import role from '@/module-settings' // 权限管理

import tools from './utils/common.js'
Vue.prototype.$tools = tools

Vue.use(tools)
Vue.use(dashboard, store)
Vue.use(demo, store)
Vue.use(saasClient,store)
Vue.use(departments,store)
Vue.use(employees,store)
Vue.use(role,store)



/*
* 注册 - 组件
*/

// 饿了么
Vue.use(Element, {
  size: 'medium', // set element-ui default size
  i18n: (key, value) => i18n.t(key, value)
})
// 过滤器
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

Vue.config.productionTip = false

/* eslint-disable */
new Vue({
  el: '#app',
  router,
  store,
  i18n,
  template: '<App/>',
  components: { App }
})

function formatDate(date, fmt) {
  date = new Date(date);
  if (typeof(fmt) === "undefined") {
      fmt = "yyyy-MM-dd HH:mm:ss";
  }
  if (/(y+)/.test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  let o = {
      'M+': date.getMonth() + 1,
      'd+': date.getDate(),
      'H+': date.getHours(),
      'm+': date.getMinutes(),
      's+': date.getSeconds()
  }
  for (let k in o) {
      if (new RegExp(`(${k})`).test(fmt)) {
          let str = o[k] + ''
          fmt = fmt.replace(RegExp.$1, RegExp.$1.length === 1 ? str : ('00' + str).substr(str.length));
      }
  }
  return fmt
};
  
//定义一个时间过滤器
Vue.filter("FormatDate", function(date, fmt) {
  return formatDate(date, fmt);
});