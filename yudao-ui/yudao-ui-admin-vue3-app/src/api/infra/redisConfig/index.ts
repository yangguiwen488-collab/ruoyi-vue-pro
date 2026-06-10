import request from '@/config/axios'

export interface RedisConfigVO {
  id: number | undefined
  name: string
  host: string
  port: number
  password: string
  database: number
  timeout: number
  createTime?: Date
}

// 新增 Redis 配置
export const createRedisConfig = (data: RedisConfigVO) => {
  return request.post({ url: '/infra/redis-config/create', data })
}

// 修改 Redis 配置
export const updateRedisConfig = (data: RedisConfigVO) => {
  return request.put({ url: '/infra/redis-config/update', data })
}

// 删除 Redis 配置
export const deleteRedisConfig = (id: number) => {
  return request.delete({ url: '/infra/redis-config/delete?id=' + id })
}

// 批量删除 Redis 配置
export const deleteRedisConfigList = (ids: number[]) => {
  return request.delete({ url: '/infra/redis-config/delete-list', params: { ids: ids.join(',') } })
}

// 查询 Redis 配置详情
export const getRedisConfig = (id: number) => {
  return request.get({ url: '/infra/redis-config/get?id=' + id })
}

// 查询 Redis 配置列表
export const getRedisConfigList = () => {
  return request.get({ url: '/infra/redis-config/list' })
}
