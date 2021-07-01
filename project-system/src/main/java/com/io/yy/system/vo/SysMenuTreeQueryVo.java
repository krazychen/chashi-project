package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2019/11/26
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "SysMenuTreeQueryVo对象", description = "菜单树形结构")
public class SysMenuTreeQueryVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单编码")
    private String id;

    @ApiModelProperty(value = "下级菜单集合")
    private List<SysMenuTreeQueryVo> children;

    @ApiModelProperty(value = "菜单名称")
    private String label;

    @ApiModelProperty(value = "菜单权限")
    private String permission;

    @ApiModelProperty(value = "菜单链接")
    private String menuHref;
}
