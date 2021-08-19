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
 * 充值记录
 * </pre>
 *
 * @author kris
 * @since 2021-08-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsRechargeRecord对象", description = "充值记录")
public class CsRechargeRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "充值金额")
    @NotNull(message = "充值金额不能为空")
    private Double rechargeAmount;

    @ApiModelProperty(value = "赠送金额")
    @NotNull(message = "赠送金额不能为空")
    private Double rechargeGived;

    @ApiModelProperty(value = "最终金额")
    @NotNull(message = "最终金额不能为空")
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
    private Integer status;

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
