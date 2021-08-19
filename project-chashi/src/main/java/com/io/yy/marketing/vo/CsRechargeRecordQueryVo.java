package com.io.yy.marketing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 充值记录 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-18
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CsRechargeRecordQueryVo对象", description = "充值记录查询参数")
public class CsRechargeRecordQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "充值金额")
    private Double rechargeAmount;

    @ApiModelProperty(value = "赠送金额")
    private Double rechargeGived;

    @ApiModelProperty(value = "最终金额")
    private Double rechargeFinal;

    @ApiModelProperty(value = "积分")
    private Integer integral;

    @ApiModelProperty(value = "微信用户ID")
    private Long wxuserId;

    @ApiModelProperty(value = "微信用户手机")
    private String wxuserPhone;

    @ApiModelProperty(value = "微信用户openID")
    private String openid;

    @ApiModelProperty(value = "商品名称，充值+金额+预订日期+uuid")
    private String orderName;

    @ApiModelProperty(value = "购买日期")
    private Date orderDate;

    @ApiModelProperty(value = "32位的UUID")
    private String outTradeNo;

    @ApiModelProperty(value = "支付状态：支付中0、支付失败1、支付成功2，支付关闭3，充值失败后就将交易关闭，每次都是最新的")
    private Integer paymentStatus;

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

    @ApiModelProperty(value = "微信用户名称")
    private Long wxuserName;
}