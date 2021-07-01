package com.io.yy.job.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Hello World Controller
 *
 * @author chenPengFei
 * @date 2020/5/15
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "CheckSchoolCodeParam对象", description = "验证用学校代码是否重复参数")
public class CheckSchoolCodeParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学校id")
    private Long id;

    @ApiModelProperty(value = "学校代码")
    @NotBlank(message = "学校代码不能为空")
    private String schoolCode;
}
