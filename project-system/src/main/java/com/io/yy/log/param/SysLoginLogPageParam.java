
package com.io.yy.log.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 系统登录日志 分页参数对象
 * </pre>
 *
 * @author kris
 * @date 2020-03-24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统登录日志分页参数")
public class SysLoginLogPageParam extends BasePageOrderParam {
    private static final long serialVersionUID = 1L;
}
