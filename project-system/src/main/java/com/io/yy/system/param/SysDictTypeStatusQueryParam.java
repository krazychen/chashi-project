package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 *
 *  根据id修改状态
 * @author chenPengFei
 * @date 2019/11/30
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "SysDictTypeStatusQueryParam对象", description = "根据字典类型id操作状态")
public class SysDictTypeStatusQueryParam {

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "id")
    @NotNull
    private String id;
}
