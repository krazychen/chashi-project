package com.io.yy.system.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/5/22
 **/

@Data
@Accessors(chain = true)
@ApiModel(value = "AccessTokenParam对象", description = "公众号accessToken请求参数")
public class MessageTextEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @XStreamAlias("ToUserName")
    private String toUserName;
    @XStreamAlias("FromUserName")
    private String fromUserName;
    //由于微信服务端需要的时间整形是以秒为单位的，故需要除以1000L
    // this.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
    @XStreamAlias("CreateTime")
    private String createTime;
    @XStreamAlias("MsgType")
    private String msgType;
    @XStreamAlias("Content")
    private String content;
    @XStreamAlias("MsgId")
    private Long msgId;

}
