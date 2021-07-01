package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 系统消息记录表 查询结果对象
 * </pre>
 *
 * @author zhaochao
 * @date 2019-12-17
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysMsgRecordQueryVo对象", description = "系统消息记录表查询参数")
public class SysMsgRecordQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "系统消息id")
    private Long msgId;

    @ApiModelProperty(value = "通知方式")
    private String msgNotifyTypeCode;

    @ApiModelProperty(value = "接受者用户id")
    private Long receiveUserId;

    @ApiModelProperty(value = "接受者用户用户名")
    private String receiveUserName;

    @ApiModelProperty(value = "优先级")
    private String msgLevelCode;

    @ApiModelProperty(value = "消息类型")
    private String msgTypeCode;

    @ApiModelProperty(value = "读取状态")
    private String status;

    @ApiModelProperty(value = "阅读时间")
    private Date readTime;

    @ApiModelProperty(value = "是否标星")
    private String isStar;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "租户代码")
    private String corpCode;

    @ApiModelProperty(value = "租户名称")
    private String corpName;

}