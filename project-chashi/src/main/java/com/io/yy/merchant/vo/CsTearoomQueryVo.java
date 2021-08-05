package com.io.yy.merchant.vo;

import com.io.yy.merchant.entity.CsMerchant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 茶室管理 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-04
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CsTearoomQueryVo对象", description = "茶室管理查询参数")
public class CsTearoomQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "茶室名称")
    private String roomName;

    @ApiModelProperty(value = "设施id（多个，隔开）")
    private String facilitiesId;

    @ApiModelProperty(value = "设施名（多个，隔开）")
    private String facilitiesName;

    @ApiModelProperty(value = "小时金额")
    private Integer hoursAmount;

    @ApiModelProperty(value = "起订时间")
    private String startTime;

    @ApiModelProperty(value = "建议使用人数")
    private Integer recomNumUsers;

    @ApiModelProperty(value = "茶室封面url")
    private String roomLogoUrl;

    @ApiModelProperty(value = "茶室封面name")
    private String roomLogoName;

    @ApiModelProperty(value = "茶室banner url")
    private String roomBannerUrl;

    @ApiModelProperty(value = "茶室banner name")
    private String roomBannerName;

    @ApiModelProperty(value = "订单开门方式")
    private Integer doorOpenMethod;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "商店id")
    private Long merchantId;

    @ApiModelProperty(value = "商店obj")
    private CsMerchantQueryVo merchant;

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