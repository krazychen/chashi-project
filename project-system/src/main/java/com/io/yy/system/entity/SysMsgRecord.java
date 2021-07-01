package com.io.yy.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 系统消息记录表
 * </pre>
 *
 * @author zhaochao
 * @since 2019-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysMsgRecord对象", description = "系统消息记录表")
public class SysMsgRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @ApiModelProperty(value = "系统消息id")
    @NotNull(message = "系统消息id不能为空")
    private Long msgId;

    @ApiModelProperty(value = "通知方式")
    @NotBlank(message = "通知方式不能为空")
    private String msgNotifyTypeCode;

    @ApiModelProperty(value = "接受者用户id")
    @NotNull(message = "接受者用户id不能为空")
    private Long receiveUserId;

    @ApiModelProperty(value = "接受者用户用户名")
    @NotBlank(message = "接受者用户用户名不能为空")
    private String receiveUserName;

    @ApiModelProperty(value = "优先级")
    private String msgLevelCode;

    @ApiModelProperty(value = "消息类型")
    @NotBlank(message = "消息类型不能为空")
    private String msgTypeCode;

    @ApiModelProperty(value = "读取状态")
    private String status;

    @ApiModelProperty(value = "阅读时间")
    @NotNull(message = "阅读时间不能为空")
    private Date readTime;

    @ApiModelProperty(value = "是否标星")
    private String isStar;

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
