package com.io.yy.marketing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 会员卡 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-09
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CsMemberCardQueryVo对象", description = "会员卡查询参数")
public class CsMemberCardQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "会员名称")
    private String cardname;

    @ApiModelProperty(value = "会员卡价格")
    private Double price;

    @ApiModelProperty(value = "封面url")
    private String logoUrl;

    @ApiModelProperty(value = "封面name")
    private String logoName;

    @ApiModelProperty(value = "级别")
    private Integer level;

    @ApiModelProperty(value = "会员折扣")
    private Double discountOff;

    @ApiModelProperty(value = "优惠金额")
    private Double discountPrice;

    @ApiModelProperty(value = "优惠时长")
    private Double discountTime;

    @ApiModelProperty(value = "有效期")
    private Integer validPeriod;

    @ApiModelProperty(value = "使用须知")
    private String usageNotice;

    @ApiModelProperty(value = "用户权益")
    private String useRights;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

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