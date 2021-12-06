package com.io.yy.merchant.service;

import com.io.yy.merchant.entity.CsMerchantOrder;
import com.io.yy.common.service.BaseService;
import com.io.yy.merchant.param.CsMerchantOrderQueryParam;
import com.io.yy.merchant.vo.CsMerchantOrderQueryVo;
import com.io.yy.common.vo.Paging;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 商店茶室订单记录 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-08-12
 */
public interface CsMerchantOrderService extends BaseService<CsMerchantOrder> {

    /**
     * 保存
     *
     * @param csMerchantOrder
     * @return
     * @throws Exception
     */
    boolean saveCsMerchantOrder(CsMerchantOrder csMerchantOrder) throws Exception;

    /**
     * 修改
     *
     * @param csMerchantOrder
     * @return
     * @throws Exception
     */
    boolean updateCsMerchantOrder(CsMerchantOrder csMerchantOrder) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsMerchantOrder(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsMerchantOrders(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsMerchantOrderQueryVo getCsMerchantOrderById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csMerchantOrderQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsMerchantOrderQueryVo> getCsMerchantOrderPageList(CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception;

    /**
     * 更新状态
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    boolean updateStatus(CsMerchantOrderQueryParam csMerchantOrderQueryParam);

    /**
     * 根据tearoomid和预订日期获取当前茶室已经被预定的时间段，返回是时间段的一个包含","的字符串
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    String getTimeRangeForWx(@Param("param") CsMerchantOrderQueryParam csMerchantOrderQueryParam);

    /**
     * 更新支付状态
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    boolean updatePaymentStatus(@Param("param") CsMerchantOrderQueryParam csMerchantOrderQueryParam);

    /**
     * 返回订单的门锁key
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    String getLockKey(CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception;

    /**
     * 一键开锁
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    String openLock(CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception;

    /**
     * 一键开房门
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    String openRoomLock(CsMerchantOrderQueryParam csMerchantOrderQueryParam) throws Exception;

    Boolean saveCsMerchantOrderForWX(CsMerchantOrder csMerchantOrder) throws Exception;

    void orderPayRedis(String out_trade_no, String orderId) throws Exception;
}
