package com.io.yy.marketing.service;

import com.io.yy.marketing.entity.CsMembercardOrder;
import com.io.yy.common.service.BaseService;
import com.io.yy.marketing.param.CsMembercardOrderQueryParam;
import com.io.yy.marketing.vo.CsMembercardOrderQueryVo;
import com.io.yy.common.vo.Paging;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 会员卡购买记录 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-08-12
 */
public interface CsMembercardOrderService extends BaseService<CsMembercardOrder> {

    /**
     * 保存
     *
     * @param csMembercardOrder
     * @return
     * @throws Exception
     */
    boolean saveCsMembercardOrder(CsMembercardOrder csMembercardOrder) throws Exception;

    /**
     * 修改
     *
     * @param csMembercardOrder
     * @return
     * @throws Exception
     */
    boolean updateCsMembercardOrder(CsMembercardOrder csMembercardOrder) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsMembercardOrder(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsMembercardOrders(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsMembercardOrderQueryVo getCsMembercardOrderById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csMembercardOrderQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsMembercardOrderQueryVo> getCsMembercardOrderPageList(CsMembercardOrderQueryParam csMembercardOrderQueryParam) throws Exception;

    /**
     * 更新状态
     *
     * @param csMembercardOrderQueryParam
     * @return
     */
     boolean updateStatus(CsMembercardOrderQueryParam csMembercardOrderQueryParam);


}
