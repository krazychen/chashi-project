package com.io.yy.merchant.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.merchant.entity.CsMerchantNotify;
import com.io.yy.merchant.param.CsMerchantNotifyQueryParam;
import com.io.yy.merchant.vo.CsMerchantNotifyQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 商家通知人员记录 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-11-03
 */
@Repository
public interface CsMerchantNotifyMapper extends BaseMapper<CsMerchantNotify> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsMerchantNotifyQueryVo getCsMerchantNotifyById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csMerchantNotifyQueryParam
     * @return
     */
    IPage<CsMerchantNotifyQueryVo> getCsMerchantNotifyPageList(@Param("page") Page page, @Param("param") CsMerchantNotifyQueryParam csMerchantNotifyQueryParam);

    /**
     * 更新状态
     *
     * @param csMerchantNotifyQueryParam
     * @return
     */
    Integer updateStatus(@Param("param") CsMerchantNotifyQueryParam csMerchantNotifyQueryParam);


}
