package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 菜单表 查询参数对象
 * </pre>
 *
 * @author chenPengfei
 * @date 2019-11-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysMenuQueryParam对象", description = "菜单表查询参数")
public class SysMenuQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "菜单类型")
    private String menuType;

    @ApiModelProperty(value = "所属模块")
    private String moduleCodes;

    @ApiModelProperty(value = "权限标识")
    private String permission;
}
