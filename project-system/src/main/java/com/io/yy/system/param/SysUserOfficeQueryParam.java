package com.io.yy.system.param;

import com.io.yy.common.param.OrderQueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 用户和机构关联表 查询参数对象
 * </pre>
 *
 * @author zhaochao
 * @date 2019-11-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysUserOfficeQueryParam对象", description = "用户和机构关联表查询参数")
public class SysUserOfficeQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;
}
