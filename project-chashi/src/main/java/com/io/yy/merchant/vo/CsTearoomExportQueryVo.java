package com.io.yy.merchant.vo;

import com.io.yy.util.excel.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 茶室管理 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-04
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CsTearoomQueryVo对象", description = "茶室管理查询参数")
public class CsTearoomExportQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "茶室名称")
    @ExcelField(title="茶室名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=1)
    private String roomName;

    @ApiModelProperty(value = "设施id（多个，隔开）")
    @ExcelField(title="服务设施", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=2)
    private String facilitiesId;

    @ApiModelProperty(value = "小时金额")
    @ExcelField(title="小时金额", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=3)
    private Integer hoursAmount;

    @ApiModelProperty(value = "起订时间")
    @ExcelField(title="起订时间", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=4)
    private String startTime;

    @ApiModelProperty(value = "建议使用人数")
    @ExcelField(title="建议使用人数", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=5)
    private String recomNumUsers;

    @ApiModelProperty(value = "茶室封面url")
    @ExcelField(title="茶室封面", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=6)
    private String roomLogoUrl;

    @ApiModelProperty(value = "茶室banner url")
    @ExcelField(title="茶室banner", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=7)
    private String roomBannerUrl;

    @ApiModelProperty(value = "订单开门方式")
    @ExcelField(title="订单开门方式", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=8)
    private Integer doorOpenMethod;

    @ApiModelProperty(value = "排序")
    @ExcelField(title="排序", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=9)
    private Integer sort;

    @ApiModelProperty(value = "状态，0：禁用，1：启用，2：锁定")
    @ExcelField(title="状态", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=10)
    private String status;

}