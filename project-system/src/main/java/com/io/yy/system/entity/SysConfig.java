package com.io.yy.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.io.yy.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 参数配置表
 * </pre>
 *
 * @author zhaochao
 * @since 2020-01-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysConfig对象", description = "参数配置表")
public class SysConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    private String configName;

    @ApiModelProperty(value = "参数键")
    @NotBlank(message = "参数键不能为空")
    private String configKey;

    @ApiModelProperty(value = "参数文本值")
    private String configTextValue;

    @ApiModelProperty(value = "参数上传图片")
    private String configPicValue;

    @ApiModelProperty(value = "参数上传图片名称")
    private String configPicName;

    @ApiModelProperty(value = "上传图片文件对象")
    @JsonIgnore
    @TableField(exist = false)
    private MultipartFile[] uploadFile;

    @ApiModelProperty(value = "参数富文本值")
    private String configContentValue;

    @ApiModelProperty(value = "参数类型")
    @NotBlank(message = "参数类型不能为空")
    private String configType;

    @ApiModelProperty(value = "系统内置（1是 0否）")
    @NotBlank(message = "系统内置（1是 0否）不能为空")
    private String isSys;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建者")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改者")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "租户代码")
    private String corpCode;

    @ApiModelProperty(value = "租户名称")
    private String corpName;

}
