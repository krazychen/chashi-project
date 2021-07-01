package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Hello World Controller
 *
 * @author zhao
 * @date 2019/11/28
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "SysOfficeTreeQueryVo对象", description = "菜单树形结构")
public class SysOfficeTreeQueryVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "机构编码")
    private String id;

    @ApiModelProperty(value = "下级机构集合")
    private List<SysOfficeQueryVo> children;

    @ApiModelProperty(value = "机构名称")
    private String label;
}
