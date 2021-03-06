package com.io.yy.marketing.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 优惠卷 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsCouponQueryParam对象", description = "优惠卷查询参数")
public class CsCouponQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "优惠卷名称")
    private String couponName;

    @ApiModelProperty(value = "获取方式")
    private Integer releasedMethod;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private String status;
}
