package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/4/21
 **/

@Data
@Accessors(chain = true)
@ApiModel(value = "SysSchoolCollegeQueryParam对象", description = "学校专业表查询参数")
public class SysSchoolParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "省份编码")
    private String provinceCode;

    @ApiModelProperty(value = "城市编码")
    private String cityCode;

    @ApiModelProperty(value = "县级编码")
    private String districtCode;
}
