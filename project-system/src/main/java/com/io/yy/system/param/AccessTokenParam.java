package com.io.yy.system.param;

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
@ApiModel(value = "AccessTokenParam对象", description = "公众号accessToken请求参数")
public class AccessTokenParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "获取access_token填写client_credential")
    private String grant_type;

    @ApiModelProperty(value = "第三方用户唯一凭证")
    private String appid;

    @ApiModelProperty(value = "第三方用户唯一凭证密钥，即appsecret")
    private String secret;
}
