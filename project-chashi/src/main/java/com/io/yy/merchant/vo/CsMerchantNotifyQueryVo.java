package com.io.yy.merchant.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 商家通知人员记录 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2021-11-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CsMerchantNotifyQueryVo对象", description = "商家通知人员记录查询参数")
public class CsMerchantNotifyQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商店id")
    private Long merchantId;

    @ApiModelProperty(value = "值班开始时间")
    private Date startTime;

    @ApiModelProperty(value = "值班结束时间")
    private Date endTime;

    @ApiModelProperty(value = "通知类型，0：保洁，1：值班")
    private Integer notifyType;

    @ApiModelProperty(value = "通知时点，0：预定后，1：消费结束")
    private Integer notifyTime;

    @ApiModelProperty(value = "XX前XX分钟")
    private Integer notifyFrontTime;

    @ApiModelProperty(value = "XX后XX分钟")
    private Integer notifyRearTime;

    @ApiModelProperty(value = "微信用户ID")
    private Long wxuserId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "微信用户open ID")
    private String openid;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private String status;

    @ApiModelProperty(value = "备注")
    private String remark;

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