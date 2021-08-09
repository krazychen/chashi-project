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
 * 商家管理
 * </pre>
 *
 * @author kris
 * @since 2021-07-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CsMerchant对象", description = "商家管理")
public class CsMerchant extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "主键不能为空")
    private Long id;

    @ApiModelProperty(value = "商店名称")
    @NotBlank(message = "商店名称不能为空")
    private String merchantName;

    @ApiModelProperty(value = "账户")
    @NotBlank(message = "账户不能为空")
    private String merchantAccount;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String merchantPassword;

    @ApiModelProperty(value = "订单手续费")
    private Integer orderFee;

    @ApiModelProperty(value = "所在城市code")
    private String cityCode;

    @ApiModelProperty(value = "所在城市")
    private String city;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "精度")
    private String longitude;

    @ApiModelProperty(value = "维度")
    private String latitude;

    @ApiModelProperty(value = "商家logo value")
    private String logoUrlValue;

    @ApiModelProperty(value = "商家logo value")
    private String logoUrlName;

    @ApiModelProperty(value = "商家轮播图 value")
    private String carouselUrlValue;

    @ApiModelProperty(value = "商家轮播图 name")
    private String carouselUrlName;

    @ApiModelProperty(value = "officeCode")
    private String officeCode;

    @ApiModelProperty(value = "联系手机(客服电话)")
    private String contactPhonse;

    @ApiModelProperty(value = "标签id（多个，隔开）")
    private String labelId;

    @ApiModelProperty(value = "标签name（多个，隔开）")
    private String labelName;

    @ApiModelProperty(value = "设施id（多个，隔开）")
    private String facilitiesId;

    @ApiModelProperty(value = "设施名（多个，隔开）")
    private String facilitiesName;

    @ApiModelProperty(value = "服务项目id（多个，隔开）")
    private String servicesId;

    @ApiModelProperty(value = "服务项目名（多个，隔开）")
    private String servicesName;

    @ApiModelProperty(value = "订单退费手续费")
    private int orderRefundFee;

    @ApiModelProperty(value = "商户简介")
    private String merchantInfo;

    @ApiModelProperty(value = "使用须知")
    private String usageNotice;

    @ApiModelProperty(value = "开始营业时间")
    private String startTime;

    @ApiModelProperty(value = "结束营业时间")
    private String endTime;

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
