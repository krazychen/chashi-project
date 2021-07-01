package com.io.yy.job.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 定时任务日志
 * </pre>
 *
 * @author kris
 * @since 2019-12-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysScheduleJoblog对象", description = "定时任务日志")
public class SysScheduleJoblog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务日志id")
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "任务日志id不能为空")
    private Long id;

    @ApiModelProperty(value = "任务id")
    @NotNull(message = "任务id不能为空")
    private String jobId;

    @ApiModelProperty(value = "spring bean名称")
    private String beanName;

    @ApiModelProperty(value = "参数")
    private String params;

    @ApiModelProperty(value = "任务状态    0：成功    1：失败")
    @NotNull(message = "任务状态    0：成功    1：失败不能为空")
    private Integer status;

    @ApiModelProperty(value = "失败信息")
    private String error;

    @ApiModelProperty(value = "耗时(单位：毫秒)")
    @NotNull(message = "耗时(单位：毫秒)不能为空")
    private Integer times;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建者")
    @TableField(fill = FieldFill.DEFAULT)
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.DEFAULT)
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    @TableField(fill = FieldFill.DEFAULT)
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.DEFAULT)
    private Date updateTime;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

}
