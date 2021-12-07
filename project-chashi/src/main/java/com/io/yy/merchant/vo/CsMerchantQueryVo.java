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

    @ApiModelProperty(value = "所在城市code")
    private String cityCode;

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

    @ApiModelProperty(value = "联系手机(客服电话)")
    private String contactPhonse;

    @ApiModelProperty(value = "标签id（多个，隔开）")
    private String labelId;

    @ApiModelProperty(value = "标签name（多个，隔开）")
    private String labelName;

    @ApiModelProperty(value = "设施id（多个，隔开）")
    private String facilitiesId;

    @ApiModelProperty(value = "设施名（多个，隔开）")
    private String facilitiesName;

    @ApiModelProperty(value = "服务项目id（多个，隔开）")
    private String servicesId;

    @ApiModelProperty(value = "服务项目名（多个，隔开）")
    private String servicesName;

    @ApiModelProperty(value = "订单退款手续费")
    private int orderRefundFee;

    @ApiModelProperty(value = "商户简介")
    private String merchantInfo;

    @ApiModelProperty(value = "使用须知")
    private String usageNotice;

    @ApiModelProperty(value = "开始营业时间")
    private String startTime;

    @ApiModelProperty(value = "结束营业时间")
    private String endTime;

    @ApiModelProperty(value = "用户和商店的距离")
    private Double merchantDistance;

    @ApiModelProperty(value = "ttlock开放平台注册时分配的app_id")
    private String ttlClientId;

    @ApiModelProperty(value = "ttlock开放平台注册时分配的app_secret")
    private String ttlClientSecret;

    @ApiModelProperty(value = "ttlock锁的id")
    private String ttlLockId;

    @ApiModelProperty(value = "科技侠或通通锁APP的登录账号")
    private String ttlUsername;

    @ApiModelProperty(value = "科技侠或通通锁APP的登录账号密码")
    private String ttlPassword;

    @ApiModelProperty(value = "曼顿空开client id")
    private String kkClientId;

    @ApiModelProperty(value = "曼顿空开kk_app_secret")
    private String kkAppSecret;

    @ApiModelProperty(value = "曼顿空开projectCode")
    private String kkProjectCode;

    @ApiModelProperty(value = "曼顿空开redirect_uri")
    private String kkRedirectUri;

    @ApiModelProperty(value = "曼顿空开uname")
    private String kkUname;

    @ApiModelProperty(value = "曼顿空开password")
    private String kkPassword;

    @ApiModelProperty(value = "第二个门锁开启后的提醒声音")
    private String txApi1;

    @ApiModelProperty(value = "结束前10分钟的提醒声音")
    private String txApi2;

    @ApiModelProperty(value = "结束前2分钟的提醒声音")
    private String txApi3;

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

    @ApiModelProperty(value = "已购买次数, 为空界面显示0，固定显示")
    private int buyRecord;

    private List<CsTearoomQueryVo> tearoomList;
}