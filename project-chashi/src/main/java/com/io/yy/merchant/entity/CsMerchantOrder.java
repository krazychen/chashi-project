package com.io.yy.merchant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.io.yy.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 商店茶室订单记录
 * </pre>
 *
 * @author kris
 * @since 2021-08-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsMerchantOrder对象", description = "商店茶室订单记录")
public class CsMerchantOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "商店id")
    @NotNull(message = "商店id不能为空")
    private Long merchantId;

    @ApiModelProperty(value = "茶室ID")
    @NotNull(message = "茶室ID不能为空")
    private Long tearoomId;

    @ApiModelProperty(value = "茶室名称")
    private String roomName;

    @ApiModelProperty(value = "微信用户ID")
    @NotNull(message = "微信用户ID不能为空")
    private Long wxuserId;

    @ApiModelProperty(value = "微信用户手机")
    @NotBlank(message = "微信用户手机不能为空")
    private String wxuserPhone;

    @ApiModelProperty(value = "微信用户openID")
    @NotNull(message = "微信用户openID不能为空")
    private String openid;

    @ApiModelProperty(value = "续单前的原订单")
    private String originOrderId;

    @ApiModelProperty(value = "商品名称，用茶室名称+预订手机+预订日期+uuid")
    private String orderName;

    @ApiModelProperty(value = "预定日期")
    private Date orderDate;

    @ApiModelProperty(value = "预订时间段，多个用，隔开")
    private String orderTimerage;

    @ApiModelProperty(value = "下一个预定日期")
    private Date nextOrderDate;

    @ApiModelProperty(value = "下一个预订时间段，多个用，隔开")
    private String nextOrderTimerage;

    @ApiModelProperty(value = "总的预订时间段数")
    private Double orderOriginTimenum;

    @ApiModelProperty(value = "会员卡订单ID")
    private Long membercardOrderId;

    @ApiModelProperty(value = "会员卡优惠时间段数")
    private Double orderMbTimenum;

    @ApiModelProperty(value = "会员卡优惠后的总的预订时间段数")
    private Double orderTimenum;

    @ApiModelProperty(value = "预订原价")
    private Double orderUnitOriginPrice;

    @ApiModelProperty(value = "会员卡折扣价格")
    private Double orderUnitPrice;

    @ApiModelProperty(value = "预订总价")
    private Double orderOriginPrice;

    @ApiModelProperty(value = "用户优惠卷ID")
    private Long couponReleasedId;

    @ApiModelProperty(value = "优惠卷优惠的价格")
    private Double orderCpAmount;

    @ApiModelProperty(value = "会员卡减少的价格")
    private Double orderMbAmount;

    @ApiModelProperty(value = "实际总价")
    private Double orderPrice;

    @ApiModelProperty(value = "32位的UUID")
    private String outTradeNo;

    @ApiModelProperty(value = "订单退款单号")
    private String outRefundNo;

    @ApiModelProperty(value = "退款时间")
    private Date refundDate;

    @ApiModelProperty(value = "支付状态：支付中0、支付失败1、支付成功2，支付取消3，支付关闭4，支付退款5")
    private Integer paymentStatus;

    @ApiModelProperty(value = "支付失败错误消息")
    private String paymentMsg;

    @ApiModelProperty(value = "支付类型：余额支付、微信支付")
    private Integer paymentType;

    @ApiModelProperty(value = "ttlock锁的id")
    private String ttlLockId;

    @ApiModelProperty(value = "键盘密码ID")
    private String keyboardPwdId;

    @ApiModelProperty(value = "键盘密码")
    private String keyboardPwd;

    @ApiModelProperty(value = "茶室门锁id")
    private String rttlLockId;

    @ApiModelProperty(value = "茶室键盘密码ID")
    private String rkeyboardPwdId;

    @ApiModelProperty(value = "茶室键盘密码")
    private String rkeyboardPwd;

    @ApiModelProperty(value = "使用状态：未使用0，已使用1，已取消2")
    private String usedStatus;

    @ApiModelProperty(value = "订单来源：系统新增0、用户购买1")
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
