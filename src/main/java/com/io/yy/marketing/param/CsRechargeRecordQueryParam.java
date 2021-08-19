package com.io.yy.marketing.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 充值记录 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsRechargeRecordQueryParam对象", description = "充值记录查询参数")
public class CsRechargeRecordQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;
}
