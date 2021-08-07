package com.io.yy.wxops.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.io.yy.common.param.OrderQueryParam;

import javax.validation.constraints.NotBlank;

/**
 * <pre>
 * 微信用户 查询参数对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "WxUserQueryParam对象", description = "微信用户查询参数")
public class WxUserQueryParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号码")
    private String phone;
}
