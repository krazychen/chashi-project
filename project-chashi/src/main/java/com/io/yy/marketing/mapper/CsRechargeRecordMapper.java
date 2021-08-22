package com.io.yy.marketing.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.io.yy.marketing.entity.CsRechargeRecord;
import com.io.yy.marketing.param.CsRechargeRecordQueryParam;
import com.io.yy.marketing.vo.CsRechargeRecordQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * <pre>
 * 充值记录 Mapper 接口
 * </pre>
 *
 * @author kris
 * @since 2021-08-18
 */
@Repository
public interface CsRechargeRecordMapper extends BaseMapper<CsRechargeRecord> {

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     */
    CsRechargeRecordQueryVo getCsRechargeRecordById(Serializable id);

    /**
     * 获取分页对象
     *
     * @param page
     * @param csRechargeRecordQueryParam
     * @return
     */
    IPage<CsRechargeRecordQueryVo> getCsRechargeRecordPageList(@Param("page") Page page, @Param("param") CsRechargeRecordQueryParam csRechargeRecordQueryParam);

    /**
     * 更新状态
     *
     * @param csRechargeRecordQueryParam
     * @return
     */
    Integer updateStatus(@Param("param") CsRechargeRecordQueryParam csRechargeRecordQueryParam);

    /**
     * 更新支付状态
     *
     * @param csRechargeRecordQueryParam
     * @return
     */
    Integer updatePaymentStatus(@Param("param") CsRechargeRecordQueryParam csRechargeRecordQueryParam);

    /**
     * 根据outTradeNo获取查询对象
     *
     * @param outTradeNo
     * @return
     */
    CsRechargeRecordQueryVo getCsRechargeRecordByOutTradeNo(String outTradeNo);
}
