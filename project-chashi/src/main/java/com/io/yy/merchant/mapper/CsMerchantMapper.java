package com.io.yy.merchant.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.merchant.entity.CsMerchant;
import com.io.yy.merchant.param.CsMerchantQueryParam;
import com.io.yy.merchant.vo.CsMerchantQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 商家管理 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-07-23
 */
@Repository
public interface CsMerchantMapper extends BaseMapper<CsMerchant> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsMerchantQueryVo getCsMerchantById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csMerchantQueryParam
     * @return
     */
    IPage<CsMerchantQueryVo> getCsMerchantPageList(@Param("page") Page page, @Param("param") CsMerchantQueryParam csMerchantQueryParam);

}
