package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/5/27
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "SysMessageAndWxTemplateQueryVo对象", description = "获取消息配置和模板映射")
public class SysMessageAndWxTemplateQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息配置id")
    private Long id;

    @ApiModelProperty(value = "微信模板id")
    private String wxConfigId;

    @ApiModelProperty(value = "微信内容模板")
    private String configTemplate;

    @ApiModelProperty(value = "微信模板映射数据")
    List<SysMessageWxTemplateQueryVo> tableData;
}
