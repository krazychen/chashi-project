package com.io.yy.merchant.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 茶室管理 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsTearoomQueryParam对象", description = "茶室管理查询参数")
public class CsTearoomQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "茶室名称")
    private String roomName;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private String status;

    @ApiModelProperty(value = "商店id")
    private Long merchantId;
}
