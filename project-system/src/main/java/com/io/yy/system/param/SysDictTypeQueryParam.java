package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 字典类型表 查询参数对象
 * </pre>
 *
 * @author chenPengfei
 * @date 2019-11-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysDictTypeQueryParam对象", description = "字典类型表查询参数")
public class SysDictTypeQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典类型名称")
    private String dictName;

    @ApiModelProperty(value = "字典类型状态")
    private String status;

    @ApiModelProperty(value = "备注")
    private String dictType;

    @ApiModelProperty(value = "备注")
    private String remarks;

}
