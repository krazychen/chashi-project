package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 参数配置表 查询参数对象
 * </pre>
 *
 * @author zhaochao
 * @date 2020-01-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysConfigQueryParam对象", description = "参数配置表查询参数")
public class SysConfigQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String configName;

    @ApiModelProperty(value = "参数键")
    private String configKey;

    @ApiModelProperty(value = "系统内置（1是 0否）")
    private String isSys;
}
