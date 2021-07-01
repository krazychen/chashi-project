package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2019/12/5
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "SysUserRoleQueryParam对象", description = "用户和角色关联表查询参数")
public class SysUserRolePLQueryParam {

    @ApiModelProperty(value = "主键")
    @NotNull(message = "角色id不能为空")
    private String[] roleids;

    @ApiModelProperty(value = "关联菜单集合")
    @NotNull(message = "菜单id不能为空")
    private String[] userids;
}
