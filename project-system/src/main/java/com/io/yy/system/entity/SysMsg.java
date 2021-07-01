package com.io.yy.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import com.io.yy.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 系统消息表
 * </pre>
 *
 * @author zhaochao
 * @since 2019-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysMsg对象", description = "系统消息表")
public class SysMsg extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String msgTitle;

    @ApiModelProperty(value = "消息内容")
    @NotBlank(message = "消息内容不能为空")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String msgContent;

    @ApiModelProperty(value = "发布人id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long sendUserId;

    @ApiModelProperty(value = "发布人用户名")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String sendUserName;

    @ApiModelProperty(value = "优先级")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String msgLevelCode;

    @ApiModelProperty(value = "消息类型")
    @NotBlank(message = "消息类型不能为空")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String msgTypeCode;

    @ApiModelProperty(value = "通知方式，多个通知方式以逗号隔开")
    @NotBlank(message = "通知方式，多个通知方式以逗号隔开不能为空")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String msgNotifyTypeCode;

    @ApiModelProperty(value = "通知对象类型")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String msgNotifyObjCode;

    @ApiModelProperty(value = "指定用户")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String userIds;

    @ApiModelProperty(value = "指定角色")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String roleIds;

    @ApiModelProperty(value = "指定标签")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String classTypeIds;

    @ApiModelProperty(value = "指定班级")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String classIds;

    @ApiModelProperty(value = "指定班级题型")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String classSubjectIds;

    @ApiModelProperty(value = "指定作业")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String homeworkIds;

    @ApiModelProperty(value = "通知人数")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer notifiedNumber;

    @ApiModelProperty(value = "通知时间类型，立即，定时，数据字段")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String notifiedType;

    @ApiModelProperty(value = "通知时间，选择定时时，需要选择定时时间，不能小于当前时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date notifiedTime;

    @ApiModelProperty(value = "是否启用必读")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String mustRead;

    @ApiModelProperty(value = "必读时长，秒")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer mustReadTime;

    @ApiModelProperty(value = "必读有效时间，不能小于当前时间")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date mustReadDueTime;

    @ApiModelProperty(value = "发布状态")
    private String status;

    @ApiModelProperty(value = "发布时间")
    private Date publishTime;

    @ApiModelProperty(value = "撤销时间")
    private Date cancelTime;

    @ApiModelProperty(value = "微信模板消息配置id")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long configId;

    @ApiModelProperty(value = "超链接")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String hyperLinks;

    @ApiModelProperty(value = "逻辑删除，0：未删除，1：已删除")
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建者")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "租户代码")
    private String corpCode;

    @ApiModelProperty(value = "租户名称")
    private String corpName;

}
