package com.io.yy.marketing.service.impl;

import com.io.yy.marketing.entity.CsRechargeSetting;
import com.io.yy.marketing.mapper.CsRechargeSettingMapper;
import com.io.yy.marketing.service.CsRechargeSettingService;
import com.io.yy.marketing.param.CsRechargeSettingQueryParam;
import com.io.yy.marketing.vo.CsRechargeSettingQueryVo;
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
 * 充值设置 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-08-18
 */
@Slf4j
@Service
public class CsRechargeSettingServiceImpl extends BaseServiceImpl<CsRechargeSettingMapper, CsRechargeSetting> implements CsRechargeSettingService {

    @Autowired
    private CsRechargeSettingMapper csRechargeSettingMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsRechargeSetting(CsRechargeSetting csRechargeSetting) throws Exception {
        return super.save(csRechargeSetting);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsRechargeSetting(CsRechargeSetting csRechargeSetting) throws Exception {
        return super.updateById(csRechargeSetting);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsRechargeSetting(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsRechargeSettings(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsRechargeSettingQueryVo getCsRechargeSettingById(Serializable id) throws Exception {
        return csRechargeSettingMapper.getCsRechargeSettingById(id);
    }

    @Override
    public Paging<CsRechargeSettingQueryVo> getCsRechargeSettingPageList(CsRechargeSettingQueryParam csRechargeSettingQueryParam) throws Exception {
        Page page = setPageParam(csRechargeSettingQueryParam, OrderItem.desc("create_time"));
        IPage<CsRechargeSettingQueryVo> iPage = csRechargeSettingMapper.getCsRechargeSettingPageList(page, csRechargeSettingQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateStatus(CsRechargeSettingQueryParam csRechargeSettingQueryParam) {
        return csRechargeSettingMapper.updateStatus(csRechargeSettingQueryParam) > 0;
    }

    @Override
    public List<CsRechargeSettingQueryVo> getSettingListForWx(CsRechargeSettingQueryParam csRechargeSettingQueryParam) throws Exception {
        Page page = setPageParam(csRechargeSettingQueryParam, OrderItem.desc("create_time"));
        page.setSize(1000);
        IPage<CsRechargeSettingQueryVo> iPage = csRechargeSettingMapper.getCsRechargeSettingPageList(page, csRechargeSettingQueryParam);
        return new Paging(iPage).getRecords();
    }

}
