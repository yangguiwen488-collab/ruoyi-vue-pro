package cn.iocoder.yudao.module.infra.controller.admin.redis;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.infra.controller.admin.redis.vo.RedisConfigRespVO;
import cn.iocoder.yudao.module.infra.controller.admin.redis.vo.RedisConfigSaveReqVO;
import cn.iocoder.yudao.module.infra.dal.dataobject.redis.RedisConfigDO;
import cn.iocoder.yudao.module.infra.service.redis.RedisConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - Redis 配置")
@RestController
@RequestMapping("/infra/redis-config")
@Validated
public class RedisConfigController {

    @Resource
    private RedisConfigService redisConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建 Redis 配置")
    @PreAuthorize("@ss.hasPermission('infra:redis-config:create')")
    public CommonResult<Long> createRedisConfig(@Valid @RequestBody RedisConfigSaveReqVO createReqVO) {
        return success(redisConfigService.createRedisConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新 Redis 配置")
    @PreAuthorize("@ss.hasPermission('infra:redis-config:update')")
    public CommonResult<Boolean> updateRedisConfig(@Valid @RequestBody RedisConfigSaveReqVO updateReqVO) {
        redisConfigService.updateRedisConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除 Redis 配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('infra:redis-config:delete')")
    public CommonResult<Boolean> deleteRedisConfig(@RequestParam("id") Long id) {
        redisConfigService.deleteRedisConfig(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除 Redis 配置")
    @Parameter(name = "ids", description = "编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('infra:redis-config:delete')")
    public CommonResult<Boolean> deleteRedisConfigList(@RequestParam("ids") List<Long> ids) {
        redisConfigService.deleteRedisConfigList(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得 Redis 配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:redis-config:query')")
    public CommonResult<RedisConfigRespVO> getRedisConfig(@RequestParam("id") Long id) {
        RedisConfigDO config = redisConfigService.getRedisConfig(id);
        return success(BeanUtils.toBean(config, RedisConfigRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得 Redis 配置列表")
    @PreAuthorize("@ss.hasPermission('infra:redis-config:query')")
    public CommonResult<List<RedisConfigRespVO>> getRedisConfigList() {
        List<RedisConfigDO> list = redisConfigService.getRedisConfigList();
        return success(BeanUtils.toBean(list, RedisConfigRespVO.class));
    }

}
