package com.io.yy.marketing.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 优惠卷发放/领取记录 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsCouponReleasedQueryParam对象", description = "优惠卷发放/领取记录查询参数")
public class CsCouponReleasedQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "优惠卷id")
    private String couponId;

    @ApiModelProperty(value = "用户ID")
    private Integer wxuserId;
}
