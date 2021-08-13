package com.io.yy.merchant.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 商店茶室订单记录 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsMerchantOrderQueryParam对象", description = "商店茶室订单记录查询参数")
public class CsMerchantOrderQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;
}
