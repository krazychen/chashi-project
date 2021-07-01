package com.io.yy.job.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

import java.util.Date;

/**
 * <pre>
 * 定时任务日志 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2019-12-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysScheduleJoblogQueryParam对象", description = "定时任务日志查询参数")
public class SysScheduleJoblogQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务id")
    private String jobId;

    @ApiModelProperty(value = "创建时间")
    private Date startTime;

    @ApiModelProperty(value = "更新时间")
    private Date endTime;
}
