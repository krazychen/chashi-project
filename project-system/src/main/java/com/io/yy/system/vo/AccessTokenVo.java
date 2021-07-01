package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/5/21
 **/

@Data
@Accessors(chain = true)
@ApiModel(value = "AccessTokenVo对象", description = "AccessToken数据")
public class AccessTokenVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "token")
    private String access_token;

    @ApiModelProperty(value = "有效时间")
    private String expires_in;

    @ApiModelProperty(value = "错误代码")
    private String errcode;

    @ApiModelProperty(value = "错误信息")
    private String errmsg;

    @ApiModelProperty(value = "失效时间")
    private Long invalid_time;
}
