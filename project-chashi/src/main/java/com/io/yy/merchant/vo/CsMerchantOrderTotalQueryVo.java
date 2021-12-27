package com.io.yy.merchant.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 商家订单统计 vo
 * </pre>
 *
 * @author kris
 * @date 2021-07-23
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CsMerchantOrderTotalQueryVo对象", description = "商家订单统计")
public class CsMerchantOrderTotalQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商店名称")
    private String merchantName;

    @ApiModelProperty(value = "预定日期")
    private Date orderDate;

    @ApiModelProperty(value = "订单总量")
    private int orderNums;

    @ApiModelProperty(value = "订单总价")
    private Double orderPrices;

    @ApiModelProperty(value = "用户数")
    private Double wxusers;

    @ApiModelProperty(value = "商户数")
    private Double merchantNums;
}