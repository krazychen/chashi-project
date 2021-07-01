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
 * 定时任务
 * </pre>
 *
 * @author kris
 * @since 2019-12-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysScheduleJob对象", description = "定时任务")
public class SysScheduleJob extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    @ApiModelProperty(value = "任务id")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "任务名称")
    @NotBlank(message = "任务名称不能为空")
    private String jobName;

    @ApiModelProperty(value = "任务分组")
    @NotBlank(message = "任务分组不能为空")
    private String jobGroup;

    @ApiModelProperty(value = "simple,cron")
    @NotBlank(message = "simple,cron不能为空")
    private String jobType;

    @ApiModelProperty(value = "spring bean名称")
    private String beanName;

    @ApiModelProperty(value = "参数")
    private String params;

    @ApiModelProperty(value = "cron表达式")
    private String cronExpression;

    @ApiModelProperty(value = "重复次数")
    private Integer simpleRepeatCount;

    @ApiModelProperty(value = "周期参数 - 间隔单位")
    private String simpleUnit;

    @ApiModelProperty(value = "周期参数 - 间隔时间")
    private Long simpleTime;

    @ApiModelProperty(value = "job开始时间")
    private Date startTime;

    @ApiModelProperty(value = "job结束时间")
    private Date endTime;

    @ApiModelProperty(value = "任务状态  0：正常  1：暂停")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建者")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

}
