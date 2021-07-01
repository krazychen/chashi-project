package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 *  查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2020-05-26
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysMessageWxTemplateQueryVo对象", description = "查询参数")
public class SysMessageWxTemplateQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "消息配置id")
    private Long configId;

    @ApiModelProperty(value = "模板字段")
    private String templateField;

    @ApiModelProperty(value = "模板对应对象")
    private String teimplateFiledObj;

    @ApiModelProperty(value = "模板对应对象名称")
    private String teimplateFiledObjName;

    @ApiModelProperty(value = "对象类型")
    private String type;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本号")
    private Integer version;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "租户代码")
    private String corpCode;

    @ApiModelProperty(value = "租户名称")
    private String corpName;

}