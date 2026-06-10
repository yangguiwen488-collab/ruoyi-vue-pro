package cn.iocoder.yudao.module.infra.dal.mysql.redis;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.infra.dal.dataobject.redis.RedisConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Redis 配置 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RedisConfigMapper extends BaseMapperX<RedisConfigDO> {
}
