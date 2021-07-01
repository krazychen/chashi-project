package com.io.yy.system.param;

import com.io.yy.common.param.OrderQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <pre>
 * 用户和角色关联表 查询参数对象
 * </pre>
 *
 * @author zhaochao
 * @date 2019-11-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysUserRoleQueryParam对象", description = "用户和角色关联表查询参数")
public class SysUserRoleQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "角色id不能为空")
    private Long id;

    @ApiModelProperty(value = "关联菜单集合")
    @NotNull(message = "菜单id不能为空")
    private List<Long> idList;
}
