package com.io.yy.wxops.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * @author wuzhixiong
 * @date 2020/7/11
 **/

@Data
@Accessors(chain = true)
@ApiModel(value = "WxLoginQueryVo", description = "调用微信登录返回信息")
public class WxLoginQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "会话密钥")
    private String session_key;

    @ApiModelProperty(value = "用户唯一标识")
    private String openId;

    @ApiModelProperty(value = "用户在开放平台的唯一标识符")
    private String unionId;

    @ApiModelProperty(value = "errcode")
    private String errcode;

    @ApiModelProperty(value = "错误信息")
    private String errmsg;
}
