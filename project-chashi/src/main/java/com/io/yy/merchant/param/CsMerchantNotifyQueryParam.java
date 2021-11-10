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
import java.util.Date;

/**
 * <pre>
 * 商家通知人员记录 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-11-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsMerchantNotifyQueryParam对象", description = "商家通知人员记录查询参数")
public class CsMerchantNotifyQueryParam extends OrderQueryParam {
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

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private String status;

    @ApiModelProperty(value = "查询开始时间")
    private Date queryStartTime;

    @ApiModelProperty(value = "查询结束时间")
    private Date queryEndTime;
}
