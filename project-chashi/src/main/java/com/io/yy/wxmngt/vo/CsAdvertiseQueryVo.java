package com.io.yy.wxmngt.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 广告设置 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-02
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CsAdvertiseQueryVo对象", description = "广告设置查询参数")
public class CsAdvertiseQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "图片url")
    private String picUrl;

    @ApiModelProperty(value = "图片名")
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

}