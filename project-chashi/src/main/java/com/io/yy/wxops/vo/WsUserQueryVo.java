package com.io.yy.wxops.vo;

import com.io.yy.wxops.entity.WsUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 用户信息表 查询结果对象
 * </pre>
 *
 * @author wuzhixiong
 * @date 2020-07-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "WsUserQueryVo对象", description = "用户信息表查询参数")
public class WsUserQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "平台唯一id")
    private String unionId;

    @ApiModelProperty(value = "应用唯一id")
    private String openId;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty(value = "用户性别。0 未知，1男性， 2 女性")
    private Integer gender;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    public static WsUserQueryVo toVo(WsUser wsUser){
        WsUserQueryVo vo = new WsUserQueryVo();
        BeanUtils.copyProperties(wsUser,vo);
        return vo;
    }

}