package com.io.yy.wxmngt.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.merchant.param.CsMerchantQueryParam;
import com.io.yy.wxmngt.entity.CsAdvertise;
import com.io.yy.wxmngt.param.CsAdvertiseQueryParam;
import com.io.yy.wxmngt.vo.CsAdvertiseQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 广告设置 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-08-02
 */
@Repository
public interface CsAdvertiseMapper extends BaseMapper<CsAdvertise> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsAdvertiseQueryVo getCsAdvertiseById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csAdvertiseQueryParam
     * @return
     */
    IPage<CsAdvertiseQueryVo> getCsAdvertisePageList(@Param("page") Page page, @Param("param") CsAdvertiseQueryParam csAdvertiseQueryParam);

    Integer updateStatusById(@Param("param") CsAdvertiseQueryParam csAdvertiseQueryParam);

    List<CsAdvertiseQueryVo> getCsAdvertiseForWx();
}
