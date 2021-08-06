package com.io.yy.wxops.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 微信用户 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WxUserQueryParam对象", description = "微信用户查询参数")
public class WxUserQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;
}
