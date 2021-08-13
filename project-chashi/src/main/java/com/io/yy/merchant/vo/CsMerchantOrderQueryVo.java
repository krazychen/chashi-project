package com.io.yy.merchant.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 商店茶室订单记录 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-12
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CsMerchantOrderQueryVo对象", description = "商店茶室订单记录查询参数")
public class CsMerchantOrderQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商店id")
    private Long merchantId;

    @ApiModelProperty(value = "茶室ID")
    private Long tearoomId;

    @ApiModelProperty(value = "微信用户ID")
    private Long wxuserId;

    @ApiModelProperty(value = "微信用户手机")
    private String wxuserPhone;

    @ApiModelProperty(value = "微信用户openID")
    private Long openid;

    @ApiModelProperty(value = "商品名称，用茶室名称+预订手机+预订日期+uuid")
    private String orderName;

    @ApiModelProperty(value = "预定日期")
    private Date orderDate;

    @ApiModelProperty(value = "预订时间段，多个用，隔开")
    private String orderTimerage;

    @ApiModelProperty(value = "总的预订时间段数")
    private Integer orderOriginTimenum;

    @ApiModelProperty(value = "会员卡优惠后的总的预订时间段数")
    private Integer orderTimenum;

    @ApiModelProperty(value = "预订原价")
    private Double orderUnitOriginPrice;

    @ApiModelProperty(value = "预订实际价格- 优惠卷优惠后")
    private Double orderUnitPrice;

    @ApiModelProperty(value = "预订总价")
    private Double orderOriginPrice;

    @ApiModelProperty(value = "优惠卷优惠的价格")
    private Double orderCpAmount;

    @ApiModelProperty(value = "会员卡减少的价格")
    private Double orderMbAmount;

    @ApiModelProperty(value = "实际总价")
    private Double orderPrice;

    @ApiModelProperty(value = "32位的UUID")
    private String outTradeNo;

    @ApiModelProperty(value = "支付状态：支付中、支付失败、支付成功")
    private Integer paymentStatus;

    @ApiModelProperty(value = "支付类型：余额支付、微信支付")
    private Integer paymentType;

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