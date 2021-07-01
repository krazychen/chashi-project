package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/5/22
 **/

@Data
@Accessors(chain = true)
@ApiModel(value = "UserInfoVo对象", description = "用户信息")
public class UserInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "")
    private Integer subscribe;

    @ApiModelProperty(value = "")
    private String openid;

    @ApiModelProperty(value = "")
    private String nickname;

    @ApiModelProperty(value = "")
    private Integer sex;

    @ApiModelProperty(value = "")
    private String language;

    @ApiModelProperty(value = "")
    private String city;

    @ApiModelProperty(value = "")
    private String province;

    @ApiModelProperty(value = "")
    private String country;

    @ApiModelProperty(value = "")
    private String headimgurl;

    @ApiModelProperty(value = "")
    private Date subscribe_time;

    @ApiModelProperty(value = "")
    private String unionid;

    @ApiModelProperty(value = "")
    private String remark;

    @ApiModelProperty(value = "")
    private Integer groupid;

    @ApiModelProperty(value = "")
    private String subscribe_scene;

    @ApiModelProperty(value = "")
    private Integer qr_scene;

    @ApiModelProperty(value = "")
    private String qr_scene_str;
}