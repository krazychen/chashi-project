package com.io.yy.merchant.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.io.yy.merchant.entity.CsMerchant;
import com.io.yy.common.service.BaseService;
import com.io.yy.merchant.param.CsMerchantQueryParam;
import com.io.yy.merchant.param.CsTearoomQueryParam;
import com.io.yy.merchant.vo.CsMerchantOrderTotalQueryVo;
import com.io.yy.merchant.vo.CsMerchantQueryVo;
import com.io.yy.common.vo.Paging;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <pre>
 * 商家管理 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-07-23
 */
public interface CsMerchantService extends BaseService<CsMerchant> {

    /**
     * 保存
     *
     * @param csMerchant
     * @return
     * @throws Exception
     */
    boolean saveCsMerchant(CsMerchant csMerchant) throws Exception;

    /**
     * 保存商店、租户和用户信息
     *
     * @param csMerchant
     * @return
     * @throws Exception
     */
    boolean saveCsMerchantAOffice (CsMerchant csMerchant) throws Exception;

    /**
     * 修改
     *
     * @param csMerchant
     * @return
     * @throws Exception
     */
    boolean updateCsMerchant(CsMerchant csMerchant) throws Exception;


    /**
     * 修改商店、租户和用户信息
     *
     * @param csMerchant
     * @return
     * @throws Exception
     */
    boolean updateCsMerchantAOffice(CsMerchant csMerchant) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsMerchant(Long id) throws Exception;

    /**
     * 删除商店、租户和用户信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsMerchantAOffice(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsMerchants(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsMerchantQueryVo getCsMerchantById(Long id) throws Exception;

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
     * @param csMerchantQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsMerchantQueryVo> getCsMerchantPageList(CsMerchantQueryParam csMerchantQueryParam) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csMerchantQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsMerchantQueryVo> getCsMerchantPageListForWx(CsMerchantQueryParam csMerchantQueryParam) throws Exception;


    /**
     * 通过ID更新status
     *
     * @param csMerchantQueryParam
     * @return
     * @throws Exception
     */
    public Boolean updateStatusById(CsMerchantQueryParam csMerchantQueryParam);

    /**
     * wx根据ID获取查询对象
     *
     * @param csMerchantQueryParam
     * @return
     * @throws Exception
     */
    CsMerchantQueryVo getCsMerchantByIdForWx(CsMerchantQueryParam csMerchantQueryParam) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csMerchantQueryParam
     * @return
     */
    Paging<CsMerchantOrderTotalQueryVo> getCsMerchantOrderTotal(@Param("param") CsMerchantQueryParam csMerchantQueryParam) throws Exception;

    /**
     * 获取统计数据
     *
     * @return
     * @throws Exception
     */
    CsMerchantOrderTotalQueryVo getCsMerchantTotalStatical() throws Exception;

    /**
     * 更新营业状态
     *
     * @param csMerchantQueryParam
     * @return
     */
    boolean updateReleaseStatus(CsMerchantQueryParam csMerchantQueryParam);
}
