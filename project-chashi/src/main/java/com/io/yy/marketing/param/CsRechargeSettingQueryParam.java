package com.io.yy.marketing.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 充值设置 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsRechargeSettingQueryParam对象", description = "充值设置查询参数")
public class CsRechargeSettingQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private String status;
}
