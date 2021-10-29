package com.io.yy.wxops.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 *
 * @author wuzhixiong
 * @date 2020/07/11
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "WxLoginQueryParam对象", description = "微信登陆参数")
public class WxLoginQueryParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "小程序调用wx.login获取的 code")
    private String jsCode;

    @ApiModelProperty(value = "加密的信息")
    private String encryptedData;

    @ApiModelProperty(value = "加密算法的初始向量， 用户数据的签名验证和加解密")
    private String iv;

    @ApiModelProperty(value = "小程序调用wx.login获取的 code后，服务下发给前端的sessionKey。不传的话。根据jscode重新获取")
    private String session_key;

    @ApiModelProperty(value = "用户唯一标识")
    private String openid;

    @ApiModelProperty(value = "用户在开放平台的唯一标识符")
    private String unionid;

    @ApiModelProperty(value = "小程序 wx.checksession通过，currentSessionObj为空时。重新从服务端缓存中获取标识。1 为重新获取")
    private String reGetFlag;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "推荐人的ID")
    private Long recommendId;

}
