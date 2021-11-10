package com.io.yy.merchant.entity;

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
 * 商家通知人员记录
 * </pre>
 *
 * @author kris
 * @since 2021-11-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsMerchantNotify对象", description = "商家通知人员记录")
public class CsMerchantNotify extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "商店id")
    @NotNull(message = "商店id不能为空")
    private Long merchantId;

    @ApiModelProperty(value = "值班开始时间")
    @NotNull(message = "值班开始时间不能为空")
    private Date startTime;

    @ApiModelProperty(value = "值班结束时间")
    @NotNull(message = "值班结束时间不能为空")
    private Date endTime;

    @ApiModelProperty(value = "通知类型，0：保洁，1：值班")
    @NotNull(message = "通知类型，0：保洁，1：值班不能为空")
    private Integer notifyType;

    @ApiModelProperty(value = "通知时点，0：预定后，1：消费结束")
    @NotNull(message = "通知时点，0：预定后，1：消费结束不能为空")
    private String notifyTime;

    @ApiModelProperty(value = "XX前XX分钟")
    private Integer notifyFrontTime;

    @ApiModelProperty(value = "XX后XX分钟")
    private Integer notifyRearTime;

    @ApiModelProperty(value = "微信用户ID")
    private Long wxuserId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "微信用户open ID")
    @NotBlank(message = "微信用户open ID不能为空")
    private String openid;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

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
