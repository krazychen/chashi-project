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
 * 充值消费
 * </pre>
 *
 * @author kris
 * @since 2021-08-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsRechargeConsum对象", description = "充值消费")
public class CsRechargeConsum extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "微信用户ID")
    @NotNull(message = "微信用户ID不能为空")
    private Long wxuserId;

    @ApiModelProperty(value = "消费金额")
    @NotNull(message = "消费金额不能为空")
    private Double cousumAmount;

    @ApiModelProperty(value = "茶室订单id")
    @NotNull(message = "茶室订单id不能为空")
    private Long roomOrderId;

    @ApiModelProperty(value = "会员卡订单id")
    @NotNull(message = "会员卡订单id不能为空")
    private Long cardOrderId;

    @ApiModelProperty(value = "消费类型，0是茶室订单，1是会员订单")
    @NotNull(message = "消费类型，0是茶室订单，1是会员订单不能为空")
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
