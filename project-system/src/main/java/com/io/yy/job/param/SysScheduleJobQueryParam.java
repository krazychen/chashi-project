package com.io.yy.job.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 定时任务 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2019-12-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysScheduleJobQueryParam对象", description = "定时任务查询参数")
public class SysScheduleJobQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "任务分组")
    private String jobGroup;

    @ApiModelProperty(value = "任务状态  0：正常  1：暂停")
    private Integer status;
}
