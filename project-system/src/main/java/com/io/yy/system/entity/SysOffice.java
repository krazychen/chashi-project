package com.io.yy.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.io.yy.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <pre>
 * 组织机构表
 * </pre>
 *
 * @author zhaochao
 * @since 2019-11-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysOffice对象", description = "组织机构表")
public class SysOffice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构编码")
    @TableId(value = "office_code", type = IdType.ID_WORKER)
    private String officeCode;

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

    @ApiModelProperty(value = "机构代码")
    @NotBlank(message = "机构代码不能为空")
    private String viewCode;

    @ApiModelProperty(value = "机构名称")
    @NotBlank(message = "机构名称不能为空")
    private String officeName;

    @ApiModelProperty(value = "机构全称")
    @NotBlank(message = "机构全称不能为空")
    private String fullName;

    @ApiModelProperty(value = "机构类型")
    @NotBlank(message = "机构类型不能为空")
    private String officeType;

    @ApiModelProperty(value = "负责人")
    private String leader;

    @ApiModelProperty(value = "办公电话")
    private String phone;

    @ApiModelProperty(value = "联系地址")
    private String address;

    @ApiModelProperty(value = "邮政编码")
    private String zipCode;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
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

    @ApiModelProperty(value = "账户默认规则")
    private String defaultAccountRules;

}
