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
    @NotBlank
    private String jsCode;

    @ApiModelProperty(value = "加密的信息")
    @NotBlank
    private String encryptedData;

    @ApiModelProperty(value = "加密算法的初始向量， 用户数据的签名验证和加解密")
    @NotBlank
    private String iv;
}
