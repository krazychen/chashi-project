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
 * @date 2020/4/1
 **/

@Data
@Accessors(chain = true)
@ApiModel(value = "ImgUrlQueryParam对象", description = "Base64字符串")
public class PasswordQueryParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户密码")
    @NotBlank(message = "密码不能为空")
    private String pass;

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "没有指定用户")
    private String id;

    @ApiModelProperty(value = "加盐")
    private String salt;
}
