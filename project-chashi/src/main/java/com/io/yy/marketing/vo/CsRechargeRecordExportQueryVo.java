package com.io.yy.marketing.vo;

import com.io.yy.util.excel.annotation.ExcelField;
import com.io.yy.util.excel.fieldtype.DateTimeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 充值记录 查询结果对象
 * </pre>
 *
 * @author kris
 * @date 2021-08-18
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CsRechargeRecordExportQueryVo对象", description = "充值记录导出")
public class CsRechargeRecordExportQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "微信用户名称")
    @ExcelField(title="用户名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=1)
    private String wxuserName;

    @ApiModelProperty(value = "微信用户手机")
    @ExcelField(title="用户手机", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=2)
    private String wxuserPhone;

    @ApiModelProperty(value = "微信用户openID")
    @ExcelField(title="用户openID", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=3)
    private String openid;

    @ApiModelProperty(value = "充值金额")
    @ExcelField(title="充值金额", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=4)
    private Double rechargeAmount;

    @ApiModelProperty(value = "赠送金额")
    @ExcelField(title="赠送金额", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=5)
    private Double rechargeGived;

    @ApiModelProperty(value = "最终金额")
    @ExcelField(title="最终金额", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=6)
    private Double rechargeFinal;

    @ApiModelProperty(value = "商品名称，充值+金额+预订日期+uuid")
    @ExcelField(title="商品名称", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=7)
    private String orderName;

    @ApiModelProperty(value = "购买日期")
    @ExcelField(title="购买日期", type= ExcelField.Type.ALL ,fieldType= DateTimeType.class,align= ExcelField.Align.CENTER, sort=8)
    private Date orderDate;

    @ApiModelProperty(value = "32位的UUID")
    @ExcelField(title="微信交易号", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=9)
    private String outTradeNo;

    @ApiModelProperty(value = "支付状态：支付中0、支付失败1、支付成功2，支付关闭3，充值失败后就将交易关闭，每次都是最新的")
    private Integer paymentStatus;

    @ApiModelProperty(value = "支付状态：支付中0、支付失败1、支付成功2，支付取消3，支付失败4")
    @ExcelField(title="支付状态", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=10)
    private String paymentStatusName;

    @ApiModelProperty(value = "支付失败错误消息")
    @ExcelField(title="支付失败错误消息", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=11)
    private String paymentMsg;

    @ApiModelProperty(value = "会员卡来源：系统发放0、用户购买1")
    private Integer sourceType;

    @ApiModelProperty(value = "会员卡来源：系统发放0、用户购买1")
    @ExcelField(title="会员卡来源", type= ExcelField.Type.ALL ,align= ExcelField.Align.CENTER, sort=12)
    private String sourceTypeName;
}