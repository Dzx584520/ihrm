import {createAPI} from '@/utils/request'

export const list = data => createAPI('/company', 'get', data)
export const findById = data => createAPI(`/company/${data.id}`, 'get', data)
