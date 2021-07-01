package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <pre>
 * 学校表 查询结果对象
 * </pre>
 *
 * @author zhaochao
 * @date 2019-12-09
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysSchoolQueryVo对象", description = "学校专业表查询参数")
public class SchoolCollegeListQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "专业id")
    private Long id;

    @ApiModelProperty(value = "专业代码")
    private String label;

    @ApiModelProperty(value = "专业名称")
    private String value;

}