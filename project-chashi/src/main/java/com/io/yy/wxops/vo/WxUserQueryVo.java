package com.io.yy.wxops.vo;

import com.io.yy.wxops.entity.WxUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 微信用户 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-06
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "WxUserQueryVo对象", description = "微信用户查询参数")
public class WxUserQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "盐值")
    private String salt;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "性别，0：女，1：男，默认1")
    private Integer gender;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "个性签名")
    private String signtext;

    @ApiModelProperty(value = "微信公众号openid")
    private String openId;

    @ApiModelProperty(value = "绑定的微信号unionid")
    private String unionId;

    @ApiModelProperty(value = "会员类型")
    private Integer menberType;

    @ApiModelProperty(value = "积分")
    private Integer integral;

    @ApiModelProperty(value = "余额")
    private Integer balance;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private Integer state;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "租户代码")
    private String corpCode;

    @ApiModelProperty(value = "租户名称")
    private String corpName;

    public static WxUserQueryVo toVo(WxUser wxUser){
        WxUserQueryVo vo = new WxUserQueryVo();
        BeanUtils.copyProperties(wxUser,vo);
        return vo;
    }
}