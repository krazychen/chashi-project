package com.io.yy.wxmngt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.io.yy.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <pre>
 * 广告设置
 * </pre>
 *
 * @author kris
 * @since 2021-08-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsAdvertise对象", description = "广告设置")
public class CsAdvertise extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "主键不能为空")
    private Long id;

    @ApiModelProperty(value = "图片url")
    @NotBlank(message = "图片url不能为空")
    private String picUrl;

    @ApiModelProperty(value = "图片名")
    @NotBlank(message = "图片名不能为空")
    private String picName;

    @ApiModelProperty(value = "跳转链接")
    private String jumpLink;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private String status;

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

    @ApiModelProperty(value = "上传图片文件对象")
    @JsonIgnore
    @TableField(exist = false)
    private MultipartFile[] uploadFile;

    @ApiModelProperty(value = "更新时上传的新增图片文件对象")
    @JsonIgnore
    @TableField(exist = false)
    private MultipartFile[] uploadFileAdd;

    @ApiModelProperty(value = "更新时上传的删除logo图片文件名称")
    @TableField(exist = false)
    private String[] uploadFileDel;

}
