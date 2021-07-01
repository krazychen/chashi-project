package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 学校专业表 查询结果对象
 * </pre>
 *
 * @author chenPengfei
 * @date 2019-12-08
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysSchoolCollegeQueryVo对象", description = "学校专业表查询参数")
public class SysSchoolCollegeQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "学校id")
    private Long schoolId;

    @ApiModelProperty(value = "专业代码")
    private String collegeCode;

    @ApiModelProperty(value = "专业名称")
    private String collegeName;

    @ApiModelProperty(value = "专业介绍")
    private String collegeInfo;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "院系所代码")
    private String facultyDepartmentCode;

    @ApiModelProperty(value = "院系所名称")
    private String facultyDepartmentName;

    @ApiModelProperty(value = "研究方向代码")
    private String researchDirectionCode;

    @ApiModelProperty(value = "研究方向名称")
    private String researchDirectionName;

    @ApiModelProperty(value = "学科类别")
    private String subjectCategory;

    @ApiModelProperty(value = "学科类别名称")
    private String subjectCategoryName;

    @ApiModelProperty(value = "专业门类")
    private String degreeType;

    @ApiModelProperty(value = "专业门类名称")
    private String degreeTypeName;

    @ApiModelProperty(value = "学习方式")
    private String studyMethod;

    @ApiModelProperty(value = "学习方式名称")
    private String studyMethodName;

    @ApiModelProperty(value = "考试方式")
    private String examMethod;

    @ApiModelProperty(value = "考试方式名称")
    private String examMethodName;

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