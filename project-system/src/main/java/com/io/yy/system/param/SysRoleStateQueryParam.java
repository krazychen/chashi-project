package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2019/11/30
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "SysRoleQueryParam对象", description = "系统角色查询参数")
public class SysRoleStateQueryParam {

    @ApiModelProperty(value = "角色id")
    @NotNull
    private String id;

    @ApiModelProperty(value = "角色状态")
    private String state;
}
