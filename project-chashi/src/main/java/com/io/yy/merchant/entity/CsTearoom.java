package com.io.yy.merchant.entity;

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
 * 茶室管理
 * </pre>
 *
 * @author kris
 * @since 2021-08-04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsTearoom对象", description = "茶室管理")
public class CsTearoom extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "茶室名称")
    @NotBlank(message = "茶室名称不能为空")
    private String roomName;

    @ApiModelProperty(value = "设施id（多个，隔开）")
    private String facilitiesId;

    @ApiModelProperty(value = "设施名（多个，隔开）")
    private String facilitiesName;

    @ApiModelProperty(value = "预定小时金额")
    private Double hoursAmount;

    @ApiModelProperty(value = "起订时间")
    private String startTime;

    @ApiModelProperty(value = "预订时间间隔")
    private String timeRange;

    @ApiModelProperty(value = "建议使用人数")
    private String recomNumUsers;

    @ApiModelProperty(value = "茶室封面url")
    private String roomLogoUrl;

    @ApiModelProperty(value = "茶室封面name")
    private String roomLogoName;

    @ApiModelProperty(value = "茶室banner url")
    private String roomBannerUrl;

    @ApiModelProperty(value = "茶室banner name")
    private String roomBannerName;

    @ApiModelProperty(value = "订单开门方式")
    private Integer doorOpenMethod;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "商店id")
    @NotNull(message = "商店id不能为空")
    private Long merchantId;

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

    @ApiModelProperty(value = "上传logo图片文件对象")
    @JsonIgnore
    @TableField(exist = false)
    private MultipartFile[] logoUploadFile;

    @ApiModelProperty(value = "上传banner图片文件对象")
    @JsonIgnore
    @TableField(exist = false)
    private MultipartFile[] bannerUploadFile;

    @ApiModelProperty(value = "更新时上传的新增logo图片文件对象")
    @JsonIgnore
    @TableField(exist = false)
    private MultipartFile[] logoUploadFileAdd;

    @ApiModelProperty(value = "更新时上传的删除logo图片文件名称")
    @TableField(exist = false)
    private String[] logoUploadFileDel;

    @ApiModelProperty(value = "更新时上传的新增banner图片文件对象")
    @JsonIgnore
    @TableField(exist = false)
    private MultipartFile[] bannerUploadFileAdd;

    @ApiModelProperty(value = "更新时上传的删除banner图片文件名称")
    @TableField(exist = false)
    private String[] bannerUploadFileDel;

}
