package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 系统消息表 查询参数对象
 * </pre>
 *
 * @author zhaochao
 * @date 2019-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysMsgQueryParam对象", description = "系统消息表查询参数")
public class SysMsgQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    private String msgTitle;

    @ApiModelProperty(value = "消息内容")
    private String msgContent;

    @ApiModelProperty(value = "是否必读")
    private String mustRead;

    @ApiModelProperty(value = "发布人id")
    private Long sendUserId;

    @ApiModelProperty(value = "发布人用户名")
    private String sendUserName;

    @ApiModelProperty(value = "消息类型")
    private String msgTypeCode;

    @ApiModelProperty(value = "通知方式，多个通知方式以逗号隔开")
    private String msgNotifyTypeCode;

    @ApiModelProperty(value = "通知对象类型")
    private String msgNotifyObjCode;
}
