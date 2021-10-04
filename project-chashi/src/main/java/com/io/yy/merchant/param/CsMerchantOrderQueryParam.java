package com.io.yy.merchant.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

import java.util.Date;

/**
 * <pre>
 * 商店茶室订单记录 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsMerchantOrderQueryParam对象", description = "商店茶室订单记录查询参数")
public class CsMerchantOrderQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "商店id")
    private Long merchantId;

    @ApiModelProperty(value = "茶室ID")
    private Long tearoomId;

    @ApiModelProperty(value = "预定日期")
    private Date orderDate;

    @ApiModelProperty(value = "查询类型:1全部，2待付款，3待使用，4已使用，5已完成，6已取消，7已退款")
    private int queryType;

    @ApiModelProperty(value = "昵称和手机号")
    private String nameAphone;

    @ApiModelProperty(value = "32位的UUID")
    private String outTradeNo;

    @ApiModelProperty(value = "支付状态：支付中0、支付失败1、支付成功2，支付取消3，支付关闭4，支付退款5")
    private Integer paymentStatus;

    @ApiModelProperty(value = "支付失败错误消息")
    private String paymentMsg;

    @ApiModelProperty(value = "订单退款单号")
    private String outRefundNo;

    @ApiModelProperty(value = "退款时间")
    private Date refundDate;

    @ApiModelProperty(value = "键盘密码ID")
    private String keyboardPwdId;

    @ApiModelProperty(value = "键盘密码")
    private String keyboardPwd;
}
