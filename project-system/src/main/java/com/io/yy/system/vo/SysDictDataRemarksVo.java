package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/5/27
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "SysDictDataRemarksVo对象", description = "数据库中的data数据")
public class SysDictDataRemarksVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典标签")
    private String dictLabel;

    @ApiModelProperty(value = "字典键值")
    private String dictValue;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "字典类型")
    private String remarks;
}
