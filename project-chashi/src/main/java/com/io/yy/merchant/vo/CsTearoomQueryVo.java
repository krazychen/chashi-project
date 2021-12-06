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
    private Double hoursAmount;

    @ApiModelProperty(value = "起订时间，值为1到10,1是1个小时，2个2个小时，以此类推")
    private String startTime;

    @ApiModelProperty(value = "预订时间间隔")
    private String timeRange;

    @ApiModelProperty(value = "建议使用人数")
    private String recomNumUsers;

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

    @ApiModelProperty(value = "茶室门锁id")
    private String rttlLockId;

    @ApiModelProperty(value = "曼顿空开mac")
    private String kkMac;

    @ApiModelProperty(value = "曼顿空开线路")
    private String kkOcSwitch;

    @ApiModelProperty(value = "茶室星级，默认值5,1~5星，固定显示")
    private int rate = 5;

    @ApiModelProperty(value = "已购买次数, 为空界面显示0，固定显示")
    private int buyRecord;

    @ApiModelProperty(value = "会员优惠金额，若有值显示会员金额，若为0或者空，则界面不显示")
    private Double menberAmount;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "商店id")
    private Long merchantId;

    @ApiModelProperty(value = "商店obj")
    private CsMerchantQueryVo merchant;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private String status;

    @ApiModelProperty(value = "营业状态，0：歇业，1：营业")
    private String releaseStatus;

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