package com.io.yy.merchant.service;

import com.io.yy.merchant.entity.CsMerchantNotify;
import com.io.yy.common.service.BaseService;
import com.io.yy.merchant.param.CsMerchantNotifyQueryParam;
import com.io.yy.merchant.vo.CsMerchantNotifyQueryVo;
import com.io.yy.common.vo.Paging;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 商家通知人员记录 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-11-03
 */
public interface CsMerchantNotifyService extends BaseService<CsMerchantNotify> {

    /**
     * 保存
     *
     * @param csMerchantNotify
     * @return
     * @throws Exception
     */
    boolean saveCsMerchantNotify(CsMerchantNotify csMerchantNotify) throws Exception;

    /**
     * 修改
     *
     * @param csMerchantNotify
     * @return
     * @throws Exception
     */
    boolean updateCsMerchantNotify(CsMerchantNotify csMerchantNotify) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsMerchantNotify(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsMerchantNotifys(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsMerchantNotifyQueryVo getCsMerchantNotifyById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csMerchantNotifyQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsMerchantNotifyQueryVo> getCsMerchantNotifyPageList(CsMerchantNotifyQueryParam csMerchantNotifyQueryParam) throws Exception;

    /**
     * 更新状态
     *
     * @param csMerchantNotifyQueryParam
     * @return
     */
     boolean updateStatus(CsMerchantNotifyQueryParam csMerchantNotifyQueryParam);


}
