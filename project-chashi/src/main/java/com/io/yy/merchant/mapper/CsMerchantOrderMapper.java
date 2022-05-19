package com.io.yy.merchant.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.merchant.entity.CsMerchantOrder;
import com.io.yy.merchant.param.CsMerchantOrderQueryParam;
import com.io.yy.merchant.vo.CsMerchantOrderQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 商店茶室订单记录 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-08-12
 */
@Repository
public interface CsMerchantOrderMapper extends BaseMapper<CsMerchantOrder> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsMerchantOrderQueryVo getCsMerchantOrderById(Serializable id);

    /**
     * 根据ID获取查询详细对象
     *
     * @param id
     * @return
     */
    CsMerchantOrderQueryVo getCsMerchantOrderDetailById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csMerchantOrderQueryParam
     * @return
     */
    IPage<CsMerchantOrderQueryVo> getCsMerchantOrderPageList(@Param("page") Page page, @Param("param") CsMerchantOrderQueryParam csMerchantOrderQueryParam);

    /**
     * 更新状态
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    Integer updateStatus(@Param("param") CsMerchantOrderQueryParam csMerchantOrderQueryParam);

    /**
     * 更新使用状态
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    Integer updateUsedStatus(@Param("param") CsMerchantOrderQueryParam csMerchantOrderQueryParam);

    /**
     * 根据tearoomid和预订日期获取当前茶室已经被预定的时间段，返回是时间段list 的一个包含","的字符串
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    List<String> getTimeRangeForWx(@Param("param") CsMerchantOrderQueryParam csMerchantOrderQueryParam);


    /**
     * 根据当前时间获取是否有订单，有订单返回订单id，没有订单返回null
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    Long getOrderByCurrent(@Param("param") CsMerchantOrderQueryParam csMerchantOrderQueryParam);

    /**
     * 更新支付状态
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    Integer updatePaymentStatus(@Param("param") CsMerchantOrderQueryParam csMerchantOrderQueryParam);

    /**
     * 更新开锁密码
     *
     * @param csMerchantOrderQueryParam
     * @return
     */
    Integer updateLockKey(@Param("param") CsMerchantOrderQueryParam csMerchantOrderQueryParam);
}
