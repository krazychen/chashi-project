package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2019/11/26
 **/

@Data
@Accessors(chain = true)
@ApiModel(value = "SysMenuTypeQueryParam对象", description = "菜单类型查询菜单")
public class SysMenuTypeQueryParam {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "菜单类型")
    private String menuType;
}
