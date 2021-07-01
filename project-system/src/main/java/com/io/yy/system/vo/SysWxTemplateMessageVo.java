package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/5/31
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "SysWxTemplateMessageVo对象", description = "微信模板消息发送后返回")
public class SysWxTemplateMessageVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "代码")
    private int errcode;

    @ApiModelProperty(value = "提示")
    private String errmsg;

    @ApiModelProperty(value = "发送的id号")
    private Long msgid;
}
