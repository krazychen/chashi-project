package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 2 * @Author: zhao
 * 3 * @Date: 2020/1/14 16:32
 * 4
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CollegeExamscopeVo对象", description = "考试范围表查询参数")
public class CollegeExamscopeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "序号")
    private String xh;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "科目代码")
    private String kmdm;

    @ApiModelProperty(value = "科目名称")
    private String kmmc;

    @ApiModelProperty(value = "备注")
    private String bz;

}
