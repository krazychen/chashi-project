package com.io.yy.marketing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 充值消费 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-18
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CsRechargeConsumQueryVo对象", description = "充值消费查询参数")
public class CsRechargeConsumQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "微信用户ID")
    private Long wxuserId;

    @ApiModelProperty(value = "消费金额")
    private Double cousumAmount;

    @ApiModelProperty(value = "消费时间")
    private Date cousumDate;

    @ApiModelProperty(value = "茶室订单id")
    private Long roomOrderId;

    @ApiModelProperty(value = "会员卡订单id")
    private Long cardOrderId;

    @ApiModelProperty(value = "消费类型，0是茶室订单，1是会员订单")
    private Integer consumType;

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