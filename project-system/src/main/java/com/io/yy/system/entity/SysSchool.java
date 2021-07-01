package com.io.yy.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.io.yy.common.entity.BaseEntity;
import com.io.yy.util.excel.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * <pre>
 * 学校表
 * </pre>
 *
 * @author chenPengfei
 * @since 2019-12-08
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysSchool对象", description = "学校表")
public class SysSchool extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.NONE)
    private Long id;

    @ApiModelProperty(value = "学校代码")
    @ExcelField(title="省份名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=1)
    @NotBlank(message = "学校代码不能为空")
    private String schoolCode;

    @ApiModelProperty(value = "学校名称")
    @ExcelField(title="学校名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=3)
    private String schoolName;

    @ApiModelProperty(value = "省份代码")
    private String provinceCode;

    @ApiModelProperty(value = "省份名称")
    @ExcelField(title="省份名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=4)
    private String provinceName;

    @ApiModelProperty(value = "城市代码")
    private String cityCode;

    @ApiModelProperty(value = "城市名称")
    @ExcelField(title="城市名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=5)
    private String cityName;

    @ApiModelProperty(value = "区县代码")
    private String districtCode;

    @ApiModelProperty(value = "区县名称")
    @ExcelField(title="区县名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=6)
    private String districtName;

    @ApiModelProperty(value = "地址")
    @ExcelField(title="地址", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=7)
    private String address;

    @ApiModelProperty(value = "学校介绍")
    @ExcelField(title="省份名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=3)
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
