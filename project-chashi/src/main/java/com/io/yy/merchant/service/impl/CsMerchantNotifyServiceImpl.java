package com.io.yy.merchant.service.impl;

import com.io.yy.merchant.entity.CsMerchantNotify;
import com.io.yy.merchant.mapper.CsMerchantNotifyMapper;
import com.io.yy.merchant.service.CsMerchantNotifyService;
import com.io.yy.merchant.param.CsMerchantNotifyQueryParam;
import com.io.yy.merchant.vo.CsMerchantNotifyQueryVo;
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
 * 商家通知人员记录 服务实现类
 * </pre>
 *
 * @author kris
 * @since 2021-11-03
 */
@Slf4j
@Service
public class CsMerchantNotifyServiceImpl extends BaseServiceImpl<CsMerchantNotifyMapper, CsMerchantNotify> implements CsMerchantNotifyService {

    @Autowired
    private CsMerchantNotifyMapper csMerchantNotifyMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCsMerchantNotify(CsMerchantNotify csMerchantNotify) throws Exception {
        return super.save(csMerchantNotify);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCsMerchantNotify(CsMerchantNotify csMerchantNotify) throws Exception {
        return super.updateById(csMerchantNotify);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMerchantNotify(Long id) throws Exception {
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCsMerchantNotifys(List<String> idList) throws Exception {
    return super.removeByIds(idList);
    }

    @Override
    public CsMerchantNotifyQueryVo getCsMerchantNotifyById(Serializable id) throws Exception {
        return csMerchantNotifyMapper.getCsMerchantNotifyById(id);
    }

    @Override
    public Paging<CsMerchantNotifyQueryVo> getCsMerchantNotifyPageList(CsMerchantNotifyQueryParam csMerchantNotifyQueryParam) throws Exception {
        Page page = setPageParam(csMerchantNotifyQueryParam, OrderItem.desc("create_time"));
        IPage<CsMerchantNotifyQueryVo> iPage = csMerchantNotifyMapper.getCsMerchantNotifyPageList(page, csMerchantNotifyQueryParam);
        return new Paging(iPage);
    }

    @Override
    public boolean updateStatus(CsMerchantNotifyQueryParam csMerchantNotifyQueryParam) {
        return csMerchantNotifyMapper.updateStatus(csMerchantNotifyQueryParam) > 0;
    }

}
