package com.io.yy.marketing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.io.yy.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 会员卡购买记录
 * </pre>
 *
 * @author kris
 * @since 2021-08-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsMembercardOrder对象", description = "会员卡购买记录")
public class CsMembercardOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "会员卡id")
    @NotNull(message = "会员卡id不能为空")
    private Long membercardId;

    @ApiModelProperty(value = "会员卡名称")
    private String membercardName;

    @ApiModelProperty(value = "微信用户ID")
    private Long wxuserId;

    @ApiModelProperty(value = "微信用户手机")
    private String wxuserPhone;

    @ApiModelProperty(value = "微信用户openID")
    private String openid;

    @ApiModelProperty(value = "商品名称，会员卡名称+预订手机+预订日期+uuid")
    private String orderName;

    @ApiModelProperty(value = "购买日期")
    private Date orderDate;

    @ApiModelProperty(value = "有效期")
    private Integer validPeriod;

    @ApiModelProperty(value = "会员卡开始时间")
    private Date startTime;

    @ApiModelProperty(value = "会员卡结束时间")
    private Date endTime;

    @ApiModelProperty(value = "实际总价")
    private Double orderPrice;

    @ApiModelProperty(value = "32位的UUID")
    private String outTradeNo;

    @ApiModelProperty(value = "支付状态：支付中、支付失败、支付成功")
    private Integer paymentStatus;

    @ApiModelProperty(value = "支付类型：余额支付、微信支付")
    private Integer paymentType;

    @ApiModelProperty(value = "支付失败错误消息")
    private Integer paymentMsg;

    @ApiModelProperty(value = "会员卡来源：系统发放0、用户购买1")
    private Integer sourceType;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private String status;

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
}
