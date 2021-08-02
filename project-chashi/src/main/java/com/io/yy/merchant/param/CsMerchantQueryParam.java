package com.io.yy.merchant.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

import javax.validation.constraints.NotBlank;

/**
 * <pre>
 * 商家管理 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-07-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsMerchantQueryParam对象", description = "商家管理查询参数")
public class CsMerchantQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商店名称")
    private String merchantName;

    @ApiModelProperty(value = "账户")
    private String merchantAccount;
}
