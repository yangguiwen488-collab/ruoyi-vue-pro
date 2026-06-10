package cn.iocoder.yudao.module.infra.service.redis;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.infra.controller.admin.redis.vo.RedisConfigSaveReqVO;
import cn.iocoder.yudao.module.infra.dal.dataobject.redis.RedisConfigDO;
import cn.iocoder.yudao.module.infra.dal.mysql.redis.RedisConfigMapper;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.infra.enums.ErrorCodeConstants.REDIS_CONFIG_NOT_EXISTS;
import static cn.iocoder.yudao.module.infra.enums.ErrorCodeConstants.REDIS_CONFIG_NOT_OK;

/**
 * Redis 配置 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class RedisConfigServiceImpl implements RedisConfigService {

    @Resource
    private RedisConfigMapper redisConfigMapper;

    @Resource
    private RedisProperties redisProperties;

    @Override
    public Long createRedisConfig(RedisConfigSaveReqVO createReqVO) {
        RedisConfigDO config = BeanUtils.toBean(createReqVO, RedisConfigDO.class);
        validateConnectionOK(config);

        // 插入
        redisConfigMapper.insert(config);
        // 返回
        return config.getId();
    }

    @Override
    public void updateRedisConfig(RedisConfigSaveReqVO updateReqVO) {
        // 校验存在
        validateRedisConfigExists(updateReqVO.getId());
        RedisConfigDO updateObj = BeanUtils.toBean(updateReqVO, RedisConfigDO.class);
        validateConnectionOK(updateObj);

        // 更新
        redisConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteRedisConfig(Long id) {
        // 校验存在
        validateRedisConfigExists(id);
        // 删除
        redisConfigMapper.deleteById(id);
    }

    @Override
    public void deleteRedisConfigList(List<Long> ids) {
        redisConfigMapper.deleteByIds(ids);
    }

    private void validateRedisConfigExists(Long id) {
        if (redisConfigMapper.selectById(id) == null) {
            throw exception(REDIS_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public RedisConfigDO getRedisConfig(Long id) {
        // 如果 id 为 0，默认为 master 的 Redis
        if (Objects.equals(id, RedisConfigDO.ID_MASTER)) {
            return buildMasterRedisConfig();
        }
        // 从 DB 中读取
        return redisConfigMapper.selectById(id);
    }

    @Override
    public List<RedisConfigDO> getRedisConfigList() {
        List<RedisConfigDO> result = redisConfigMapper.selectList();
        // 补充 master Redis
        result.add(0, buildMasterRedisConfig());
        return result;
    }

    private void validateConnectionOK(RedisConfigDO config) {
        // 构建 Redisson 配置
        Config redissonConfig = new Config();
        String address = String.format("redis://%s:%d", config.getHost(), config.getPort());
        redissonConfig.useSingleServer()
                .setAddress(address)
                .setDatabase(config.getDatabase())
                .setConnectTimeout(config.getTimeout() != null ? config.getTimeout() : 5000);
        // 如果有密码，设置密码
        if (StrUtil.isNotBlank(config.getPassword())) {
            redissonConfig.useSingleServer().setPassword(config.getPassword());
        }
        // 创建临时 RedissonClient 验证连接
        RedissonClient redissonClient = Redisson.create(redissonConfig);
        try {
            redissonClient.getKeys().count(); // 测试连接是否可用
        } catch (Exception ex) {
            throw exception(REDIS_CONFIG_NOT_OK);
        } finally {
            redissonClient.shutdown();
        }
    }

    private RedisConfigDO buildMasterRedisConfig() {
        return new RedisConfigDO()
                .setId(RedisConfigDO.ID_MASTER)
                .setName("master")
                .setHost(redisProperties.getHost())
                .setPort(redisProperties.getPort())
                .setPassword(redisProperties.getPassword())
                .setDatabase(redisProperties.getDatabase())
                .setTimeout(redisProperties.getTimeout() != null
                        ? (int) redisProperties.getTimeout().toMillis() : 5000);
    }

}
