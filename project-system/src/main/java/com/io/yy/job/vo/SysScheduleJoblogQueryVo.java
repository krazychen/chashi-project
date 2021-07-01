package com.io.yy.job.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 定时任务日志 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2019-12-18
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysScheduleJoblogQueryVo对象", description = "定时任务日志查询参数")
public class SysScheduleJoblogQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务日志id")
    private Long id;

    @ApiModelProperty(value = "任务id")
    private String jobId;

    @ApiModelProperty(value = "spring bean名称")
    private String beanName;

    @ApiModelProperty(value = "参数")
    private String params;

    @ApiModelProperty(value = "任务状态    0：成功    1：失败")
    private Integer status;

    @ApiModelProperty(value = "失败信息")
    private String error;

    @ApiModelProperty(value = "耗时(单位：毫秒)")
    private Integer times;

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

    @ApiModelProperty(value = "任务名称")
    private String jobName;

}