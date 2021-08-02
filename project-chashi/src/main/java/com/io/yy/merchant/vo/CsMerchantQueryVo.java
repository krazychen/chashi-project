package com.io.yy.merchant.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 商家管理 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2021-07-23
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CsMerchantQueryVo对象", description = "商家管理查询参数")
public class CsMerchantQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商店名称")
    private String merchantName;

    @ApiModelProperty(value = "账户")
    private String merchantAccount;

    @ApiModelProperty(value = "密码")
    private String merchantPassword;

    @ApiModelProperty(value = "订单手续费")
    private Integer orderFee;

    @ApiModelProperty(value = "所在城市")
    private String city;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "精度")
    private String longitude;

    @ApiModelProperty(value = "维度")
    private String latitude;

    @ApiModelProperty(value = "商家logo value")
    private String logoUrlValue;

    @ApiModelProperty(value = "商家logo value")
    private String logoUrlName;

    @ApiModelProperty(value = "商家轮播图 value")
    private String carouselUrlValue;

    @ApiModelProperty(value = "商家轮播图 name")
    private String carouselUrlName;

    @ApiModelProperty(value = "officeCode")
    private String officeCode;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private String status;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "租户代码")
    private String corpCode;

    @ApiModelProperty(value = "租户名称")
    private String corpName;

}