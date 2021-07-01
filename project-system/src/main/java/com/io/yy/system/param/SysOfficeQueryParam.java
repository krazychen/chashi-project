package com.io.yy.system.param;

import com.io.yy.common.param.OrderQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 组织机构表 查询参数对象
 * </pre>
 *
 * @author zhaochao
 * @date 2019-11-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysOfficeQueryParam对象", description = "组织机构表查询参数")
public class SysOfficeQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构名称")
    private String officeName;

    @ApiModelProperty(value = "机构类型")
    private String officeType;

    @ApiModelProperty(value = "机构编码")
    private String officeCode;

    @ApiModelProperty(value = "父级编号")
    private String parentCode;
}
