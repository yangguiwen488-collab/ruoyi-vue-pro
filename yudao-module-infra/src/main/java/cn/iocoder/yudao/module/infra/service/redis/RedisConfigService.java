package cn.iocoder.yudao.module.infra.service.redis;

import cn.iocoder.yudao.module.infra.controller.admin.redis.vo.RedisConfigSaveReqVO;
import cn.iocoder.yudao.module.infra.dal.dataobject.redis.RedisConfigDO;

import javax.validation.Valid;
import java.util.List;

/**
 * Redis 配置 Service 接口
 *
 * @author 芋道源码
 */
public interface RedisConfigService {

    /**
     * 创建 Redis 配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRedisConfig(@Valid RedisConfigSaveReqVO createReqVO);

    /**
     * 更新 Redis 配置
     *
     * @param updateReqVO 更新信息
     */
    void updateRedisConfig(@Valid RedisConfigSaveReqVO updateReqVO);

    /**
     * 删除 Redis 配置
     *
     * @param id 编号
     */
    void deleteRedisConfig(Long id);

    /**
     * 批量删除 Redis 配置
     *
     * @param ids 编号列表
     */
    void deleteRedisConfigList(List<Long> ids);

    /**
     * 获得 Redis 配置
     *
     * @param id 编号
     * @return Redis 配置
     */
    RedisConfigDO getRedisConfig(Long id);

    /**
     * 获得 Redis 配置列表
     *
     * @return Redis 配置列表
     */
    List<RedisConfigDO> getRedisConfigList();

}
