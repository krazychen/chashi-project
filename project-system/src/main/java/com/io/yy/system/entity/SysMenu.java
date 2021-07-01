package com.io.yy.system.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

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
 * 菜单表
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-11-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysMenu对象", description = "菜单表")
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单编码")
    @TableId(value = "menu_code", type = IdType.ID_WORKER)
    @Only(tableName = "sys_menu",field = "menu_code",message = "此编号已存在")
    private String menuCode;

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

    @ApiModelProperty(value = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    @ApiModelProperty(value = "菜单类型（1菜单 2权限 3开发）")
    @NotBlank(message = "菜单类型（1菜单 2权限 3开发）不能为空")
    private String menuType;

    @ApiModelProperty(value = "链接")
    private String menuHref;

    @ApiModelProperty(value = "目标")
    private String menuTarget;

    @ApiModelProperty(value = "图标")
    private String menuIcon;

    @ApiModelProperty(value = "颜色")
    private String menuColor;

    @ApiModelProperty(value = "菜单标题")
    private String menuTitle;

    @ApiModelProperty(value = "权限标识")
    private String permission;

    @ApiModelProperty(value = "菜单权重")
    private BigDecimal weight;

    @ApiModelProperty(value = "是否显示（1显示 0隐藏）")
    @NotBlank(message = "是否显示（1显示 0隐藏）不能为空")
    private String isShow;

    @ApiModelProperty(value = "归属系统（default:主导航菜单、mobileApp:APP菜单）")
    @NotBlank(message = "归属系统（default:主导航菜单、mobileApp:APP菜单）不能为空")
    private String sysCode;

    @ApiModelProperty(value = "归属模块（多个用逗号隔开）")
    private String moduleCodes;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    @Version
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

}
