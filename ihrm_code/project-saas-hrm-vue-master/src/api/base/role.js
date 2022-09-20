import {createAPI, createFormAPI} from '@/utils/request'
import data from '../../icons/generateIconsView'

//查询角色
export const list = data => createAPI('/sys/role', 'get', data)
//保存角色
export const add = data => createAPI('/sys/role', 'post', data)
//根据id查询角色
export const detail = data => createAPI(`/sys/role/${data.id}`, 'get', data)
//根据id删除角色
export const remove = data => createAPI(`/sys/role/${data.id}`, 'delete', data)
//根据更新角色
export const update = data => createAPI(`/sys/role/${data.id}`, 'put', data)
//更新或保存
export const saveOrUpdate = data => {return data.id?update(data):add(data)}
export const assignPrem = data => createAPI(`/sys/role/assignPrem`, 'put', data)

export const roleList = data => createAPI(`/sys/role/list`,'get',data)
