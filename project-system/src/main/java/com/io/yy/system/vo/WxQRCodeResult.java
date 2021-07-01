package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/5/21
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "WxQRCodeResult对象", description = "二维码返回结果")
public class WxQRCodeResult implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码")
    private String ticket;

    @ApiModelProperty(value = "该二维码有效时间，以秒为单位。 最大不超过2592000")
    private Integer expire_seconds;

    @ApiModelProperty(value = "二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片")
    private String url;
}
