package com.io.yy.marketing.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 优惠卷发放/领取记录 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-10
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CsCouponReleasedQueryVo对象", description = "优惠卷发放/领取记录查询参数")
public class CsCouponReleasedQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "优惠卷id")
    private Long couponId;

    @ApiModelProperty(value = "用户ID")
    private Long wxuserId;

    @ApiModelProperty(value = "领取时间")
    private Date releasedTime;

    @ApiModelProperty(value = "是否被使用0未被使用，1已使用")
    private Integer isUsed;

    @ApiModelProperty(value = "使用时间")
    private Date usedTime;

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