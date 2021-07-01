package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 系统消息记录表 查询参数对象
 * </pre>
 *
 * @author zhaochao
 * @date 2019-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysMsgRecordQueryParam对象", description = "系统消息记录表查询参数")
public class SysMsgRecordQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统消息id")
    private Long msgId;

    @ApiModelProperty(value = "读取状态")
    private String status;
}
