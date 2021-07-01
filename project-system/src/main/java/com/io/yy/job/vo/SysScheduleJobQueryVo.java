package com.io.yy.job.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 定时任务 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2019-12-19
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysScheduleJobQueryVo对象", description = "定时任务查询参数")
public class SysScheduleJobQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务id")
    private String id;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务分组")
    private String jobGroup;

    @ApiModelProperty(value = "simple,cron")
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
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

}