package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 参数配置表 查询结果对象
 * </pre>
 *
 * @author zhaochao
 * @date 2020-01-02
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysConfigQueryVo对象", description = "参数配置表查询参数")
public class SysConfigQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String id;

    @ApiModelProperty(value = "名称")
    private String configName;

    @ApiModelProperty(value = "参数键")
    private String configKey;

    @ApiModelProperty(value = "参数文本值")
    private String configTextValue;

    @ApiModelProperty(value = "参数上传图片")
    private String configPicValue;

    @ApiModelProperty(value = "参数上传图片名称")
    private String configPicName;

    @ApiModelProperty(value = "参数富文本值")
    private String configContentValue;

    @ApiModelProperty(value = "参数类型")
    private String configType;

    @ApiModelProperty(value = "系统内置（1是 0否）")
    private String isSys;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "租户代码")
    private String corpCode;

    @ApiModelProperty(value = "租户名称")
    private String corpName;

}