package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;
import com.io.yy.common.param.OrderQueryParam;
import java.util.List;

/**
 * <pre>
 * 角色与菜单关联表 查询参数对象
 * </pre>
 *
 * @author chenPengfei
 * @date 2019-12-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysRoleMenuQueryParam对象", description = "角色与菜单关联表查询参数")
public class SysRoleMenuQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "角色id不能为空")
    private String id;

    @ApiModelProperty(value = "关联菜单集合")
    @NotNull(message = "菜单id不能为空")
    private List<String> idList;

}
