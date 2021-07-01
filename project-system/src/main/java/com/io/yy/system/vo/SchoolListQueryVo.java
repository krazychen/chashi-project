package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value = "SysSchoolQueryVo对象", description = "学校表查询参数")
public class SchoolListQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long schoolId;

    @ApiModelProperty(value = "学校代码")
    private String label;

    @ApiModelProperty(value = "学校名称")
    private String value;


}