package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 学校专业表 查询参数对象
 * </pre>
 *
 * @author chenPengfei
 * @date 2019-12-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysSchoolCollegeQueryParam对象", description = "学校专业表查询参数")
public class SysSchoolCollegeQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "专业代码")
    private String collegeCode;

    @ApiModelProperty(value = "专业名称")
    private String collegeName;

    @ApiModelProperty(value = "学校id")
    @NotNull
    private String schoolId;
}
