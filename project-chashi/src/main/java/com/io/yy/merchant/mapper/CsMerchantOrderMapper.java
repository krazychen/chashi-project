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


}
