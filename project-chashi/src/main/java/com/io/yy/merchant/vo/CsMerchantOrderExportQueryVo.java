package com.io.yy.merchant.vo;

import com.io.yy.util.excel.annotation.ExcelField;
import com.io.yy.util.excel.fieldtype.DateTimeType;
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
public class CsMerchantOrderExportQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "茶室名称")
    @ExcelField(title="茶室名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=1)
    private String roomName;

    @ApiModelProperty(value = "商店地址")
    @ExcelField(title="商店地址", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=2)
    private String address;

    @ApiModelProperty(value = "微信用户手机")
    @ExcelField(title="用户手机", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=3)
    private String wxuserPhone;

    @ApiModelProperty(value = "微信用户openID")
    @ExcelField(title="用户openID", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=4)
    private String openid;

    @ApiModelProperty(value = "微信用户名称")
    @ExcelField(title="用户名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=5)
    private String nickname;

    @ApiModelProperty(value = "商品名称，用茶室名称+预订手机+预订日期+uuid")
    @ExcelField(title="商品名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=6)
    private String orderName;

    @ApiModelProperty(value = "预定日期")
    @ExcelField(title="订单日期", type= ExcelField.Type.ALL ,fieldType= DateTimeType.class, align= ExcelField.Align.CENTER, sort=7)
    private Date orderDate;

    @ApiModelProperty(value = "预订时间段，多个用，隔开")
    @ExcelField(title="预订时间段", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=8)
    private String orderTimerage;

    @ApiModelProperty(value = "总的预订时间段数")
    @ExcelField(title="预订时间段数", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=9)
    private Double orderOriginTimenum;

    @ApiModelProperty(value = "会员卡优惠时间段数")
    @ExcelField(title="会员优惠时间段数", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=10)
    private Double orderMbTimenum;

    @ApiModelProperty(value = "会员卡优惠后的总的预订时间段数")
    @ExcelField(title="实际预定时间段数", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=11)
    private Double orderTimenum;

    @ApiModelProperty(value = "预订原单价")
    @ExcelField(title="预订单价", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=12)
    private Double orderUnitOriginPrice;

    @ApiModelProperty(value = "会员卡折扣价格")
    @ExcelField(title="会员折扣单价", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=13)
    private Double orderUnitPrice;

    @ApiModelProperty(value = "预订总价")
    @ExcelField(title="预订总价", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=14)
    private Double orderOriginPrice;

    @ApiModelProperty(value = "优惠卷优惠的价格")
    @ExcelField(title="优惠卷优惠金额", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=15)
    private Double orderCpAmount;

    @ApiModelProperty(value = "会员卡减少的价格")
    @ExcelField(title="会员优惠金额", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=16)
    private Double orderMbAmount;

    @ApiModelProperty(value = "实际总价")
    @ExcelField(title="实际总价", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=17)
    private Double orderPrice;

    @ApiModelProperty(value = "32位的UUID")
    @ExcelField(title="微信交易号", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=18)
    private String outTradeNo;

    @ApiModelProperty(value = "订单退款单号")
    @ExcelField(title="微信退款单号", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=19)
    private String outRefundNo;

    @ApiModelProperty(value = "退款时间")
    @ExcelField(title="退款时间", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=20)
    private Date refundDate;

    @ApiModelProperty(value = "支付状态：支付中0、支付失败1、支付成功2，支付取消3，支付失败4")
    private Integer paymentStatus;

    @ApiModelProperty(value = "支付状态：支付中0、支付失败1、支付成功2，支付取消3，支付失败4")
    @ExcelField(title="支付状态", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=21)
    private String paymentStatusName;

    @ApiModelProperty(value = "支付类型：余额支付、微信支付")
    private Integer paymentType;

    @ApiModelProperty(value = "支付类型：余额支付、微信支付")
    @ExcelField(title="支付类型", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=22)
    private String paymentTypeName;

    @ApiModelProperty(value = "支付失败错误消息")
    @ExcelField(title="支付失败错误消息", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=23)
    private String paymentMsg;

    @ApiModelProperty(value = "使用状态：未使用0，已使用1，已取消2, 已完成3")
    private String usedStatus;

    @ApiModelProperty(value = "使用状态：未使用0，已使用1，已取消2, 已完成3")
    @ExcelField(title="使用状态", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=24)
    private String usedStatusName;

    @ApiModelProperty(value = "创建时间")
    @ExcelField(title="订单创建时间", type= ExcelField.Type.ALL ,fieldType= DateTimeType.class,align= ExcelField.Align.CENTER, sort=254)
    private Date createTime;
}