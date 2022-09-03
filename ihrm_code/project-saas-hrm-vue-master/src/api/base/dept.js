import {createAPI, createFormAPI} from '@/utils/request'

//查询部门列表
export const list = data => createAPI('/company/department', 'get', data)
//保存部门
export const save = data => createAPI('/company/department', 'post', data)
//根据id查询部门
export const detail = data => createAPI(`/company/department/${data.id}`, 'get', data)
//根据id删除部门
export const deleteById = data => createAPI(`/company/department/${data.id}`, 'delete', data)
//根据更新部门
export const updateById = data => createAPI(`/company/department/${data.id}`, 'put', data)
//更新或保存
export const saveOrUpdate = data => {return data.id?updateById(data):save(data)}
