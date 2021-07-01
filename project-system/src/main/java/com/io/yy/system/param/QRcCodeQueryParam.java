package com.io.yy.system.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/5/21
 **/

@Data
@Accessors(chain = true)
@ApiModel(value = "QRcCodeQueryParam对象", description = "公众号二维码请求参数")
public class QRcCodeQueryParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。")
    private int expire_seconds;

    @ApiModelProperty(value = "二维码类型，QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值，QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值")
    private String action_name;

    @ApiModelProperty(value = "二维码详细信息")
    private Map<String,Object> action_info;
}
