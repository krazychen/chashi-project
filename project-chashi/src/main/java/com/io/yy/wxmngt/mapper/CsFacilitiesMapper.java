package com.io.yy.wxmngt.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.wxmngt.entity.CsFacilities;
import com.io.yy.wxmngt.param.CsFacilitiesQueryParam;
import com.io.yy.wxmngt.vo.CsFacilitiesQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 服务设施管理 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-08-04
 */
@Repository
public interface CsFacilitiesMapper extends BaseMapper<CsFacilities> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsFacilitiesQueryVo getCsFacilitiesById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csFacilitiesQueryParam
     * @return
     */
    IPage<CsFacilitiesQueryVo> getCsFacilitiesPageList(@Param("page") Page page, @Param("param") CsFacilitiesQueryParam csFacilitiesQueryParam);

    /**
     * 更新状态
     *
     * @param csFacilitiesQueryParam
     * @return
     */
    Integer updateStatus(@Param("param") CsFacilitiesQueryParam csFacilitiesQueryParam);


}
