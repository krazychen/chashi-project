package com.io.yy.system.vo;

import com.io.yy.util.excel.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/5/19
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "NewSysSchoolVo对象", description = "导入学校模板数据")
public class NewSysSchoolVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学校代码")
    @ExcelField(title="省份名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=1)
    @NotBlank(message = "学校代码不能为空")
    private String schoolCode;

    @ApiModelProperty(value = "学校名称")
    @NotBlank(message = "学校名称不能为空")
    @ExcelField(title="学校名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=2)
    private String schoolName;

    @ApiModelProperty(value = "学校介绍")
    @ExcelField(title="省份名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=3)
    private String schoolInfo;

    @ApiModelProperty(value = "省份名称")
    @ExcelField(title="省份名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=4)
    private String provinceName;

    @ApiModelProperty(value = "城市名称")
    @ExcelField(title="城市名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=5)
    private String cityName;

    @ApiModelProperty(value = "区县名称")
    @ExcelField(title="区县名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=6)
    private String districtName;

    @ApiModelProperty(value = "地址")
    @ExcelField(title="地址", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=7)
    private String address;

    @ApiModelProperty(value = "备注信息")
    @ExcelField(title="地址", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=8)
    private String remarks;

    @ApiModelProperty(value = "院系所名称")
    @ExcelField(title="院系所名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=9)
    @NotBlank(message = "院系所名称不能为空")
    private String facultyDepartmentName;

    @ApiModelProperty(value = "专业名称")
    @ExcelField(title="专业名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=10)
    @NotBlank(message = "专业名称不能为空")
    private String collegeName;

    @ApiModelProperty(value = "研究方向名称")
    @ExcelField(title="研究方向名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=11)
    @NotBlank(message = "研究方向名称不能为空")
    private String researchDirectionName;

    @ApiModelProperty(value = "考试方式名称")
    @ExcelField(title="考试方式名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=12)
    @NotNull(message = "考试方式名称不能为空")
    private String examMethodName;

    @ApiModelProperty(value = "学科门类名称")
    @ExcelField(title="学科门类名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=13)
    @NotNull(message = "学科门类名称称不能为空")
    private String subjectCategory;

    @ApiModelProperty(value = "学习方式名称")
    @ExcelField(title="学习方式名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=14)
    @NotNull(message = "学习方式名称不能为空")
    private String studyMethodName;

    @ApiModelProperty(value = "科目一名称")
    @ExcelField(title="科目一名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=15)
    private String subOneName;

    @ApiModelProperty(value = "科目二名称")
    @ExcelField(title="科目二名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=16)
    private String subTwoName;

    @ApiModelProperty(value = "科目三名称")
    @ExcelField(title="科目三名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=17)
    private String subThreeName;

    @ApiModelProperty(value = "科目四名称")
    @ExcelField(title="科目四名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=18)
    private String subFourName;
}
