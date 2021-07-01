package com.io.yy.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;

import java.util.Date;

/**
 * <pre>
 * 学校表 查询结果对象
 * </pre>
 *
 * @author chenPengfei
 * @date 2019-12-08
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysSchoolQueryVo对象", description = "学校表查询参数")
public class SysSchoolQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "学校代码")
    private String schoolCode;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "省份代码")
    private String provinceCode;

    @ApiModelProperty(value = "省份名称")
    private String provinceName;

    @ApiModelProperty(value = "城市代码")
    private String cityCode;

    @ApiModelProperty(value = "城市名称")
    private String cityName;

    @ApiModelProperty(value = "区县代码")
    private String districtCode;

    @ApiModelProperty(value = "区县名称")
    private String districtName;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "学校介绍")
    private String schoolInfo;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    private Integer status;

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