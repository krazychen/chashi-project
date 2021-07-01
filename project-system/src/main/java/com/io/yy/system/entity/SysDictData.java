package com.io.yy.system.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.io.yy.common.constraints.Only;
import com.io.yy.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 字典数据表
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysDictData对象", description = "字典数据表")
public class SysDictData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典编码")
    @TableId(value = "dict_code", type = IdType.ID_WORKER)
    private String dictCode;

    @ApiModelProperty(value = "父级编号")
    private String parentCode;

    @ApiModelProperty(value = "所有父级编号")
    private String parentCodes;

    @ApiModelProperty(value = "本级排序号（升序）")
    @NotNull(message = "本级排序号（升序）不能为空")
    private BigDecimal treeSort;

    @ApiModelProperty(value = "所有级别排序号")
    private String treeSorts;

    @ApiModelProperty(value = "是否最末级")
    private String treeLeaf;

    @ApiModelProperty(value = "层次级别")
    private BigDecimal treeLevel;

    @ApiModelProperty(value = "全节点名")
    private String treeNames;

    @ApiModelProperty(value = "字典标签")
    @NotBlank(message = "字典标签不能为空")
    private String dictLabel;

    @ApiModelProperty(value = "字典键值")
    @NotBlank(message = "字典键值不能为空")
    private String dictValue;

    @ApiModelProperty(value = "字典类型")
    @NotBlank(message = "字典类型不能为空")
    private String dictType;

    @ApiModelProperty(value = "系统内置（1是 0否）")
    @NotBlank(message = "系统内置（1是 0否）不能为空")
    private String isSys;

    @ApiModelProperty(value = "字典描述")
    private String description;

    @ApiModelProperty(value = "css样式（如：color:red)")
    private String cssStyle;

    @ApiModelProperty(value = "css类名（如：red）")
    private String cssClass;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "状态（0正常 1删除 2停用）")
    private String status;

    @ApiModelProperty(value = "创建者")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "租户代码")
    private String corpCode;

    @ApiModelProperty(value = "租户名称")
    private String corpName;

}
