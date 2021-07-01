package com.io.yy.system.param;

import com.io.yy.common.param.OrderQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 系统消息表 查询参数对象
 * </pre>
 *
 * @author zhaochao
 * @date 2019-12-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StudentSysMsgQueryParam", description = "学员系统消息表查询参数")
public class StudentSysMsgQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private String receiveUserId;

    @ApiModelProperty(value = "读取状态")
    private String status;

    @ApiModelProperty(value = "消息类型")
    private String msgTypeCode;
}
