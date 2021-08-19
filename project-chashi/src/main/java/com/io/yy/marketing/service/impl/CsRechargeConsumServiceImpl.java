package com.io.yy.marketing.service.impl;

import com.io.yy.marketing.entity.CsRechargeConsum;
import com.io.yy.marketing.mapper.CsRechargeConsumMapper;
import com.io.yy.marketing.service.CsRechargeConsumService;
import com.io.yy.marketing.param.CsRechargeConsumQueryParam;
import com.io.yy.marketing.vo.CsRechargeConsumQueryVo;
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
 * 充值消费 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-08-18
 */
@Slf4j
@Service
public class CsRechargeConsumServiceImpl extends BaseServiceImpl<CsRechargeConsumMapper, CsRechargeConsum> implements CsRechargeConsumService {

    @Autowired
    private CsRechargeConsumMapper csRechargeConsumMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsRechargeConsum(CsRechargeConsum csRechargeConsum) throws Exception {
        return super.save(csRechargeConsum);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsRechargeConsum(CsRechargeConsum csRechargeConsum) throws Exception {
        return super.updateById(csRechargeConsum);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsRechargeConsum(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsRechargeConsums(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsRechargeConsumQueryVo getCsRechargeConsumById(Serializable id) throws Exception {
        return csRechargeConsumMapper.getCsRechargeConsumById(id);
    }

    @Override
    public Paging<CsRechargeConsumQueryVo> getCsRechargeConsumPageList(CsRechargeConsumQueryParam csRechargeConsumQueryParam) throws Exception {
        Page page = setPageParam(csRechargeConsumQueryParam, OrderItem.desc("create_time"));
        IPage<CsRechargeConsumQueryVo> iPage = csRechargeConsumMapper.getCsRechargeConsumPageList(page, csRechargeConsumQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateStatus(CsRechargeConsumQueryParam csRechargeConsumQueryParam) {
        return csRechargeConsumMapper.updateStatus(csRechargeConsumQueryParam) > 0;
    }

}
