package com.io.yy.marketing.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 会员卡购买记录 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-12
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CsMembercardOrderQueryVo对象", description = "会员卡购买记录查询参数")
public class CsMembercardOrderQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "会员卡id")
    private Long membercardId;

    @ApiModelProperty(value = "会员卡名称")
    private String membercardName;

    @ApiModelProperty(value = "微信用户ID")
    private Long wxuserId;

    @ApiModelProperty(value = "微信用户手机")
    private String wxuserPhone;

    @ApiModelProperty(value = "微信用户openID")
    private String openid;

    @ApiModelProperty(value = "商品名称，会员卡名称+预订手机+预订日期+uuid")
    private String orderName;

    @ApiModelProperty(value = "购买日期")
    private Date orderDate;

    @ApiModelProperty(value = "有效期")
    private Integer validPeriod;

    @ApiModelProperty(value = "会员卡开始时间")
    private Date startTime;

    @ApiModelProperty(value = "会员卡结束时间")
    private Date endTime;

    @ApiModelProperty(value = "实际总价")
    private Double orderPrice;

    @ApiModelProperty(value = "32位的UUID")
    private String outTradeNo;

    @ApiModelProperty(value = "支付状态：支付中、支付失败、支付成功")
    private Integer paymentStatus;

    @ApiModelProperty(value = "支付失败错误消息")
    private String paymentMsg;

    @ApiModelProperty(value = "支付类型：余额支付、微信支付")
    private Integer paymentType;

    @ApiModelProperty(value = "会员卡来源：系统发放0、用户购买1")
    private Integer sourceType;

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

    @ApiModelProperty(value = "微信用户昵称")
    private String wxuserName;

    @ApiModelProperty(value = "会员卡级别")
    private String level;

    @ApiModelProperty(value = "会员折扣")
    private Double discountOff;

    @ApiModelProperty(value = "优惠金额")
    private Double discountPrice;

    @ApiModelProperty(value = "优惠时长")
    private Double discountTime;

    @ApiModelProperty(value = "封面url")
    private String logoUrl;

    @ApiModelProperty(value = "封面name")
    private String logoName;

    @ApiModelProperty(value = "使用须知")
    private String usageNotice;

    @ApiModelProperty(value = "用户权益")
    private String useRights;

}