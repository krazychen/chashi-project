package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 字典数据表 查询结果对象
 * </pre>
 *
 * @author chenPengfei
 * @date 2019-11-23
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysDictDataQueryVo对象", description = "字典数据表查询参数")
public class SysDictDataQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    @ApiModelProperty(value = "父级编号")
    private String parentCode;

    @ApiModelProperty(value = "所有父级编号")
    private String parentCodes;

    @ApiModelProperty(value = "本级排序号（升序）")
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
    private String dictLabel;

    @ApiModelProperty(value = "字典键值")
    private String dictValue;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "系统内置（1是 0否）")
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
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "租户代码")
    private String corpCode;

    @ApiModelProperty(value = "租户名称")
    private String corpName;

    @ApiModelProperty(value = "下级字典集合")
    private List<SysDictDataQueryVo> children;
}