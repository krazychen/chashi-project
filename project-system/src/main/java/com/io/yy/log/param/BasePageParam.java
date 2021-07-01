
package com.io.yy.log.param;

import com.io.yy.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询参数
 *
 * @author kris
 * @since 2018-11-08
 */
@Data
@ApiModel("查询参数对象")
public abstract class BasePageParam implements Serializable {
    private static final long serialVersionUID = -3263921252635611410L;

    @ApiModelProperty(value = "页码,默认为1", example = "1")
    private Integer pageIndex = CommonConstant.DEFAULT_PAGE_INDEX;

    @ApiModelProperty(value = "页大小,默认为10", example = "10")
    private Integer pageSize = CommonConstant.DEFAULT_PAGE_SIZE;

    @ApiModelProperty(value = "搜索字符串", example = "")
    private String keyword;

    public void setPageIndex(Integer pageIndex) {
        if (pageIndex == null || pageIndex <= 0) {
            this.pageIndex = CommonConstant.DEFAULT_PAGE_INDEX;
        } else {
            this.pageIndex = pageIndex;
        }
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize <= 0) {
            this.pageSize = CommonConstant.DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

}
