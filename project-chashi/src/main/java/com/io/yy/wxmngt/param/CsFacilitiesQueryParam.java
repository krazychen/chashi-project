package com.io.yy.wxmngt.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 服务设施管理 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsFacilitiesQueryParam对象", description = "服务设施管理查询参数")
public class CsFacilitiesQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;
}
