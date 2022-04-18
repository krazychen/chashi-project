package com.io.yy.merchant.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 商家管理 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-07-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsMerchantQueryParam对象", description = "商家管理查询参数")
public class CsMerchantQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商店名称")
    private String merchantName;

    @ApiModelProperty(value = "商店主键")
    private Long merchantId;

    @ApiModelProperty(value = "账户")
    private String merchantAccount;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private String status;

    @ApiModelProperty(value = "排序方式，1：智能、按照距离、价格、时间，2：最近距离，默认5公里，3：按照价格从小到大，4：按照价格从大到小")
    private String sortType;

    @ApiModelProperty(value = "用户精度")
    private String userLng;

    @ApiModelProperty(value = "用户维度")
    private String userLat;

    @ApiModelProperty(value = "附近的商店的默认公里数10KM")
    private int distance = 10000;

    @ApiModelProperty(value = "城市代码")
    private String cityCode;

    @ApiModelProperty(value = "附近最小lng")
    private double minlng;

    @ApiModelProperty(value = "附近最大lng")
    private double maxlng;

    @ApiModelProperty(value = "附近最小lat")
    private double minlat;

    @ApiModelProperty(value = "附近最大lat")
    private double maxlat;

    @ApiModelProperty(value = "微信公众号openid")
    private String openid;

    @ApiModelProperty(value = "营业状态，0：歇业，1：营业")
    private String releaseStatus;
}
