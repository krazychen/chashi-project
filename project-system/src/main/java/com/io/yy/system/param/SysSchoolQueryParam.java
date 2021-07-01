package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 学校表 查询参数对象
 * </pre>
 *
 * @author chenPengfei
 * @date 2019-12-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysSchoolQueryParam对象", description = "学校表查询参数")
public class SysSchoolQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学校编码")
    private String schoolCode;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;
}
