package com.io.yy.wxmngt.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.wxmngt.entity.CsLabel;
import com.io.yy.wxmngt.param.CsLabelQueryParam;
import com.io.yy.wxmngt.vo.CsLabelQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 标签管理 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-07-08
 */
@Repository
public interface CsLabelMapper extends BaseMapper<CsLabel> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsLabelQueryVo getCsLabelById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csLabelQueryParam
     * @return
     */
    IPage<CsLabelQueryVo> getCsLabelPageList(@Param("page") Page page, @Param("param") CsLabelQueryParam csLabelQueryParam);

}
