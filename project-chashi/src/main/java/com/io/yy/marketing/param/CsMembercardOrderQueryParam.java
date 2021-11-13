package com.io.yy.marketing.param;

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
 * 会员卡购买记录 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsMembercardOrderQueryParam对象", description = "会员卡购买记录查询参数")
public class CsMembercardOrderQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "会员卡id")
    private Long membercardId;

    @ApiModelProperty(value = "微信用户ID")
    private Long wxuserId;

    @ApiModelProperty(value = "支付状态：支付中、支付失败、支付成功")
    private Integer paymentStatus;

    @ApiModelProperty(value = "32位的UUID")
    private String outTradeNo;

    @ApiModelProperty(value = "支付失败错误消息")
    private String paymentMsg;

    @ApiModelProperty(value = "剩余优惠时长")
    private Double restDiscountTime;

    @ApiModelProperty(value = "剩余优惠价格")
    private Double restDiscountPrice;

    @ApiModelProperty(value = "订单退款单号")
    private String outRefundNo;

    @ApiModelProperty(value = "退款时间")
    private Date refundDate;
}
