import Mock from 'mockjs'
import TableAPI from './table'
import ProfileAPI from './profile'
import LoginAPI from './login'

Mock.setup({
  //timeout: '1000'
})

// 发送请求的API路径匹配，进行拦截
// 第一个参数：匹配请求的路径。第二个参数：请求的方式。第三个参数：相应的数据如何替换
Mock.mock(/\/table\/list\.*/, 'get', TableAPI.list)
Mock.mock(/\/frame\/profile/, 'post', ProfileAPI.profile)
Mock.mock(/\/frame\/login/, 'post', LoginAPI.login)
