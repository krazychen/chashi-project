package com.io.yy.marketing.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.marketing.entity.CsMembercardOrder;
import com.io.yy.marketing.param.CsMembercardOrderQueryParam;
import com.io.yy.marketing.vo.CsMembercardOrderQueryVo;
import com.io.yy.merchant.param.CsMerchantOrderQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 会员卡购买记录 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-08-12
 */
@Repository
public interface CsMembercardOrderMapper extends BaseMapper<CsMembercardOrder> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsMembercardOrderQueryVo getCsMembercardOrderById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csMembercardOrderQueryParam
     * @return
     */
    IPage<CsMembercardOrderQueryVo> getCsMembercardOrderPageList(@Param("page") Page page, @Param("param") CsMembercardOrderQueryParam csMembercardOrderQueryParam);

    /**
     * 更新状态
     *
     * @param csMembercardOrderQueryParam
     * @return
     */
    Integer updateStatus(@Param("param") CsMembercardOrderQueryParam csMembercardOrderQueryParam);

    /**
     * 检查是否购买过会员卡，购买过则不能再购买;
     *
     * @param csMembercardOrderQueryParam
     * @return
     */
    CsMembercardOrderQueryVo isExistCsMembercardOrderByUserId(@Param("param") CsMembercardOrderQueryParam csMembercardOrderQueryParam);

    /**
     * 更新支付状态
     *
     * @param csMembercardOrderQueryParam
     * @return
     */
    Integer updatePaymentStatus(@Param("param") CsMembercardOrderQueryParam csMembercardOrderQueryParam);

    /**
     * 减少剩余优惠时长和优惠金额
     *
     * @param csMembercardOrderQueryParam
     * @return
     */
    Integer reduceRest(@Param("param") CsMembercardOrderQueryParam csMembercardOrderQueryParam);

    /**
     * 增加剩余优惠时长和优惠金额
     *
     * @param csMembercardOrderQueryParam
     * @return
     */
    Integer addRest(@Param("param") CsMembercardOrderQueryParam csMembercardOrderQueryParam);
}
