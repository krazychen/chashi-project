package com.io.yy.wxmngt.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 广告设置 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsAdvertiseQueryParam对象", description = "广告设置查询参数")
public class CsAdvertiseQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private String status;
}
