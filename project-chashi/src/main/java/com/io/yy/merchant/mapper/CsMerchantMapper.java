package com.io.yy.merchant.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.merchant.entity.CsMerchant;
import com.io.yy.merchant.param.CsMerchantQueryParam;
import com.io.yy.merchant.vo.CsMerchantOrderTotalQueryVo;
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

    Integer updateStatusById(@Param("param") CsMerchantQueryParam csMerchantQueryParam);

    /**
     * 根据office code获取查询对象
     *
     * @param officeCode
     * @return
     * @throws Exception
     */
    CsMerchantQueryVo getCsMerchantByOfficeCode(String officeCode) throws Exception;

    /**
     * 获取分页对象
     *
     * @param page
     * @param csMerchantQueryParam
     * @return
     */
    IPage<CsMerchantQueryVo> getCsMerchantPageListOrderByPriceASC(@Param("page") Page page, @Param("param") CsMerchantQueryParam csMerchantQueryParam);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csMerchantQueryParam
     * @return
     */
    IPage<CsMerchantQueryVo> getCsMerchantPageListOrderByPriceDESC(@Param("page") Page page, @Param("param") CsMerchantQueryParam csMerchantQueryParam);

    /**
     * 获取分页对象
     *
     * @param csMerchantQueryParam
     * @return
     */
    IPage<CsMerchantQueryVo> getCsMerchantPageListOrderByNear(@Param("page") Page page, @Param("param") CsMerchantQueryParam csMerchantQueryParam);

    /**
     * 获取分页对象
     *
     * @param csMerchantQueryParam
     * @return
     */
    IPage<CsMerchantQueryVo> getCsMerchantPageListOrderByDefault(@Param("page") Page page, @Param("param") CsMerchantQueryParam csMerchantQueryParam);

    /**
     * wx获取商店详情
     *
     * @param id
     * @return
     */
    CsMerchantQueryVo getCsMerchantByIdForWx(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csMerchantQueryParam
     * @return
     */
    IPage<CsMerchantOrderTotalQueryVo> getCsMerchantOrderTotal(@Param("page") Page page, @Param("param") CsMerchantQueryParam csMerchantQueryParam);

    /**
     * 获取统计数据
     *
     * @return
     */
    CsMerchantOrderTotalQueryVo getCsMerchantTotalStatical();


    /**
     * 更新营业状态
     *
     * @param csMerchantQueryParam
     * @return
     */
    Integer updateReleaseStatus(@Param("param") CsMerchantQueryParam csMerchantQueryParam);
}
