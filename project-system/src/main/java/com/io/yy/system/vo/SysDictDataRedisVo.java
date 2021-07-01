package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 字典数据表 查询结果对象
 * </pre>
 *
 * @author chenPengfei
 * @date 2019-11-23
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysDictDataRedisVo对象", description = "字典数据表缓存查询参数")
public class SysDictDataRedisVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典标签")
    private String dictLabel;

    @ApiModelProperty(value = "字典键值")
    private String dictValue;

    @ApiModelProperty(value = "字典类型")
    private String dictType;
}