package com.io.yy.shiro.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/2/26
 **/

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class WxOpenQueryVo implements Serializable {

    private static final long serialVersionUID = -1758338570596088158L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "unionid")
    private String wxOpenid;

    @ApiModelProperty(value = "openid")
    private String wxOpenidTwo;

    @ApiModelProperty(value = "微信")
    private String isWx;

    @ApiModelProperty(value = "绑定还是登陆")
    private String type;
}
