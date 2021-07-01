package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/5/11
 **/

@Data
@Accessors(chain = true)
@ApiModel(value = "CheckUserNameParam对象", description = "验证用户名是否重复参数")
public class CheckUserNameParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名称不能为空")
    private String userName;
}
