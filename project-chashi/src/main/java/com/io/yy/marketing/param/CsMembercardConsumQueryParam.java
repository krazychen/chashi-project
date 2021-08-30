package com.io.yy.marketing.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 会员卡消费记录 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsMembercardConsumQueryParam对象", description = "会员卡消费记录查询参数")
public class CsMembercardConsumQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;
}
