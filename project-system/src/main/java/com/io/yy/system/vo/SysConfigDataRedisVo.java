package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <pre>
 * 参数配置表 查询结果对象
 * </pre>
 *
 * @author zhaochao
 * @date 2020-01-02
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysConfigDataRedisVo对象", description = "参数配置表缓存对象")
public class SysConfigDataRedisVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String configName;

    @ApiModelProperty(value = "参数键")
    private String configKey;

    @ApiModelProperty(value = "参数值")
    private String configValue;
}