package cn.iocoder.yudao.module.infra.dal.dataobject.redis;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import cn.iocoder.yudao.framework.mybatis.core.type.EncryptTypeHandler;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Redis 配置
 *
 * @author 芋道源码
 */
@TableName(value = "infra_redis_config", autoResultMap = true)
@KeySequence("infra_redis_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@TenantIgnore
public class RedisConfigDO extends BaseDO {

    /**
     * 主键编号 - Master Redis
     */
    public static final Long ID_MASTER = 0L;

    /**
     * 主键编号
     */
    private Long id;
    /**
     * 连接名称
     */
    private String name;

    /**
     * Redis 主机地址
     */
    private String host;
    /**
     * Redis 端口
     */
    private Integer port;
    /**
     * 密码
     */
    @TableField(typeHandler = EncryptTypeHandler.class)
    private String password;
    /**
     * 数据库索引
     */
    private Integer database;
    /**
     * 连接超时(毫秒)
     */
    private Integer timeout;

}
