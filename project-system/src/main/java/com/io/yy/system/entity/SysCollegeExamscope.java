package com.io.yy.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

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

/**
 * <pre>
 * 考试范围表
 * </pre>
 *
 * @author zhaochao
 * @since 2020-01-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysCollegeExamscope对象", description = "考试范围表")
public class SysCollegeExamscope extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "专业id")
    @NotNull(message = "专业id不能为空")
    private Long collegeId;

    @ApiModelProperty(value = "科目一序号")
    @NotBlank(message = "科目一序号不能为空")
    private String subOneOrdinal;

    @ApiModelProperty(value = "科目一代码")
    private String subOneCode;

    @ApiModelProperty(value = "科目一名称")
    private String subOneName;

    @ApiModelProperty(value = "科目一备注")
    private String subOneRemarks;

    @ApiModelProperty(value = "科目二序号")
    @NotBlank(message = "科目二序号不能为空")
    private String subTwoOrdinal;

    @ApiModelProperty(value = "科目二代码")
    private String subTwoCode;

    @ApiModelProperty(value = "科目二名称")
    private String subTwoName;

    @ApiModelProperty(value = "科目二备注")
    private String subTwoRemarks;

    @ApiModelProperty(value = "科目三序号")
    @NotBlank(message = "科目三序号不能为空")
    private String subThreeOrdinal;

    @ApiModelProperty(value = "科目三代码")
    private String subThreeCode;

    @ApiModelProperty(value = "科目三名称")
    private String subThreeName;

    @ApiModelProperty(value = "科目三备注")
    private String subThreeRemarks;

    @ApiModelProperty(value = "科目四序号")
    @NotBlank(message = "科目四序号不能为空")
    private String subFourOrdinal;

    @ApiModelProperty(value = "科目四代码")
    private String subFourCode;

    @ApiModelProperty(value = "科目四名称")
    private String subFourName;

    @ApiModelProperty(value = "科目四备注")
    private String subFourRemarks;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private Integer status;

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
