package cn.iocoder.yudao.module.infra.controller.admin.redis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - Redis 配置 Response VO")
@Data
public class RedisConfigRespVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "连接名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "本地Redis")
    private String name;

    @Schema(description = "Redis 主机地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    private String host;

    @Schema(description = "Redis 端口", requiredMode = Schema.RequiredMode.REQUIRED, example = "6379")
    private Integer port;

    @Schema(description = "数据库索引", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer database;

    @Schema(description = "连接超时(毫秒)", requiredMode = Schema.RequiredMode.REQUIRED, example = "5000")
    private Integer timeout;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
