package cn.iocoder.yudao.module.infra.controller.admin.redis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - Redis 配置创建/修改 Request VO")
@Data
public class RedisConfigSaveReqVO {

    @Schema(description = "主键编号", example = "1024")
    private Long id;

    @Schema(description = "连接名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "本地Redis")
    @NotEmpty(message = "连接名称不能为空")
    private String name;

    @Schema(description = "Redis 主机地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "127.0.0.1")
    @NotEmpty(message = "主机地址不能为空")
    private String host;

    @Schema(description = "Redis 端口", requiredMode = Schema.RequiredMode.REQUIRED, example = "6379")
    @NotNull(message = "端口不能为空")
    private Integer port;

    @Schema(description = "密码", example = "123456")
    private String password;

    @Schema(description = "数据库索引", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "数据库索引不能为空")
    private Integer database;

    @Schema(description = "连接超时(毫秒)", requiredMode = Schema.RequiredMode.REQUIRED, example = "5000")
    @NotNull(message = "连接超时不能为空")
    private Integer timeout;

}
