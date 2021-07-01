package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

/**
 * <pre>
 * 考试范围表 查询参数对象
 * </pre>
 *
 * @author zhaochao
 * @date 2020-01-14
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysCollegeExamscopeQueryParam对象", description = "考试范围表查询参数")
public class SysCollegeExamscopeQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;
}
