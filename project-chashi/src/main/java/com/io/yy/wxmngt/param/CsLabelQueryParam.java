package com.io.yy.wxmngt.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 标签管理 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-07-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsLabelQueryParam对象", description = "标签管理查询参数")
public class CsLabelQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;
}
