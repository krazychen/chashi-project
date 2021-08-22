package com.io.yy.marketing.service;

import com.io.yy.marketing.entity.CsRechargeRecord;
import com.io.yy.common.service.BaseService;
import com.io.yy.marketing.param.CsRechargeRecordQueryParam;
import com.io.yy.marketing.vo.CsRechargeRecordQueryVo;
import com.io.yy.common.vo.Paging;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 充值记录 服务类
 * </pre>
 *
 * @author kris
 * @since 2021-08-18
 */
public interface CsRechargeRecordService extends BaseService<CsRechargeRecord> {

    /**
     * 保存
     *
     * @param csRechargeRecord
     * @return
     * @throws Exception
     */
    boolean saveCsRechargeRecord(CsRechargeRecord csRechargeRecord) throws Exception;

    /**
     * 修改
     *
     * @param csRechargeRecord
     * @return
     * @throws Exception
     */
    boolean updateCsRechargeRecord(CsRechargeRecord csRechargeRecord) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteCsRechargeRecord(Long id) throws Exception;

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * @throws Exception
     */
    boolean deleteCsRechargeRecords(List<String> idList) throws Exception;

    /**
     * 根据ID获取查询对象
     *
     * @param id
     * @return
     * @throws Exception
     */
    CsRechargeRecordQueryVo getCsRechargeRecordById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     *
     * @param csRechargeRecordQueryParam
     * @return
     * @throws Exception
     */
    Paging<CsRechargeRecordQueryVo> getCsRechargeRecordPageList(CsRechargeRecordQueryParam csRechargeRecordQueryParam) throws Exception;

    /**
     * 更新状态
     *
     * @param csRechargeRecordQueryParam
     * @return
     */
     boolean updateStatus(CsRechargeRecordQueryParam csRechargeRecordQueryParam);

    /**
     * 更新支付状态
     *
     * @param csRechargeRecordQueryParam
     * @return
     */
    boolean updatePaymentStatus(CsRechargeRecordQueryParam csRechargeRecordQueryParam);

    /**
     * 根据outTradeNo获取查询对象
     *
     * @param outTradeNo
     * @return
     * @throws Exception
     */
    CsRechargeRecordQueryVo getCsRechargeRecordByOutTradeNo(String outTradeNo) throws Exception;
}
