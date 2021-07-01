package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 系统消息表 查询结果对象
 * </pre>
 *
 * @author zhaochao
 * @date 2019-12-17
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysMsgQueryVo对象", description = "系统消息表查询参数")
public class SysMsgQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String msgTitle;

    @ApiModelProperty(value = "消息内容")
    private String msgContent;

    @ApiModelProperty(value = "发布人id")
    private Long sendUserId;

    @ApiModelProperty(value = "发布人用户名")
    private String sendUserName;

    @ApiModelProperty(value = "优先级")
    private String msgLevelCode;

    @ApiModelProperty(value = "消息类型")
    private String msgTypeCode;

    @ApiModelProperty(value = "通知方式，多个通知方式以逗号隔开")
    private String msgNotifyTypeCode;

    @ApiModelProperty(value = "通知对象类型")
    private String msgNotifyObjCode;

    @ApiModelProperty(value = "指定用户")
    private String userIds;

    @ApiModelProperty(value = "指定角色")
    private String roleIds;

    @ApiModelProperty(value = "指定标签")
    private String classTypeIds;

    @ApiModelProperty(value = "指定班级")
    private String classIds;

    @ApiModelProperty(value = "指定班级题型")
    private String classSubjectIds;

    @ApiModelProperty(value = "指定作业")
    private String homeworkIds;

    @ApiModelProperty(value = "通知人数")
    private Integer notifiedNumber;

    @ApiModelProperty(value = "通知时间类型，立即，定时，数据字段")
    private String notifiedType;

    @ApiModelProperty(value = "通知时间，选择定时时，需要选择定时时间，不能小于当前时间")
    private Date notifiedTime;

    @ApiModelProperty(value = "是否启用必读")
    private String mustRead;

    @ApiModelProperty(value = "必读时长，秒")
    private Integer mustReadTime;

    @ApiModelProperty(value = "必读有效时间，不能小于当前时间")
    private Date mustReadDueTime;

    @ApiModelProperty(value = "发布状态")
    private String status;

    @ApiModelProperty(value = "发布时间")
    private Date publishTime;

    @ApiModelProperty(value = "撤销时间")
    private Date cancelTime;

    @ApiModelProperty(value = "微信模板消息配置id")
    private Long configId;

    @ApiModelProperty(value = "超链接")
    private String hyperLinks;

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

    @ApiModelProperty(value = "消息记录表Id")
    private Long msgRecordId;

    @ApiModelProperty(value = "消息记录读取状态")
    private String redStatus;

    @ApiModelProperty(value = "通知人数")
    private Integer msgNumber;

    @ApiModelProperty(value = "通知对象")
    private String msgNotifyObj;

    @ApiModelProperty(value = "未读人数")
    private Integer noReadNumber;

}