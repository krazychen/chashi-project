package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 考试范围表 查询结果对象
 * </pre>
 *
 * @author zhaochao
 * @date 2020-01-14
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysCollegeExamscopeQueryVo对象", description = "考试范围表查询参数")
public class SysCollegeExamscopeQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "专业id")
    private Long collegeId;

    @ApiModelProperty(value = "科目一序号")
    private String subOneOrdinal;

    @ApiModelProperty(value = "科目一代码")
    private String subOneCode;

    @ApiModelProperty(value = "科目一名称")
    private String subOneName;

    @ApiModelProperty(value = "科目一备注")
    private String subOneRemarks;

    @ApiModelProperty(value = "科目二序号")
    private String subTwoOrdinal;

    @ApiModelProperty(value = "科目二代码")
    private String subTwoCode;

    @ApiModelProperty(value = "科目二名称")
    private String subTwoName;

    @ApiModelProperty(value = "科目二备注")
    private String subTwoRemarks;

    @ApiModelProperty(value = "科目三序号")
    private String subThreeOrdinal;

    @ApiModelProperty(value = "科目三代码")
    private String subThreeCode;

    @ApiModelProperty(value = "科目三名称")
    private String subThreeName;

    @ApiModelProperty(value = "科目三备注")
    private String subThreeRemarks;

    @ApiModelProperty(value = "科目四序号")
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