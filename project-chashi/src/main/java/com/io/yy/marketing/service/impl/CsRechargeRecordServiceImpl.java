package com.io.yy.marketing.service.impl;

import com.io.yy.marketing.entity.CsRechargeRecord;
import com.io.yy.marketing.mapper.CsRechargeRecordMapper;
import com.io.yy.marketing.service.CsRechargeRecordService;
import com.io.yy.marketing.param.CsRechargeRecordQueryParam;
import com.io.yy.marketing.vo.CsRechargeRecordQueryVo;
import com.io.yy.common.service.impl.BaseServiceImpl;
import com.io.yy.common.vo.Paging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.List;


/**
 * <pre>
 * 充值记录 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-08-18
 */
@Slf4j
@Service
public class CsRechargeRecordServiceImpl extends BaseServiceImpl<CsRechargeRecordMapper, CsRechargeRecord> implements CsRechargeRecordService {

    @Autowired
    private CsRechargeRecordMapper csRechargeRecordMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsRechargeRecord(CsRechargeRecord csRechargeRecord) throws Exception {
        return super.save(csRechargeRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsRechargeRecord(CsRechargeRecord csRechargeRecord) throws Exception {
        return super.updateById(csRechargeRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsRechargeRecord(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsRechargeRecords(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsRechargeRecordQueryVo getCsRechargeRecordById(Serializable id) throws Exception {
        return csRechargeRecordMapper.getCsRechargeRecordById(id);
    }

    @Override
    public Paging<CsRechargeRecordQueryVo> getCsRechargeRecordPageList(CsRechargeRecordQueryParam csRechargeRecordQueryParam) throws Exception {
        Page page = setPageParam(csRechargeRecordQueryParam, OrderItem.desc("create_time"));
        IPage<CsRechargeRecordQueryVo> iPage = csRechargeRecordMapper.getCsRechargeRecordPageList(page, csRechargeRecordQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateStatus(CsRechargeRecordQueryParam csRechargeRecordQueryParam) {
        return csRechargeRecordMapper.updateStatus(csRechargeRecordQueryParam) > 0;
    }

    /**
     * 更新支付状态
     *
     * @param csRechargeRecordQueryParam
     * @return
     */
    @Override
    public boolean updatePaymentStatus(CsRechargeRecordQueryParam csRechargeRecordQueryParam) {
        return csRechargeRecordMapper.updatePaymentStatus(csRechargeRecordQueryParam) > 0;
    }

    /**
     * 根据outTradeNo获取查询对象
     *
     * @param outTradeNo
     * @return
     * @throws Exception
     */
    @Override
    public CsRechargeRecordQueryVo getCsRechargeRecordByOutTradeNo(String outTradeNo) throws Exception {
        return csRechargeRecordMapper.getCsRechargeRecordByOutTradeNo(outTradeNo);
    }
}
