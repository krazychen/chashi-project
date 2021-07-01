package com.io.yy.system.param;

import com.io.yy.common.param.OrderQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <pre>
 * 老师表 查询参数对象
 * </pre>
 *
 * @author zhaochao
 * @date 2019-12-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UpdateUserParam", description = "用户参数")
public class UpdateUserParam extends OrderQueryParam {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "昵称")
    private String nickname;

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

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "办公电话")
    private String mobile;

    @ApiModelProperty(value = "性别，0：女，1：男，默认1")
    private Integer gender;

    @ApiModelProperty(value = "学校id")
    private Long schoolId;

    @ApiModelProperty(value = "学校代码")
    private String schoolCode;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    @ApiModelProperty(value = "专业id")
    private Long collegeId;

    @ApiModelProperty(value = "专业代码")
    private String collegeCode;

    @ApiModelProperty(value = "专业名称")
    private String collegeName;
}
