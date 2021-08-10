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
 * 会员卡
 * </pre>
 *
 * @author kris
 * @since 2021-08-09
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsMemberCard对象", description = "会员卡")
public class CsMemberCard extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "会员名称")
    private String cardname;

    @ApiModelProperty(value = "会员卡价格")
    @NotNull(message = "会员卡价格不能为空")
    private Double price;

    @ApiModelProperty(value = "封面url")
    private String logoUrl;

    @ApiModelProperty(value = "封面name")
    private String logoName;

    @ApiModelProperty(value = "级别")
    private Integer level;

    @ApiModelProperty(value = "会员折扣")
    private Double discountOff;

    @ApiModelProperty(value = "优惠金额")
    private Double discountPrice;

    @ApiModelProperty(value = "优惠时长")
    private Double discountTime;

    @ApiModelProperty(value = "有效期")
    private Integer validPeriod;

    @ApiModelProperty(value = "使用须知")
    private String usageNotice;

    @ApiModelProperty(value = "用户权益")
    private String useRights;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

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
