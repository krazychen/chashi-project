package com.io.yy.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.io.yy.common.entity.BaseEntity;
import com.io.yy.util.excel.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <pre>
 * 学校专业表
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysSchoolCollege对象", description = "学校专业表")
public class SysSchoolCollege extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.NONE)
    private Long id;

    @ApiModelProperty(value = "学校id")
    @NotNull(message = "学校id不能为空")
    private Long schoolId;

    @ApiModelProperty(value = "专业代码")
    @ExcelField(title="专业代码", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=1)
    @NotBlank(message = "专业代码不能为空")
    private String collegeCode;

    @ApiModelProperty(value = "专业名称")
    @ExcelField(title="专业名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=2)
    @NotBlank(message = "专业名称不能为空")
    private String collegeName;

    @ApiModelProperty(value = "专业介绍")
    private String collegeInfo;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "院系所代码")
    @ExcelField(title="院系所代码", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=3)
    @NotBlank(message = "院系所代码不能为空")
    private String facultyDepartmentCode;

    @ApiModelProperty(value = "院系所名称")
    @ExcelField(title="院系所名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=4)
    @NotBlank(message = "院系所名称不能为空")
    private String facultyDepartmentName;

    @ApiModelProperty(value = "研究方向代码")
    @ExcelField(title="研究方向代码", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=5)
    @NotBlank(message = "研究方向代码不能为空")
    private String researchDirectionCode;

    @ApiModelProperty(value = "研究方向名称")
    @ExcelField(title="研究方向名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=6)
    @NotBlank(message = "研究方向名称不能为空")
    private String researchDirectionName;

    @ApiModelProperty(value = "学科类别id")
    @ExcelField(title="学科类别id", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=7)
    private String subjectCategory;

    @ApiModelProperty(value = "学科类别名称")
    @NotNull(message = "学科类别名称不能为空")
    private String subjectCategoryName;

    @ApiModelProperty(value = "专业门类")
    @ExcelField(title="专业门类", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=8)
    private String degreeType;

    @ApiModelProperty(value = "专业门类名称")
    @NotNull(message = "专业门类名称不能为空")
    private String degreeTypeName;

    @ApiModelProperty(value = "学习方式")
    @ExcelField(title="学习方式", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=9)
    private String studyMethod;

    @ApiModelProperty(value = "学习方式名称")
    @NotNull(message = "学习方式名称不能为空")
    private String studyMethodName;

    @ApiModelProperty(value = "考试方式")
    @ExcelField(title="考试方式", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=10)
    private String examMethod;

    @ApiModelProperty(value = "考试方式名称")
    @NotNull(message = "考试方式名称不能为空")
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
