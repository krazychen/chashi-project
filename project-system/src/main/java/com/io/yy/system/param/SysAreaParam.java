package com.io.yy.system.param;

import com.io.yy.util.excel.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2019/12/10
 **/

@Data
@Accessors(chain = true)
@ApiModel(value = "SysSchool对象", description = "学校表")
public class SysAreaParam implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "省份直辖市代码")
    @ExcelField(title="省份直辖市代码", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=1)
    private String provinceCode;

    @ApiModelProperty(value = "省份直辖市代码")
    @ExcelField(title="省份直辖市代码", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=2)
    private String provinceName;

    @ApiModelProperty(value = "省份直辖市代码")
    @ExcelField(title="省份直辖市代码", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=3)
    private String cityCode;

    @ApiModelProperty(value = "省份直辖市代码")
    @ExcelField(title="省份直辖市代码", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=4)
    private String cityName;

    @ApiModelProperty(value = "省份直辖市代码")
    @ExcelField(title="省份直辖市代码", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=5)
    private String districtCode;

    @ApiModelProperty(value = "省份直辖市代码")
    @ExcelField(title="省份直辖市代码", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=6)
    private String districtName;

    @ApiModelProperty(value = "排序")
    @ExcelField(title="排序", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=7)
    private String treeSort;
}
